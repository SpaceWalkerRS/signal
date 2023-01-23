package signal.api.signal.block;

import signal.api.SignalBlockBehavior;
import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

/**
 * This interface represents a block that consumes a signal of some, or all, type(s).
 * 
 * @author Space Walker
 */
public interface SignalConsumer extends SignalBlockBehavior {

	@Override
	default boolean isSignalConsumer(SignalType type) {
		return getConsumedSignalType().is(type);
	}

	/**
	 * Returns the type of signal this block consumes.
	 */
	default SignalType getConsumedSignalType() {
		return SignalTypes.ANY;
	}
}
