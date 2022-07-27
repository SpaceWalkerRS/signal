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
		BlockPos below = pos.below();
		BlockPos above = pos.above();
		IBlockState ibelowState = (IBlockState)level.getBlockState(below);
		IBlockState iaboveState = (IBlockState)level.getBlockState(above);

		boolean belowIsConductor = ibelowState.isSignalConductor(level, below, signal);
		boolean aboveIsConductor = iaboveState.isSignalConductor(level, above, signal);

		for (Direction dir : Direction.Plane.HORIZONTAL) {
			BlockPos adjacentPos = pos.relative(dir);
			BlockState adjacentState = level.getBlockState(adjacentPos);
			IBlockState iadjacentState = (IBlockState)adjacentState;

			if (iadjacentState.isWire()) {
				consumer.accept(ConnectionSide.fromDirection(dir), adjacentPos, adjacentState, ConnectionType.BOTH);
			} else {
				boolean adjacentIsConductor = iadjacentState.isSignalConductor(level, adjacentPos, signal);

				if (!adjacentIsConductor) {
					ConnectionSide side = ConnectionSide.fromDirections(dir, Direction.DOWN);
					ConnectionType connection = belowIsConductor ? ConnectionType.BOTH : ConnectionType.IN;

					consumer.accept(level, pos, side, connection);
				}
				if (!aboveIsConductor) {
					ConnectionSide side = ConnectionSide.fromDirections(dir, Direction.UP);
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
			BlockPos sidePos = hor.offset(pos);
			BlockState sideState = level.getBlockState(sidePos);
			IBlockState isideState = (IBlockState)sideState;

			if (!isideState.isSignalConductor(level, sidePos, signal)) {
				BlockPos belowPos = pos.below();
				BlockState belowState = level.getBlockState(belowPos);
				IBlockState ibelowState = (IBlockState)belowState;

				return ibelowState.isSignalConductor(level, belowPos, signal) ? ConnectionType.BOTH : ConnectionType.IN;
			}
		} else
		if (ver == ConnectionSide.UP) {
			BlockPos abovePos = pos.above();
			BlockState aboveState = level.getBlockState(abovePos);
			IBlockState iaboveState = (IBlockState)aboveState;

			if (!iaboveState.isSignalConductor(level, abovePos, signal)) {
				BlockPos sidePos = hor.offset(pos);
				BlockState sideState = level.getBlockState(sidePos);
				IBlockState isideState = (IBlockState)sideState;

				return isideState.isSignalConductor(level, sidePos, signal) ? ConnectionType.BOTH : ConnectionType.OUT;
			}
		}

		return ConnectionType.NONE;
	}
}
