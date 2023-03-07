package signal.impl.signal.block;

import net.minecraft.world.level.block.DetectorRailBlock;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public class SignalDetectorRailBlock extends DetectorRailBlock {

	protected final SignalType signalType;

	public SignalDetectorRailBlock(Properties properties, SignalType signalType) {
		super(properties);

		SignalTypes.requireNotAny(signalType);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
