package signal.api.signal;

public class SignalHolder {

	private int signal;

	public SignalHolder() {
		this(SignalTypes.ANY.min());
	}

	public SignalHolder(int signal) {
		this.signal = signal;
	}

	public void set(int signal) {
		this.signal = signal;
	}

	public void increase(int signal) {
		if (signal > this.signal) {
			this.signal = signal;
		}
	}

	public void decrease(int signal) {
		if (signal < this.signal) {
			this.signal = signal;
		}
	}

	public int get() {
		return signal;
	}
}
