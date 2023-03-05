package signal.api.signal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import signal.api.SignalBlockBehavior;
import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

/**
 * This interface represents a block that can consume signals of a single type.
 * 
 * @author Space Walker
 */
public interface BasicSignalConsumer extends SignalBlockBehavior {

	/**
	 * Returns the type of analog signal this block consumes.
	 */
	default SignalType getConsumedSignalType() {
		return SignalTypes.ANY;
	}

	default int getNeighborSignal(Level level, BlockPos pos) {
		return level.getSignal(pos, getConsumedSignalType());
	}

	default int getDirectNeighborSignal(Level level, BlockPos pos) {
		return level.getDirectSignal(pos, getConsumedSignalType());
	}

	default int getNeighborSignalFrom(Level level, BlockPos pos, Direction dir) {
		return level.getSignalFrom(pos, dir, getConsumedSignalType());
	}

	default int getDirectNeighborSignalFrom(Level level, BlockPos pos, Direction dir) {
		return level.getDirectSignalFrom(pos, dir, getConsumedSignalType());
	}

	default boolean hasNeighborSignal(Level level, BlockPos pos) {
		return level.hasSignal(pos, getConsumedSignalType());
	}

	default boolean hasDirectNeighborSignal(Level level, BlockPos pos) {
		return level.hasDirectSignal(pos, getConsumedSignalType());
	}

	default boolean hasNeighborSignalFrom(Level level, BlockPos pos, Direction dir) {
		return level.hasSignalFrom(pos, dir, getConsumedSignalType());
	}
	
	default boolean hasDirectNeighborSignalFrom(Level level, BlockPos pos, Direction dir) {
		return level.hasDirectSignalFrom(pos, dir, getConsumedSignalType());
	}
}
