package signal.api.signal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;

public interface AnalogSignalSource extends SignalSource {

	@Override
	default boolean analogSignalSource(BlockState state) {
		return true;
	}

	@Override
	default boolean analogSignalSource(BlockState state, SignalType type) {
		return is(type);
	}

	@Override
	default int getAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return is(type) ? getAnalogSignal(level, pos, state) : type.min();
	}

	@Override
	default boolean hasAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return getAnalogSignal(level, pos, state, type) > type.min();
	}

	default int getAnalogSignal(Level level, BlockPos pos, BlockState state) {
		return getSignalType().min();
	}
}
