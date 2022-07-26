package signal.api.signal.redstone;

import net.minecraft.world.level.redstone.Redstone;

import signal.api.signal.SignalType;

public class RedstoneSignalType extends SignalType {

	private static final int MIN = Redstone.SIGNAL_MIN;
	private static final int MAX = Redstone.SIGNAL_MAX;

	public RedstoneSignalType() {
		super(MIN, MAX);
	}

	public RedstoneSignalType(int min, int max) {
		super(min, max);

		if (min < MIN) {
			throw new IllegalArgumentException("min cannot be less than " + MIN + "!");
		}
		if (max > MAX) {
			throw new IllegalArgumentException("max cannot be more than " + MAX + "!");
		}
	}
}
