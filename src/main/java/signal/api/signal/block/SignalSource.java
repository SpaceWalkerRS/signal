package signal.api.signal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlock;
import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

public interface SignalSource extends IBlock {

	@Override
	default boolean isSignalSource(SignalType type) {
		return getSignalType().is(type);
	}

	@Override
	default int getSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return isSignalSource(type) ? getSignal(level, pos, state, dir) : type.min();
	}

	@Override
	default int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return isSignalSource(type) ? getDirectSignal(level, pos, state, dir) : type.min();
	}

	@Override
	default boolean hasSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return getSignal(level, pos, state, dir, type) > type.min();
	}

	@Override
	default boolean hasDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return getDirectSignal(level, pos, state, dir, type) > type.min();
	}

	@Override
	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType type) {
		return isSignalSource(type.signal()) && shouldConnectToWire(level, pos, state, side);
	}

	/**
	 * Returns the type of signal this block emits (cannot be {@link signal.api.signal.SignalTypes#ANY SignalTypes.ANY}).
	 */
	SignalType getSignalType();


	// override these methods for basic control over your signal source block

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
