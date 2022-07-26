package signal.impl.signal.block;

import net.minecraft.world.level.block.DiodeBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalConsumer;
import signal.api.signal.block.SignalSource;
import signal.impl.interfaces.mixin.IDiodeBlock;

public abstract class SignalDiodeBlock extends DiodeBlock implements IDiodeBlock, SignalSource, SignalConsumer {

	protected final SignalType signalType;

	protected SignalDiodeBlock(Properties properties, SignalType signalType) {
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
