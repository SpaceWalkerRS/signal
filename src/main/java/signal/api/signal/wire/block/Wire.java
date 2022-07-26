package signal.api.signal.wire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlockState;
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
	default boolean wire(BlockState state) {
		return true;
	}

	@Override
	default boolean wire(BlockState state, WireType type) {
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

	default int getReceivedSignal(Level level, BlockPos pos) {
		WireType type = getWireType();

		setAllowWireSignals(level, false);
		int signal = getReceivedSignal(level, pos);
		setAllowWireSignals(level, true);

		if (signal < type.max()) {
			signal = Math.max(signal, getReceivedWireSignal(level, pos));
		}

		return signal;
	}

	default int getReceivedWireSignal(Level level, BlockPos pos) {
		WireType type = getWireType();

		int signal = type.min();

		for (ConnectionSide side : ConnectionSide.ALL) {
			signal = Math.max(signal, getReceivedWireSignal(level, pos, side));

			if (signal >= type.max()) {
				return type.max();
			}
		}

		return type.clamp(signal);
	}

	default int getReceivedWireSignal(Level level, BlockPos pos, ConnectionSide side) {
		WireType type = getWireType();

		if (!type.getConnection(level, pos, side).in()) {
			return type.min();
		}

		BlockPos neighborPos = side.offset(pos);
		BlockState neighbor = level.getBlockState(neighborPos);
		IBlockState ineighbor = (IBlockState)neighbor;

		if (!ineighbor.wire()) {
			return type.min();
		}

		Wire neighborWire = (Wire)ineighbor.getIBlock();

		if (!neighborWire.isCompatible(type)) {
			return type.min();
		}

		WireType neighborType = neighborWire.getWireType();

		if (neighborType != type && !neighborType.getConnection(level, neighborPos, side.getOpposite()).out()) {
			return type.min();
		}

		int neighborSignal = neighborWire.getSignal(level, neighborPos, neighbor);
		int step = Math.max(type.step(), neighborType.step());

		return neighborSignal - step;
	}
}
