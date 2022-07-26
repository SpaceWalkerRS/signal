package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedstoneWallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.redstone.RedstoneSignalConsumer;
import signal.api.signal.block.redstone.RedstoneSignalSource;
import signal.api.signal.wire.ConnectionSide;

@Mixin(RedstoneWallTorchBlock.class)
public class RedstoneWallTorchBlockMixin implements RedstoneSignalSource, RedstoneSignalConsumer {

	@Inject(
		method = "hasNeighborSignal",
		cancellable = true,
		at = @At(
			value = "HEAD"
		)
	)
	private void modifyHasNeighborSignal(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		Direction dir = state.getValue(RedstoneWallTorchBlock.FACING).getOpposite();
		BlockPos behind = pos.relative(dir);

		cir.setReturnValue(hasReceivedSignal(level, behind));
	}

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(RedstoneWallTorchBlock.LIT) && state.getValue(RedstoneWallTorchBlock.FACING) != dir ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return side.isAligned() && !side.is(state.getValue(RedstoneWallTorchBlock.FACING).getOpposite());
	}
}
