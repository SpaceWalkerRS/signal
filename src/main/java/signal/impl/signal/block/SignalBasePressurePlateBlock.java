package signal.impl.signal.block;

import net.minecraft.world.level.block.BasePressurePlateBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;

public abstract class SignalBasePressurePlateBlock extends BasePressurePlateBlock implements SignalSource {

	protected final SignalType signalType;

	protected SignalBasePressurePlateBlock(Properties properties, SignalType signalType) {
		super(properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
