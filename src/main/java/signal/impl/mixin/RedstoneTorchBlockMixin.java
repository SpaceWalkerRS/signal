package signal.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.wire.ConnectionSide;
import signal.impl.signal.block.RedstoneSignalConsumer;
import signal.impl.signal.block.RedstoneSignalSource;

@Mixin(RedstoneTorchBlock.class)
public class RedstoneTorchBlockMixin implements RedstoneSignalSource, RedstoneSignalConsumer {

	@Inject(
		method = "hasNeighborSignal",
		cancellable = true,
		at = @At(
			value = "HEAD"
		)
	)
	private void modifyHasNeighborSignal(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(hasReceivedSignal(level, pos.below()));
	}

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(RedstoneTorchBlock.LIT) && dir != Direction.UP ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(RedstoneTorchBlock.LIT) && dir == Direction.DOWN ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return side.isAligned() && side != ConnectionSide.DOWN;
	}
}
