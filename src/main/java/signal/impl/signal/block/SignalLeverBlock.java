package signal.impl.signal.block;

import net.minecraft.world.level.block.LeverBlock;

import signal.api.signal.SignalType;

public class SignalLeverBlock extends LeverBlock {

	protected final SignalType signalType;

	public SignalLeverBlock(Properties properties, SignalType signalType) {
		super(properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
