package signal.api.signal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.interfaces.mixin.IBlockBehaviour;
import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

public interface SignalSource extends IBlockBehaviour {

	@Override
	default boolean signalSource(BlockState state) {
		return true;
	}

	@Override
	default boolean signalSource(BlockState state, SignalType type) {
		return is(type);
	}

	@Override
	default int getSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return is(type) ? getSignal(level, pos, state, dir) : type.min();
	}

	@Override
	default int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return is(type) ? getDirectSignal(level, pos, state, dir) : type.min();
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
		return is(type.signal()) && shouldConnectToWire(level, pos, state, side);
	}

	SignalType getSignalType();

	default boolean is(SignalType type) {
		return getSignalType().is(type);
	}

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
