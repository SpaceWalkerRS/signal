package signal.impl.signal.block;

import net.minecraft.world.level.block.LeverBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;

public class SignalLeverBlock extends LeverBlock implements SignalSource {

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
