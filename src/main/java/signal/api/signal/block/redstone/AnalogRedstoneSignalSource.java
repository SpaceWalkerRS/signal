package signal.api.signal.block.redstone;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.block.AnalogSignalSource;

public interface AnalogRedstoneSignalSource extends AnalogSignalSource {

	@Override
	default SignalType getAnalogSignalType() {
		return SignalTypes.REDSTONE;
	}
}
