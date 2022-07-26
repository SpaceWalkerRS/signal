package signal.api.signal.wire.block.redstone;

import signal.api.signal.wire.WireType;
import signal.api.signal.wire.WireTypes;
import signal.api.signal.wire.block.Wire;

public interface RedstoneWire extends Wire {

	@Override
	default WireType getWireType() {
		return WireTypes.REDSTONE;
	}
}
