package signal.api.signal;

import net.minecraft.util.Mth;

public class SignalType {

	protected final int min;
	protected final int max;

	SignalType() {
		this.min = Integer.MIN_VALUE;
		this.max = Integer.MAX_VALUE;
	}

	public SignalType(int min, int max) {
		if (max < min) {
			throw new IllegalArgumentException("max cannot be less than min!");
		}

		this.min = min;
		this.max = max;
	}

	@Override
	public final String toString() {
		return String.format("SignalType[min: %d, max: %d] {%s}", min, max, SignalTypes.getKey(this));
	}

	public final int min() {
		return min;
	}

	public final int max() {
		return max;
	}

	public final int clamp(int signal) {
		return Mth.clamp(signal, min, max);
	}

	public final boolean is(SignalType type) {
		return this == type || this == SignalTypes.ANY || type == SignalTypes.ANY;
	}
}
