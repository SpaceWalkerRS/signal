package signal.impl.signal.block;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public abstract class SignalButtonBlock extends ButtonBlock {

	protected final SignalType signalType;

	protected SignalButtonBlock(Properties properties, BlockSetType type, int ticksToStayPressed, boolean arrowsCanPress, SignalType signalType) {
		super(properties, type, ticksToStayPressed, arrowsCanPress);

		SignalTypes.requireNotAny(signalType);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
