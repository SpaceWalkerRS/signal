package signal.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

public interface SignalBlockStateBase {

	/**
	 * This method is equivalent to {@linkplain BlockStateBase#asState}
	 */
	default BlockState signal$asState() {
		return null;
	}

	/**
	 * This method is equivalent to {@linkplain BlockStateBase#getBlock}
	 */
	default Block signal$getBlock() {
		return null;
	}

	default boolean isSignalSource(SignalType type) {
		return signal$getBlock().isSignalSource(type);
	}

	default int getSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return signal$getBlock().getSignal(level, pos, signal$asState(), dir, type);
	}

	default int getDirectSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return signal$getBlock().getDirectSignal(level, pos, signal$asState(), dir, type);
	}

	default boolean hasSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return signal$getBlock().hasSignal(level, pos, signal$asState(), dir, type);
	}

	default boolean hasDirectSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return signal$getBlock().hasDirectSignal(level, pos, signal$asState(), dir, type);
	}

	default boolean isAnalogSignalSource(SignalType type) {
		return signal$getBlock().isAnalogSignalSource(type);
	}

	default int getAnalogSignal(Level level, BlockPos pos, SignalType type) {
		return signal$getBlock().getAnalogSignal(level, pos, signal$asState(), type);
	}

	default boolean isSignalConsumer(SignalType type) {
		return signal$getBlock().isSignalConsumer(type);
	}

	default boolean isSignalConductor(Level level, BlockPos pos, SignalType type) {
		return signal$getBlock().isSignalConductor(level, pos, signal$asState(), type);
	}

	default boolean isWire(WireType type) {
		return signal$getBlock().isWire(type);
	}

	default boolean shouldConnectToWire(Level level, BlockPos pos, ConnectionSide dir, WireType type) {
		return signal$getBlock().shouldConnectToWire(level, pos, signal$asState(), dir, type);
	}
}
