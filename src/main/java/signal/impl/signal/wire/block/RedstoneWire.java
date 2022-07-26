package signal.impl.signal.wire.block;

import signal.api.signal.wire.WireTypes;
import signal.api.signal.wire.block.Wire;
import signal.impl.signal.wire.RedstoneWireType;

public interface RedstoneWire extends Wire {

	@Override
	default RedstoneWireType getWireType() {
		return WireTypes.REDSTONE;
	}
}
