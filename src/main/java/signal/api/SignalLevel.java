package signal.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import signal.api.signal.SignalType;

public interface SignalLevel {

	void ignoreWireSignals(boolean ignore);

	default int getSignal(BlockPos pos, SignalType type) {
		return type.min();
	}

	default int getDirectSignal(BlockPos pos, SignalType type) {
		return type.min();
	}

	default int getSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		return type.min();
	}

	default int getDirectSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		return type.min();
	}

	default boolean hasSignal(BlockPos pos, SignalType type) {
		return false;
	}

	default boolean hasDirectSignal(BlockPos pos, SignalType type) {
		return false;
	}

	default boolean hasSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		return false;
	}

	default boolean hasDirectSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		return false;
	}
}
