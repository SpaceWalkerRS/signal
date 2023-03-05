package signal.api.signal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.SignalBlockBehavior;
import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

/**
 * This interface represents a block that can emit signals of a single type.
 * 
 * @author Space Walker
 */
public interface BasicSignalSource extends SignalBlockBehavior {

	@Override
	default boolean isSignalSource(BlockState state, SignalType type) {
		return getSignalType().is(type);
	}

	@Override
	default int getSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return isSignalSource(state, type) ? getSignal(level, pos, state, dir) : getSignalType().min();
	}

	@Override
	default int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return isSignalSource(state, type) ? getDirectSignal(level, pos, state, dir) : getSignalType().min();
	}

	@Override
	default boolean hasSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return getSignal(level, pos, state, dir, type) > getSignalType().min();
	}

	@Override
	default boolean hasDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return getDirectSignal(level, pos, state, dir, type) > getSignalType().min();
	}

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType type) {
		return isSignalSource(state, type.signal()) && shouldConnectToWire(level, pos, state, side);
	}


	// override these methods for basic control over your signal source block

	/**
	 * Returns the type of signal this block emits (cannot be {@link signal.api.signal.SignalTypes#ANY SignalTypes.ANY}).
	 */
	SignalType getSignalType();

	default int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return getSignalType().min();
	}

	default int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return getSignalType().min();
	}

	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return side.isAligned();
	}
}
