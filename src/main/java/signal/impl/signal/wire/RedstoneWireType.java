package signal.impl.signal.wire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.interfaces.mixin.IBlockStateBase;
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
			IBlockStateBase isideState = (IBlockStateBase)sideState;

			if (!isideState.signalConductor(level, sidePos, signal)) {
				BlockPos belowPos = pos.below();
				BlockState belowState = level.getBlockState(belowPos);
				IBlockStateBase ibelowState = (IBlockStateBase)belowState;

				return ibelowState.signalConductor(level, belowPos, signal) ? ConnectionType.BOTH : ConnectionType.IN;
			}
		} else
		if (ver == ConnectionSide.UP) {
			BlockPos abovePos = pos.above();
			BlockState aboveState = level.getBlockState(abovePos);
			IBlockStateBase iaboveState = (IBlockStateBase)aboveState;

			if (!iaboveState.signalConductor(level, abovePos, signal)) {
				BlockPos sidePos = hor.offset(pos);
				BlockState sideState = level.getBlockState(sidePos);
				IBlockStateBase isideState = (IBlockStateBase)sideState;

				return isideState.signalConductor(level, sidePos, signal) ? ConnectionType.BOTH : ConnectionType.OUT;
			}
		}

		return ConnectionType.NONE;
	}
}
