package signal.impl.signal.block;

import signal.api.signal.block.BasicAnalogSignalSource;

public class CakeHelper {

	public static int getAnalogSignal(int bites, int min, int max) {
		return BasicAnalogSignalSource.getAnalogSignal(Math.max(1, (max - min) / 7) * (7 - bites), min, max);
	}
}
