package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlockState;
import signal.api.signal.SignalTypes;
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
	private int modifyDefaultOutputSignal(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "calculateOutputSignal",
		constant = @Constant(
			intValue = 0
		)
	)
	private int modifyMinOutputSignal(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "shouldTurnOn",
		constant = @Constant(
			intValue = 0
		)
	)
	private int modifyMinInputSignal(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "getInputSignal",
		constant = @Constant(
			intValue = 15
		)
	)
	private int modifyMaxInputSignal(int fifteen) {
		return getSignalType().max();
	}

	@Redirect(
		method = "getInputSignal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;hasAnalogOutputSignal()Z"
		)
	)
	private boolean hasAnalogOutputSignal(BlockState state) {
		return ((IBlockState)state).isAnalogSignalSource(SignalTypes.ANY);
	}

	@Redirect(
		method = "getInputSignal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos, Level level) {
		return ((IBlockState)state).isSignalConductor(level, pos, getSignalType());
	}

	@ModifyConstant(
		method = "checkTickOnNeighbor",
		constant = @Constant(
			intValue = 0
		)
	)
	private int modifyMinOutputSignal1(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "refreshOutputState",
		constant = @Constant(
			intValue = 0
		)
	)
	private int modifyMinOutputSignal2(int zero) {
		return getSignalType().min();
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return side.isAlignedHorizontal();
	}
}
