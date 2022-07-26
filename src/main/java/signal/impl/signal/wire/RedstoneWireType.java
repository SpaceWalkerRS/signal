package signal.impl.signal.wire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.interfaces.mixin.IBlockState;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.ConnectionType;
import signal.api.signal.wire.WireType;
import signal.impl.signal.RedstoneSignalType;

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
	public ConnectionType getConnection(Level level, BlockPos pos, ConnectionSide side) {
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
