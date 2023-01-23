package signal.impl.signal.block;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;

import signal.api.signal.SignalType;

public abstract class SignalWeightedPressurePlateBlock extends WeightedPressurePlateBlock {

	protected final SignalType signalType;

	protected SignalWeightedPressurePlateBlock(int maxWeight, Properties properties, SoundEvent soundOff, SoundEvent soundOn, SignalType signalType) {
		super(maxWeight, properties, soundOff, soundOn);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
