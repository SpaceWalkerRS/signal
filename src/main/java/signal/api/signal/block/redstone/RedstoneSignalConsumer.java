package signal.api.signal.block.redstone;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.block.BasicSignalConsumer;

public interface RedstoneSignalConsumer extends BasicSignalConsumer {

	@Override
	default SignalType getConsumedSignalType() {
		return SignalTypes.REDSTONE;
	}
}
