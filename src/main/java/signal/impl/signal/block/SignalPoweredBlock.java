package signal.impl.signal.block;

import net.minecraft.world.level.block.PoweredBlock;

import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;
import signal.impl.interfaces.mixin.IPoweredBlock;

public class SignalPoweredBlock extends PoweredBlock implements IPoweredBlock, SignalSource {

	protected final SignalType signalType;
	protected final int signal;

	public SignalPoweredBlock(Properties properties, SignalType signalType) {
		this(properties, signalType, signalType.max());
	}

	public SignalPoweredBlock(Properties properties, SignalType signalType, int signal) {
		super(properties);

		this.signalType = signalType;
		this.signal = this.signalType.clamp(signal); // clamp just in case...
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}

	@Override
	public int getSignal() {
		return signal;
	}
}
