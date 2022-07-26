package signal.api.signal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.interfaces.mixin.IBlockBehaviour;
import signal.api.interfaces.mixin.ILevel;
import signal.api.signal.SignalType;

public interface SignalConsumer extends IBlockBehaviour {

	@Override
	default boolean signalConsumer(BlockState state) {
		return true;
	}

	@Override
	default boolean signalConsumer(BlockState state, SignalType type) {
		return consumes(type);
	}

	SignalType getConsumedSignalType();

	default boolean consumes(SignalType type) {
		return getConsumedSignalType().is(type);
	}

	default void setAllowWireSignals(Level level, boolean allowWireSignals) {
		((ILevel)level).setAllowWireSignals(allowWireSignals);
	}

	default int getReceivedSignal(Level level, BlockPos pos) {
		return ((ILevel)level).getSignal(pos, getConsumedSignalType());
	}

	default int getReceivedDirectSignal(Level level, BlockPos pos) {
		return ((ILevel)level).getDirectSignal(pos, getConsumedSignalType());
	}

	default int getReceivedSignalFrom(Level level, BlockPos pos, Direction dir) {
		return ((ILevel)level).getSignalFrom(pos, dir, getConsumedSignalType());
	}

	default int getReceivedDirectSignalFrom(Level level, BlockPos pos, Direction dir) {
		return ((ILevel)level).getDirectSignalFrom(pos, dir, getConsumedSignalType());
	}

	default boolean hasReceivedSignal(Level level, BlockPos pos) {
		return ((ILevel)level).hasSignal(pos, getConsumedSignalType());
	}

	default boolean hasReceivedDirectSignal(Level level, BlockPos pos) {
		return ((ILevel)level).hasDirectSignal(pos, getConsumedSignalType());
	}

	default boolean hasReceivedSignalFrom(Level level, BlockPos pos, Direction dir) {
		return ((ILevel)level).hasSignalFrom(pos, dir, getConsumedSignalType());
	}

	default boolean hasReceivedDirectSignalFrom(Level level, BlockPos pos, Direction dir) {
		return ((ILevel)level).hasDirectSignalFrom(pos, dir, getConsumedSignalType());
	}
}
