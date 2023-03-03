package signal.api.signal.wire.block.redstone;

import signal.api.signal.wire.BasicWireType;
import signal.api.signal.wire.WireTypes;
import signal.api.signal.wire.block.Wire;

public interface RedstoneWire extends Wire {

	@Override
	default BasicWireType getWireType() {
		return WireTypes.REDSTONE;
	}
}
