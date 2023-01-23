package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Constant.Condition;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.PoweredBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalConsumer;
import signal.api.signal.block.SignalSource;
import signal.api.signal.wire.block.Wire;
import signal.impl.interfaces.mixin.IPoweredBlock;

@Mixin(DiodeBlock.class)
public abstract class DiodeBlockMixin implements SignalSource, SignalConsumer {

	@Shadow private boolean isAlternateInput(BlockState state) { return false; }
	@Shadow private int getOutputSignal(BlockGetter level, BlockPos pos, BlockState state) { return 0; }

	@ModifyConstant(
		method = "shouldTurnOn",
		constant = @Constant(
			expandZeroConditions = Condition.GREATER_THAN_ZERO
		)
	)
	private int signal$modifyMinInputSignal(int zero) {
		return getSignalType().min();
	}

	@Inject(
		method = "getInputSignal",
		cancellable = true,
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$modifyGetInputSignal(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Integer> cir) {
		Direction facing = state.getValue(DiodeBlock.FACING);
		BlockPos behind = pos.relative(facing);

		cir.setReturnValue(level.getSignalFrom(behind, facing, this));
	}

	@Inject(
		method = "getAlternateSignalAt",
		cancellable = true,
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$modifySideInputSignalAt(LevelReader levelReader, BlockPos pos, Direction dir, CallbackInfoReturnable<Integer> cir) {
		if (!(levelReader instanceof Level)) {
			return; // we should never get here
		}

		SignalType type = getSignalType();
		int signal = type.min();

		Level level = (Level)levelReader;
		BlockState state = level.getBlockState(pos);

		if (isAlternateInput(state)) {
			Block block = state.getBlock();

			if (block instanceof PoweredBlock) {
				signal = ((IPoweredBlock)block).getSignal();
			} else
			if (block instanceof Wire) {
				signal = ((Wire)block).getSignal(level, pos, state);
			} else {
				signal = level.getDirectSignalFrom(pos, dir, this);
			}

			signal = type.clamp(signal);
		}

		cir.setReturnValue(signal);
	}

	@Redirect(
		method = "isAlternateInput",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isSignalSource()Z"
		)
	)
	private boolean signal$isSignalSource(BlockState state) {
		return state.isSignalSource(getConsumedSignalType());
	}

	@ModifyConstant(
		method = "getOutputSignal",
		constant = @Constant(
			intValue = 15
		)
	)
	private int modifyOutputSignalRepeater(int fifteen) {
		return getSignalType().max();
	}

	@Redirect(
		method = "shouldPrioritize",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/DiodeBlock;isDiode(Lnet/minecraft/world/level/block/state/BlockState;)Z"
		)
	)
	private boolean redirectIsDiode(BlockState state) {
		return DiodeBlock.isDiode(state) && isAlternateInput(state);
	}

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(DiodeBlock.POWERED) && dir == state.getValue(DiodeBlock.FACING) ? getOutputSignal(level, pos, state) : getSignalType().min();
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return getSignal(level, pos, state, dir);
	}
}
