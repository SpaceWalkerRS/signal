package signal.impl.signal.block;

import net.minecraft.world.level.block.LecternBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;

public class SignalLecternBlock extends LecternBlock implements SignalSource {

	protected final SignalType signalType;

	public SignalLecternBlock(Properties properties, SignalType signalType) {
		super(properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
