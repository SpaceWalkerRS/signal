package signal.impl.signal.block;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.PressurePlateBlock;

import signal.api.signal.SignalType;

public class SignalPressurePlateBlock extends PressurePlateBlock {

	protected final SignalType signalType;

	public SignalPressurePlateBlock(Sensitivity sensitivity, Properties properties, SoundEvent soundOff, SoundEvent soundOn, SignalType signalType) {
		super(sensitivity, properties, soundOff, soundOn);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
