package signal.api.interfaces.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

public interface IBlockBehaviour {

	static final Direction[] DIRECTIONS = Direction.values();

	default boolean isSignalSource(BlockState state) {
		return false;
	}

	default boolean isSignalSource(BlockState state, SignalType type) {
		return false;
	}

	default int getSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return type.min();
	}

	default int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return type.min();
	}

	default boolean hasSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return false;
	}

	default boolean hasDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir, SignalType type) {
		return false;
	}

	default boolean isAnalogSignalSource(BlockState state) {
		return false;
	}

	default boolean isAnalogSignalSource(BlockState state, SignalType type) {
		return false;
	}

	default int getAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return type.min();
	}

	default boolean hasAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return false;
	}

	default boolean isSignalConsumer(BlockState state) {
		return false;
	}

	default boolean isSignalConsumer(BlockState state, SignalType type) {
		return false;
	}

	default boolean isSignalConductor(Level level, BlockPos pos, BlockState state, SignalType type) {
		return state.isRedstoneConductor(level, pos);
	}

	default boolean isWire(BlockState state) {
		return false;
	}

	default boolean isWire(BlockState state, WireType type) {
		return false;
	}

	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType type) {
		return false;
	}
}
