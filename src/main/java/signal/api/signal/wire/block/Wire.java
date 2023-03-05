package signal.api.signal.wire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.SignalBlockBehavior;
import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.ConnectionType;
import signal.api.signal.wire.WireType;

public interface Wire extends SignalBlockBehavior {

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType type) {
		return isCompatible(type) && getConnection(level, pos, state, side, type) != ConnectionType.NONE;
	}

	boolean isCompatible(WireType type);

	int getSignal(Level level, BlockPos pos, BlockState state, SignalType type);

	BlockState setSignal(Level level, BlockPos pos, BlockState state, SignalType type, int signal);

	ConnectionType getConnection(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType neighborType);

}
