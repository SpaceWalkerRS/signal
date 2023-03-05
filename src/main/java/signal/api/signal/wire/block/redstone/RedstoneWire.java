package signal.api.signal.wire.block.redstone;

import signal.api.signal.wire.BasicWireType;
import signal.api.signal.wire.WireTypes;
import signal.api.signal.wire.block.BasicWire;

public interface RedstoneWire extends BasicWire {

	@Override
	default BasicWireType getWireType() {
		return WireTypes.REDSTONE;
	}
}
