package signal.api.signal.wire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlockState;
import signal.api.signal.SignalHolder;
import signal.api.signal.SignalType;
import signal.api.signal.block.SignalConsumer;
import signal.api.signal.block.SignalSource;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

public interface Wire extends SignalSource, SignalConsumer {

	@Override
	default SignalType getSignalType() {
		return getWireType().signal();
	}

	@Override
	default SignalType getConsumedSignalType() {
		return getWireType().signal();
	}

	@Override
	default boolean isWire() {
		return true;
	}

	@Override
	default boolean isWire(WireType type) {
		return is(type);
	}

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType type) {
		return isCompatible(type) && shouldConnectToWire(level, pos, state, side);
	}

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return true;
	}

	WireType getWireType();

	default boolean is(WireType type) {
		return getWireType() == type;
	}

	default boolean isCompatible(WireType type) {
		return getWireType().isCompatible(type);
	}

	default int getSignal(Level level, BlockPos pos, BlockState state) {
		return getWireType().min();
	}

	default int getNeighborSignal(Level level, BlockPos pos) {
		int signal = getReceivedSignal(level, pos);

		if (signal < getWireType().max()) {
			signal = Math.max(signal, getNeighborWireSignal(level, pos));
		}

		return signal;
	}

	default int getNeighborWireSignal(Level level, BlockPos pos) {
		WireType type = getWireType();
		SignalHolder signal = new SignalHolder(type.min());

		getWireType().findConnections(level, pos, (side, neighborPos, neighborState, connection) -> {
			if (!connection.in()) {
				return;
			}

			IBlockState ineighborState = (IBlockState)neighborState;
			Wire neighborWire = (Wire)ineighborState.getIBlock();

			int neighborSignal = neighborWire.getSignal(level, neighborPos, neighborState);
			int step = Math.max(type.step(), neighborWire.getWireType().step());

			signal.increase(neighborSignal - step);
		});

		return type.clamp(signal.get());
	}
}
