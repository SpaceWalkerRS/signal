package signal.impl.signal.block;

import signal.api.signal.SignalType;
import signal.api.signal.block.AnalogSignalSource;

public class CakeHelper {

	public static int getSignal(int bites, SignalType type) {
		int min = type.min();
		int max = type.max();
		int mul = Math.max(1, (max - min) / 7);

		return AnalogSignalSource.getAnalogSignal(mul * (7 - bites), type);
	}
}
