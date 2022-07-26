package signal.api.signal.wire;

public enum ConnectionType {

	NONE(false, false),
	OUT (true , false),
	IN  (false, true),
	BOTH(true , true);

	private static final int out = 0b01;
	private static final int in  = 0b10;

	private static final ConnectionType[] ALL;

	static {

		ConnectionType[] values = values();
		ALL = new ConnectionType[values.length];

		for (ConnectionType type : values) {
			ALL[type.flags] = type;
		}
	}

	private final int flags;

	private ConnectionType(boolean isOut, boolean isIn) {
		int f = 0;

		if (isOut) f |= out;
		if (isIn) f |= in;

		this.flags = f;
	}

	public boolean out() {
		return (flags & out) != 0;
	}

	public boolean in() {
		return (flags & in) != 0;
	}

	public ConnectionType inverse() {
		return ALL[((flags << 1) | (flags >> 1)) & 0b11];
	}

	public ConnectionType or(ConnectionType type) {
		return ALL[flags | type.flags];
	}

	public ConnectionType and(ConnectionType type) {
		return ALL[flags & type.flags];
	}
}
