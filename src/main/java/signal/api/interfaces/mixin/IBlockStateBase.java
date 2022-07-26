package signal.api.interfaces.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

public interface IBlockStateBase {

	IBlockBehaviour getIBlock();

	default boolean signalSource() {
		return false;
	}

	default boolean signalSource(SignalType type) {
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

	default boolean analogSignalSource() {
		return false;
	}

	default boolean analogSignalSource(SignalType type) {
		return false;
	}

	default int getAnalogSignal(Level level, BlockPos pos, SignalType type) {
		return type.min();
	}

	default boolean hasAnalogSignal(Level level, BlockPos pos, SignalType type) {
		return false;
	}

	default boolean signalConsumer() {
		return false;
	}

	default boolean signalConsumer(SignalType type) {
		return false;
	}

	default boolean signalConductor(Level level, BlockPos pos) {
		return false;
	}

	default boolean signalConductor(Level level, BlockPos pos, SignalType type) {
		return false;
	}

	default boolean wire() {
		return false;
	}

	default boolean wire(WireType type) {
		return false;
	}

	default boolean shouldConnectToWire(Level level, BlockPos pos, ConnectionSide dir, WireType type) {
		return false;
	}
}
