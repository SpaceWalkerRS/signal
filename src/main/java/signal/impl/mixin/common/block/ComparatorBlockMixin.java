package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalTypes;
import signal.api.signal.block.AnalogSignalSource;
import signal.api.signal.block.redstone.RedstoneSignalConsumer;
import signal.api.signal.block.redstone.RedstoneSignalSource;
import signal.api.signal.wire.ConnectionSide;

@Mixin(ComparatorBlock.class)
public class ComparatorBlockMixin implements RedstoneSignalSource, RedstoneSignalConsumer {

	@ModifyConstant(
		method = "getOutputSignal",
		constant = @Constant(
			intValue = 0
		)
	)
	private int signal$modifyDefaultOutputSignal(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "calculateOutputSignal",
		constant = @Constant(
			intValue = 0
		)
	)
	private int signal$modifyMinOutputSignal(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "shouldTurnOn",
		constant = @Constant(
			intValue = 0
		)
	)
	private int signal$modifyMinInputSignal(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "getInputSignal",
		constant = @Constant(
			intValue = 15
		)
	)
	private int signal$modifyMaxInputSignal(int fifteen) {
		return getSignalType().max();
	}

	@Redirect(
		method = "getInputSignal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;hasAnalogOutputSignal()Z"
		)
	)
	private boolean signal$hasAnalogOutputSignal(BlockState state) {
		return state.isAnalogSignalSource(SignalTypes.ANY);
	}

	@Redirect(
		method = "getInputSignal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getAnalogOutputSignal(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I"
		)
	)
	private int signal$deprecateGetAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return state.getAnalogSignal(level, pos, getConsumedSignalType());
	}

	@Redirect(
		method = "getInputSignal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean signal$isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos, Level level) {
		return state.isSignalConductor(level, pos, getConsumedSignalType());
	}

	@Redirect(
		method = "getInputSignal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/decoration/ItemFrame;getAnalogOutput()I"
		)
	)
	private int signal$getAnalogOutput(ItemFrame itemFrame) {
		return AnalogSignalSource.getAnalogSignal(itemFrame.getAnalogOutput(), getConsumedSignalType().min(), getConsumedSignalType().max());
	}

	@ModifyConstant(
		method = "checkTickOnNeighbor",
		constant = @Constant(
			intValue = 0
		)
	)
	private int signal$modifyMinOutputSignal1(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "refreshOutputState",
		constant = @Constant(
			intValue = 0
		)
	)
	private int signal$modifyMinOutputSignal2(int zero) {
		return getSignalType().min();
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return side.isAlignedHorizontal();
	}
}
