package signal.impl.signal.block;

import net.minecraft.world.level.block.ComparatorBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalConsumer;
import signal.api.signal.block.SignalSource;
import signal.impl.interfaces.mixin.IDiodeBlock;

public class SignalComparatorBlock extends ComparatorBlock implements IDiodeBlock, SignalSource, SignalConsumer {

	protected final SignalType signalType;

	public SignalComparatorBlock(Properties properties, SignalType signalType) {
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
