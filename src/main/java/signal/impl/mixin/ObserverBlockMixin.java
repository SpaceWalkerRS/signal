package signal.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.wire.ConnectionSide;
import signal.impl.signal.block.RedstoneSignalSource;

@Mixin(ObserverBlock.class)
public class ObserverBlockMixin implements RedstoneSignalSource {

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(ObserverBlock.POWERED) && state.getValue(ObserverBlock.FACING) == dir ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return getSignal(level, pos, state, dir);
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return side.getOpposite().is(state.getValue(ObserverBlock.FACING));
	}
}
