package signal.api.signal.wire.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlockState;
import signal.api.signal.redstone.RedstoneSignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.ConnectionType;
import signal.api.signal.wire.WireType;
import signal.util.Lazy;

public class RedstoneWireType extends WireType {

	public RedstoneWireType(RedstoneSignalType signal) {
		super(signal, signal.min(), signal.max(), 1);
	}

	public RedstoneWireType(RedstoneSignalType signal, int step) {
		super(signal, signal.min(), signal.max(), step);
	}

	public RedstoneWireType(RedstoneSignalType signal, int min, int max, int step) {
		super(signal, min, max, step);
	}

	@Override
	public void findPotentialConnections(Level level, BlockPos pos, PotentialConnectionConsumer consumer) {
		Lazy<IBlockState> ibelowState = new Lazy<>(() -> (IBlockState)level.getBlockState(pos.below()));
		Lazy<IBlockState> iaboveState = new Lazy<>(() -> (IBlockState)level.getBlockState(pos.above()));

		Lazy<Boolean> belowIsConductor = new Lazy<>(() -> ibelowState.get().isSignalConductor(level, pos.below(), signal));
		Lazy<Boolean> aboveIsConductor = new Lazy<>(() -> iaboveState.get().isSignalConductor(level, pos.above(), signal));

		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos adjacent = pos.relative(dir);
			BlockState adjacentState = level.getBlockState(adjacent);
			IBlockState iadjacentState = (IBlockState)adjacentState;

			if (iadjacentState.isWire()) {
				consumer.accept(ConnectionSide.fromDirection(dir), adjacent, adjacentState, ConnectionType.BOTH);
			} else {
				boolean adjacentIsConductor = iadjacentState.isSignalConductor(level, adjacent, signal);

				if (!adjacentIsConductor) {
					ConnectionSide side = ConnectionSide.fromDirection(dir).withDown();
					ConnectionType connection = belowIsConductor.get() ? ConnectionType.BOTH : ConnectionType.IN;

					consumer.accept(level, pos, side, connection);
				}
				if (!aboveIsConductor.get()) {
					ConnectionSide side = ConnectionSide.fromDirection(dir).withUp();
					ConnectionType connection = adjacentIsConductor ? ConnectionType.BOTH : ConnectionType.OUT;

					consumer.accept(level, pos, side, connection);
				}
			}
		}
	}

	@Override
	public ConnectionType getPotentialConnection(Level level, BlockPos pos, ConnectionSide side) {
		// no connections straight up or down
		if (side.isAlignedVertical()) {
			return ConnectionType.NONE;
		}
		// connections directly adjacent
		if (side.isAlignedHorizontal()) {
			return ConnectionType.BOTH;
		}

		ConnectionSide ver = side.projectVertical();

		// no connections diagonally horizontal
		if (ver == null) {
			return ConnectionType.NONE;
		}

		ConnectionSide hor = side.projectHorizontal();

		if (ver == ConnectionSide.DOWN) {
			BlockPos adjacent = hor.offset(pos);
			BlockState adjacentState = level.getBlockState(adjacent);
			IBlockState iadjacentState = (IBlockState)adjacentState;

			if (iadjacentState.isSignalConductor(level, adjacent, signal)) {
				return ConnectionType.NONE;
			}

			BlockPos below = pos.below();
			BlockState belowState = level.getBlockState(below);
			IBlockState ibelowState = (IBlockState)belowState;

			return ibelowState.isSignalConductor(level, below, signal) ? ConnectionType.BOTH : ConnectionType.IN;
		}
		if (ver == ConnectionSide.UP) {
			BlockPos above = pos.above();
			BlockState aboveState = level.getBlockState(above);
			IBlockState iaboveState = (IBlockState)aboveState;

			if (iaboveState.isSignalConductor(level, above, signal)) {
				return ConnectionType.NONE;
			}

			BlockPos adjacent = hor.offset(pos);
			BlockState adjacentState = level.getBlockState(adjacent);
			IBlockState iadjacentState = (IBlockState)adjacentState;

			return iadjacentState.isSignalConductor(level, adjacent, signal) ? ConnectionType.BOTH : ConnectionType.OUT;
		}

		return ConnectionType.NONE;
	}
}
