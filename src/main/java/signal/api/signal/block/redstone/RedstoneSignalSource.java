package signal.api.signal.block.redstone;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.block.SignalSource;

public interface RedstoneSignalSource extends SignalSource {

	@Override
	default SignalType getSignalType() {
		return SignalTypes.REDSTONE;
	}
}
