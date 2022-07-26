package signal.impl.signal.block;

import net.minecraft.world.level.block.ButtonBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;

public abstract class SignalButtonBlock extends ButtonBlock implements SignalSource {

	protected final SignalType signalType;

	protected SignalButtonBlock(boolean sensitive, Properties properties, SignalType signalType) {
		super(sensitive, properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
