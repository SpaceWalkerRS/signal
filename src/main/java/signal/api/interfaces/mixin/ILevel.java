package signal.api.interfaces.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import signal.api.signal.SignalType;

public interface ILevel {

	void setAllowWireSignals(boolean allowWireSignals);

	int getSignal(BlockPos pos, SignalType type);

	int getDirectSignal(BlockPos pos, SignalType type);

	int getSignalFrom(BlockPos pos, Direction dir, SignalType type);

	int getDirectSignalFrom(BlockPos pos, Direction dir, SignalType type);

	boolean hasSignal(BlockPos pos, SignalType type);
	
	boolean hasDirectSignal(BlockPos pos, SignalType type);

	boolean hasSignalFrom(BlockPos pos, Direction dir, SignalType type);
	
	boolean hasDirectSignalFrom(BlockPos pos, Direction dir, SignalType type);

}
