package signal.impl.signal.block;

import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public abstract class SignalBasePressurePlateBlock extends BasePressurePlateBlock {

	protected final SignalType signalType;

	protected SignalBasePressurePlateBlock(Properties properties, BlockSetType type, SignalType signalType) {
		super(properties, type);

		SignalTypes.requireNotAny(signalType);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
