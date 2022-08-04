package signal.api.signal;

public class BasicSignalType extends SignalType {

	private static final int MIN = 0;
	private static final int MAX = 15;

	public BasicSignalType() {
		super(MIN, MAX);
	}

	public BasicSignalType(int min, int max) {
		super(min, max);

		if (min < MIN) {
			throw new IllegalArgumentException("min cannot be less than " + MIN + "!");
		}
		if (max > MAX) {
			throw new IllegalArgumentException("max cannot be more than " + MAX + "!");
		}
	}
}
