package signal.impl.signal.block;

import signal.api.signal.block.AnalogSignalSource;

public class CakeHelper {

	public static int getAnalogSignal(int bites, int min, int max) {
		return AnalogSignalSource.getAnalogSignal(Math.max(1, (max - min) / 7) * (7 - bites), min, max);
	}
}
