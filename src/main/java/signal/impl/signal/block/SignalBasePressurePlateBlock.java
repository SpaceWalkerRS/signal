package signal.impl.signal.block;

import net.minecraft.world.level.block.BasePressurePlateBlock;

import signal.api.signal.SignalType;

public abstract class SignalBasePressurePlateBlock extends BasePressurePlateBlock {

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
