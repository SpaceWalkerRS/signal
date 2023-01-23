package signal.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import signal.api.signal.block.SignalConsumer;

public interface SignalLevel {

	default int getSignal(BlockPos pos, SignalConsumer consumer) {
		return consumer.getConsumedSignalType().min();
	}

	default int getDirectSignal(BlockPos pos, SignalConsumer consumer) {
		return consumer.getConsumedSignalType().min();
	}

	default int getSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer) {
		return consumer.getConsumedSignalType().min();
	}

	default int getDirectSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer) {
		return consumer.getConsumedSignalType().min();
	}

	default boolean hasSignal(BlockPos pos, SignalConsumer consumer) {
		return false;
	}

	default boolean hasDirectSignal(BlockPos pos, SignalConsumer consumer) {
		return false;
	}

	default boolean hasSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer) {
		return false;
	}

	default boolean hasDirectSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer) {
		return false;
	}
}
