package signal.impl.signal.block;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.ButtonBlock;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public abstract class SignalButtonBlock extends ButtonBlock {

	protected final SignalType signalType;

	protected SignalButtonBlock(Properties properties, int ticksToStayPressed, boolean arrowsCanPress, SoundEvent soundOff, SoundEvent soundOn, SignalType signalType) {
		super(properties, ticksToStayPressed, arrowsCanPress, soundOff, soundOn);

		SignalTypes.requireNotAny(signalType);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
