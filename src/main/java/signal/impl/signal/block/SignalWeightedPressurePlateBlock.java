package signal.impl.signal.block;

import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;

public abstract class SignalWeightedPressurePlateBlock extends WeightedPressurePlateBlock implements SignalSource {

	protected final SignalType signalType;

	protected SignalWeightedPressurePlateBlock(int maxWeight, Properties properties, SignalType signalType) {
		super(maxWeight, properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}

	@Override
	protected int getSignalForState(BlockState state) {
		return getSignal(state);
	}

	@Override
	protected BlockState setSignalForState(BlockState state, int signal) {
		return setSignal(state, signal);
	}

	public abstract int getSignal(BlockState state);

	public abstract BlockState setSignal(BlockState state, int signal);

}
