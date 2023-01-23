package signal.impl.signal.block;

import net.minecraft.world.level.block.RepeaterBlock;

import signal.api.signal.SignalType;

public class SignalRepeaterBlock extends RepeaterBlock {

	protected final SignalType signalType;

	public SignalRepeaterBlock(Properties properties, SignalType signalType) {
		super(properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}

	@Override
	public SignalType getConsumedSignalType() {
		return signalType;
	}
}
