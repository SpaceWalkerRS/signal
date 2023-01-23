package signal.api.signal.wire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalHolder;
import signal.api.signal.SignalType;
import signal.api.signal.block.SignalConsumer;
import signal.api.signal.block.SignalSource;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.ConnectionType;
import signal.api.signal.wire.WireType;
import signal.api.signal.wire.WireTypes;

public interface Wire extends SignalSource, SignalConsumer {

	@Override
	default boolean isWire(WireType type) {
		return getWireType().is(type);
	}

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType neighborType) {
		WireType type = getWireType();

		if (!WireTypes.areCompatible(type, neighborType)) {
			return false;
		}

		ConnectionType connection = type.getPotentialConnection(level, pos, side);

		if (type != neighborType) {
			BlockPos neighborPos = side.offset(pos);
			ConnectionSide neighborSide = side.getOpposite();

			connection = connection.and(neighborType.getPotentialConnection(level, neighborPos, neighborSide));
		}

		return connection != ConnectionType.NONE;
	}

	@Override
	default SignalType getSignalType() {
		return getWireType().signal();
	}

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return getWireType().getConnection(level, pos, side) != ConnectionType.NONE;
	}

	@Override
	default SignalType getConsumedSignalType() {
		return getWireType().signal();
	}

	/**
	 * Returns the type of wire this block is (cannot be {@link signal.api.signal.wire.WireTypes#ANY WireTypes.ANY}).
	 */
	WireType getWireType();

	default boolean isCompatible(Wire wire) {
		return WireTypes.areCompatible(getWireType(), wire.getWireType());
	}

	default int getSignal(Level level, BlockPos pos, BlockState state) {
		return getWireType().min();
	}

	default BlockState setSignal(Level level, BlockPos pos, BlockState state, int signal) {
		return state;
	}

	default int getNeighborSignal(Level level, BlockPos pos) {
		int signal = level.getSignal(pos, this);

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
				return true;
			}

			Wire neighborWire = (Wire)neighborState.getBlock();

			int neighborSignal = neighborWire.getSignal(level, neighborPos, neighborState);
			int step = Math.max(type.step(), neighborWire.getWireType().step());

			signal.increase(neighborSignal - step);

			return signal.get() < type.max();
		});

		return type.clamp(signal.get());
	}
}
