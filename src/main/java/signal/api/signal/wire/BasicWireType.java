package signal.api.signal.wire;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.util.Lazy;

public class BasicWireType extends WireType {

	public BasicWireType(SignalType signal) {
		super(signal, signal.min(), signal.max(), 1);
	}

	public BasicWireType(SignalType signal, int step) {
		super(signal, signal.min(), signal.max(), step);
	}

	public BasicWireType(SignalType signal, int min, int max, int step) {
		super(signal, min, max, step);
	}

	@Override
	public void findPotentialConnections(Level level, BlockPos pos, PotentialConnectionConsumer action) {
		Lazy<BlockState> belowState = new Lazy<>(() -> level.getBlockState(pos.below()));
		Lazy<BlockState> aboveState = new Lazy<>(() -> level.getBlockState(pos.above()));

		Lazy<Boolean> belowIsConductor = new Lazy<>(() -> belowState.get().isSignalConductor(level, pos.below(), signal));
		Lazy<Boolean> aboveIsConductor = new Lazy<>(() -> aboveState.get().isSignalConductor(level, pos.above(), signal));

		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos adjacent = pos.relative(dir);
			BlockState adjacentState = level.getBlockState(adjacent);

			if (adjacentState.isWire(WireTypes.ANY)) {
				ConnectionSide side = ConnectionSide.fromDirection(dir);
				ConnectionType connection = ConnectionType.BOTH;

				action.accept(side, adjacent, adjacentState, connection);
			} else {
				boolean adjacentIsConductor = adjacentState.isSignalConductor(level, adjacent, signal);

				if (!adjacentIsConductor) {
					ConnectionSide side = ConnectionSide.fromDirection(dir).withDown();
					ConnectionType connection = belowIsConductor.get() ? ConnectionType.BOTH : ConnectionType.IN;

					action.accept(level, pos, side, connection);
				}
				if (!aboveIsConductor.get()) {
					ConnectionSide side = ConnectionSide.fromDirection(dir).withUp();
					ConnectionType connection = adjacentIsConductor ? ConnectionType.BOTH : ConnectionType.OUT;

					action.accept(level, pos, side, connection);
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

			if (adjacentState.isSignalConductor(level, adjacent, signal)) {
				return ConnectionType.NONE;
			}

			BlockPos below = pos.below();
			BlockState belowState = level.getBlockState(below);

			return belowState.isSignalConductor(level, below, signal) ? ConnectionType.BOTH : ConnectionType.IN;
		}
		if (ver == ConnectionSide.UP) {
			BlockPos above = pos.above();
			BlockState aboveState = level.getBlockState(above);

			if (aboveState.isSignalConductor(level, above, signal)) {
				return ConnectionType.NONE;
			}

			BlockPos adjacent = hor.offset(pos);
			BlockState adjacentState = level.getBlockState(adjacent);

			return adjacentState.isSignalConductor(level, adjacent, signal) ? ConnectionType.BOTH : ConnectionType.OUT;
		}

		return ConnectionType.NONE;
	}
}
