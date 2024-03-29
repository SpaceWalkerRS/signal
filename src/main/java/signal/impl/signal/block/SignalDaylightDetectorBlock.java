package signal.impl.signal.block;

import net.minecraft.world.level.block.DaylightDetectorBlock;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public class SignalDaylightDetectorBlock extends DaylightDetectorBlock {

	protected final SignalType signalType;

	public SignalDaylightDetectorBlock(Properties properties, SignalType signalType) {
		super(properties);

		SignalTypes.requireNotAny(signalType);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
