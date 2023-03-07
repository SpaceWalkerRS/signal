package signal.impl.signal.block;

import net.minecraft.world.level.block.LeverBlock;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public class SignalLeverBlock extends LeverBlock {

	protected final SignalType signalType;

	public SignalLeverBlock(Properties properties, SignalType signalType) {
		super(properties);

		SignalTypes.requireNotAny(signalType);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
