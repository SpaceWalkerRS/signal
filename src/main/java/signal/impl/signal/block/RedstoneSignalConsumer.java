package signal.impl.signal.block;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.block.SignalConsumer;

public interface RedstoneSignalConsumer extends SignalConsumer {

	@Override
	default SignalType getConsumedSignalType() {
		return SignalTypes.REDSTONE;
	}
}
