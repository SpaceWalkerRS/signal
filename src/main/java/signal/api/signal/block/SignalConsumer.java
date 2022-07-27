package signal.api.signal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import signal.api.IBlock;
import signal.api.ILevel;
import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public interface SignalConsumer extends IBlock {

	@Override
	default boolean isSignalConsumer() {
		return true;
	}

	@Override
	default boolean isSignalConsumer(SignalType type) {
		return consumes(type);
	}

	default SignalType getConsumedSignalType() {
		return SignalTypes.ANY;
	}

	default boolean consumes(SignalType type) {
		return getConsumedSignalType().is(type);
	}


	// convenient methods so you do not have to do any casting

	default int getReceivedSignal(Level level, BlockPos pos) {
		return ((ILevel)level).getSignal(pos, this);
	}

	default int getReceivedDirectSignal(Level level, BlockPos pos) {
		return ((ILevel)level).getDirectSignal(pos, this);
	}

	default int getReceivedSignalFrom(Level level, BlockPos pos, Direction dir) {
		return ((ILevel)level).getSignalFrom(pos, dir, this);
	}

	default int getReceivedDirectSignalFrom(Level level, BlockPos pos, Direction dir) {
		return ((ILevel)level).getDirectSignalFrom(pos, dir, this);
	}

	default boolean hasReceivedSignal(Level level, BlockPos pos) {
		return ((ILevel)level).hasSignal(pos, this);
	}

	default boolean hasReceivedDirectSignal(Level level, BlockPos pos) {
		return ((ILevel)level).hasDirectSignal(pos, this);
	}

	default boolean hasReceivedSignalFrom(Level level, BlockPos pos, Direction dir) {
		return ((ILevel)level).hasSignalFrom(pos, dir, this);
	}

	default boolean hasReceivedDirectSignalFrom(Level level, BlockPos pos, Direction dir) {
		return ((ILevel)level).hasDirectSignalFrom(pos, dir, this);
	}
}
