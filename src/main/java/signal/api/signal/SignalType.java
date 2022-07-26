package signal.api.signal;

import net.minecraft.util.Mth;

public class SignalType {

	protected final int min;
	protected final int max;

	public SignalType(int min, int max) {
		if (max < min) {
			throw new IllegalArgumentException("max cannot be less than min!");
		}

		this.min = min;
		this.max = max;
	}

	@Override
	public final String toString() {
		return String.format("SignalType[min: %d, max: %d]", min, max);
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

	public final boolean isAny() {
		return this == SignalTypes.ANY;
	}

	public final boolean is(SignalType type) {
		return isAny() || type.isAny() || this == type;
	}
}
