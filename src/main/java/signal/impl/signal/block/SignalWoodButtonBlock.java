package signal.impl.signal.block;

import net.minecraft.world.level.block.WoodButtonBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;

public class SignalWoodButtonBlock extends WoodButtonBlock implements SignalSource {

	protected final SignalType signalType;

	public SignalWoodButtonBlock(Properties properties, SignalType signalType) {
		super(properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
