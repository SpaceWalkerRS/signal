package signal.impl.signal.block;

import net.minecraft.world.level.block.DaylightDetectorBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;
import signal.api.signal.block.SignalState;

public class SignalDaylightDetectorBlock extends DaylightDetectorBlock implements SignalSource, SignalState {

	protected final SignalType signalType;

	public SignalDaylightDetectorBlock(Properties properties, SignalType signalType) {
		super(properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
