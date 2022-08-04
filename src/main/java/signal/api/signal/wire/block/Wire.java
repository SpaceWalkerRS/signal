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
import signal.api.signal.wire.ConnectionType;
import signal.api.signal.wire.WireType;
import signal.api.signal.wire.WireTypes;

public interface Wire extends SignalSource, SignalConsumer {

	@Override
	default boolean isWire() {
		return true;
	}

	@Override
	default boolean isWire(WireType type) {
		return getWireType() == type;
	}

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType type) {
		return WireTypes.areCompatible(getWireType(), type) && shouldConnectToWire(level, pos, state, side);
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
		int signal = getReceivedSignal(level, pos);

		if (signal < getWireType().max()) {
			signal = Math.max(signal, getReceivedWireSignal(level, pos));
		}

		return signal;
	}

	default int getReceivedWireSignal(Level level, BlockPos pos) {
		WireType type = getWireType();
		SignalHolder signal = new SignalHolder(type.min());

		getWireType().findConnections(level, pos, (side, neighborPos, neighborState, connection) -> {
			if (!connection.in()) {
				return true;
			}

			IBlockState ineighborState = (IBlockState)neighborState;
			Wire neighborWire = (Wire)ineighborState.getIBlock();

			int neighborSignal = neighborWire.getSignal(level, neighborPos, neighborState);
			int step = Math.max(type.step(), neighborWire.getWireType().step());

			signal.increase(neighborSignal - step);

			return signal.get() < type.max();
		});

		return type.clamp(signal.get());
	}
}
