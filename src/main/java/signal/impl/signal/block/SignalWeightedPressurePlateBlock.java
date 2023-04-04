package signal.impl.signal.block;

import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public abstract class SignalWeightedPressurePlateBlock extends WeightedPressurePlateBlock {

	protected final SignalType signalType;

	protected SignalWeightedPressurePlateBlock(int maxWeight, Properties properties, BlockSetType type, SignalType signalType) {
		super(maxWeight, properties, type);

		SignalTypes.requireNotAny(signalType);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
