package signal.api;

import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;
import signal.impl.interfaces.mixin.IBlockStateBase;

public interface SignalBlockBehavior {

	default void getSignalTypes(BlockState state, Consumer<SignalType> action) {
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

	default boolean isAnalogSignalSource(BlockState state, SignalType type) {
		return false;
	}

	default int getAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return type.min();
	}

	default boolean isSignalConsumer(BlockState state, SignalType type) {
		return false;
	}

	default boolean isSignalConductor(Level level, BlockPos pos, BlockState state, SignalType type) {
		return type.is(SignalTypes.ANY) && ((IBlockStateBase)state).signal$isRedstoneConductor(level, pos);
	}

	default boolean isWire(BlockState state, WireType type) {
		return false;
	}

	default boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side, WireType type) {
		return false;
	}
}
