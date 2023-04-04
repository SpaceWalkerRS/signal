package signal.impl.signal.block;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public class SignalPressurePlateBlock extends PressurePlateBlock {

	protected final SignalType signalType;

	public SignalPressurePlateBlock(Sensitivity sensitivity, Properties properties, BlockSetType type, SignalType signalType) {
		super(sensitivity, properties, type);

		SignalTypes.requireNotAny(signalType);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
