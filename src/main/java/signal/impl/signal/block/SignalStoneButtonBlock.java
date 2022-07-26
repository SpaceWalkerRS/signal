package signal.impl.signal.block;

import net.minecraft.world.level.block.StoneButtonBlock;
import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;

public class SignalStoneButtonBlock extends StoneButtonBlock implements SignalSource {

	protected final SignalType signalType;

	public SignalStoneButtonBlock(Properties properties, SignalType signalType) {
		super(properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
