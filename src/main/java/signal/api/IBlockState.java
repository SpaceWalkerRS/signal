package signal.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

public interface IBlockState {

	IBlock getIBlock();

	default boolean isSignalSource(SignalType type) {
		return false;
	}

	default int getSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return type.min();
	}

	default int getDirectSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return type.min();
	}

	default boolean hasSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return false;
	}

	default boolean hasDirectSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return false;
	}

	default boolean isAnalogSignalSource(SignalType type) {
		return false;
	}

	default int getAnalogSignal(Level level, BlockPos pos, SignalType type) {
		return type.min();
	}

	default boolean hasAnalogSignal(Level level, BlockPos pos, SignalType type) {
		return false;
	}

	default boolean isSignalConsumer(SignalType type) {
		return false;
	}

	default boolean isSignalConductor(Level level, BlockPos pos, SignalType type) {
		return false;
	}

	default boolean isWire() {
		return false;
	}

	default boolean isWire(WireType type) {
		return false;
	}

	default boolean shouldConnectToWire(Level level, BlockPos pos, ConnectionSide dir, WireType type) {
		return false;
	}
}
