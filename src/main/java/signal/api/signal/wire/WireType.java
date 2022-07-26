package signal.api.signal.wire;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import signal.api.signal.SignalType;

public abstract class WireType {

	protected final SignalType signal;
	protected final int min;
	protected final int max;
	protected final int step;

	protected WireType(SignalType signal, int min, int max, int step) {
		if (signal == null || signal.isAny()) {
			throw new IllegalArgumentException("signal type cannot be null or any!");
		}
		if (max < min) {
			throw new IllegalArgumentException("max cannot be less than min!");
		}
		if (min < signal.min() || min > signal.max()) {
			throw new IllegalArgumentException("min must be bound by signal type's min/max!");
		}
		if (max < signal.min() || max > signal.max()) {
			throw new IllegalArgumentException("max must be bound by signal type's min/max!");
		}
		if (step < 0) {
			throw new IllegalArgumentException("step cannot be less than 0!");
		}

		this.signal = signal;
		this.min = min;
		this.max = max;
		this.step = step;
	}

	@Override
	public final String toString() {
		return String.format("WireType[signal type: %s, min: %d, max: %d, step: %d]", signal, min, max, step);
	}

	public final SignalType signal() {
		return signal;
	}

	public final int min() {
		return min;
	}

	public final int max() {
		return max;
	}

	public final int step() {
		return step;
	}

	public final int clamp(int signal) {
		return Mth.clamp(signal, min, max);
	}

	public final boolean isCompatible(WireType type) {
		return signal.is(type.signal);
	}

	public abstract ConnectionType getConnection(Level level, BlockPos pos, ConnectionSide side);

}
