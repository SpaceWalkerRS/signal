package signal.impl.signal.block;

import net.minecraft.world.level.block.RedstoneTorchBlock;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public class SignalTorchBlock extends RedstoneTorchBlock {

	protected final SignalType signalType;

	public SignalTorchBlock(Properties properties, SignalType signalType) {
		super(properties);

		SignalTypes.requireNotAny(signalType);

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
