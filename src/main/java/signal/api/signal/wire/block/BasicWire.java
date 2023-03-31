package signal.api.signal.wire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalHolder;
import signal.api.signal.SignalType;
import signal.api.signal.block.BasicSignalConsumer;
import signal.api.signal.block.BasicSignalSource;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.ConnectionType;
import signal.api.signal.wire.WireType;
import signal.api.signal.wire.WireTypes;

/**
 * This interface represents a wire block of one specific type.
 * 
 * @author Space Walker
 */
public interface BasicWire extends BasicSignalSource, BasicSignalConsumer, Wire {

	@Override
	default boolean isWire(BlockState state, WireType type) {
		return getWireType().is(type);
	}

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType type) {
		return isCompatible(type) && shouldConnectToWire(level, pos, state, side);
	}

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return getWireType().getPotentialConnection(level, pos, side) != ConnectionType.NONE;
	}

	@Override
	default ConnectionType getConnection(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType neighborType) {
		return isCompatible(neighborType) ? getWireType().getPotentialConnection(level, pos, side) : ConnectionType.NONE;
	}

	@Override
	default SignalType getSignalType() {
		return getWireType().signal();
	}

	@Override
	default int getNeighborSignal(Level level, BlockPos pos) {
		level.ignoreWireSignals(true);
		int signal = level.getSignal(pos, getSignalType());
		level.ignoreWireSignals(false);

		if (signal < getWireType().max()) {
			signal = Math.max(signal, getNeighborWireSignal(level, pos));
		}

		return signal;
	}

	@Override
	default boolean hasNeighborSignal(Level level, BlockPos pos) {
		level.ignoreWireSignals(true);
		boolean hasNeighborSignal = level.hasSignal(pos, getSignalType());
		level.ignoreWireSignals(false);

		return hasNeighborSignal || hasNeighborWireSignal(level, pos);
	}

	@Override
	default boolean isCompatible(WireType type) {
		return WireTypes.areCompatible(getWireType(), type);
	}

	@Override
	default int getSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return isSignalSource(state, type) ? getSignal(level, pos, state) : type.min();
	}

	@Override
	default BlockState setSignal(Level level, BlockPos pos, BlockState state, SignalType type, int signal) {
		return isSignalSource(state, type) ? state : setSignal(level, pos, state, signal);
	}

	/**
	 * @return the type of this wire (cannot be {@link signal.api.signal.wire.WireTypes#ANY WireTypes.ANY}).
	 */
	WireType getWireType();

	int getSignal(Level level, BlockPos pos, BlockState state);

	BlockState setSignal(Level level, BlockPos pos, BlockState state, int signal);

	default int getNeighborWireSignal(Level level, BlockPos pos) {
		WireType type = getWireType();
		SignalHolder signal = new SignalHolder();

		type.findConnections(level, pos, (side, neighborPos, neighborState, connection) -> {
			Wire neighborWire = (Wire)neighborState.getBlock();
			int neighborSignal = neighborWire.getSignal(level, neighborPos, neighborState, type.signal());
			int step = type.step();

			signal.increase(neighborSignal - step);

			return signal.get() < type.max();
		});

		return signal.get();
	}

	default boolean hasNeighborWireSignal(Level level, BlockPos pos) {
		WireType type = getWireType();
		SignalHolder signal = new SignalHolder();

		type.findConnections(level, pos, (side, neighborPos, neighborState, connection) -> {
			Wire neighborWire = (Wire)neighborState.getBlock();
			int neighborSignal = neighborWire.getSignal(level, neighborPos, neighborState, type.signal());
			int step = type.step();

			signal.increase(neighborSignal - step);

			return signal.get() == type.min();
		});

		return signal.get() > type.min();
	}
}
