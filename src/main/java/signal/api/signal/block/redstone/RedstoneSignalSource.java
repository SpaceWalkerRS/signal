package signal.api.signal.block.redstone;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.block.BasicSignalSource;

public interface RedstoneSignalSource extends BasicSignalSource {

	@Override
	default SignalType getSignalType() {
		return SignalTypes.REDSTONE;
	}
}
