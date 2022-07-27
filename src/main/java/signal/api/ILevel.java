package signal.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import signal.api.signal.block.SignalConsumer;

public interface ILevel {

	int getSignal(BlockPos pos, SignalConsumer consumer);

	int getDirectSignal(BlockPos pos, SignalConsumer consumer);

	int getSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer);

	int getDirectSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer);

	boolean hasSignal(BlockPos pos, SignalConsumer consumer);
	
	boolean hasDirectSignal(BlockPos pos, SignalConsumer consumer);

	boolean hasSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer);
	
	boolean hasDirectSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer);

}
