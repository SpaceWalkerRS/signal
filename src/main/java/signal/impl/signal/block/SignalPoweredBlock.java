package signal.impl.signal.block;

import net.minecraft.world.level.block.PoweredBlock;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public class SignalPoweredBlock extends PoweredBlock {

	protected final SignalType signalType;
	protected final int signal;

	public SignalPoweredBlock(Properties properties, SignalType signalType) {
		this(properties, signalType, signalType.max());
	}

	public SignalPoweredBlock(Properties properties, SignalType signalType, int signal) {
		super(properties);

		SignalTypes.requireNotAny(signalType);
		SignalTypes.requireBounded(signalType, signal);

		this.signalType = signalType;
		this.signal = signal;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}

	public int getSignal() {
		return signal;
	}
}
