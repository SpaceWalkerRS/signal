package signal.impl.signal.block;

import net.minecraft.world.level.block.DetectorRailBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.AnalogSignalSource;
import signal.api.signal.block.SignalSource;

public class SignalDetectorRailBlock extends DetectorRailBlock implements SignalSource, AnalogSignalSource {

	protected final SignalType type;

	public SignalDetectorRailBlock(Properties properties, SignalType type) {
		super(properties);

		this.type = type;
	}

	@Override
	public SignalType getSignalType() {
		return type;
	}
}
