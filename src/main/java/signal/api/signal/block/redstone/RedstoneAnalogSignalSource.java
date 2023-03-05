package signal.api.signal.block.redstone;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.block.BasicAnalogSignalSource;

public interface RedstoneAnalogSignalSource extends BasicAnalogSignalSource {

	@Override
	default SignalType getAnalogSignalType() {
		return SignalTypes.REDSTONE;
	}
}
