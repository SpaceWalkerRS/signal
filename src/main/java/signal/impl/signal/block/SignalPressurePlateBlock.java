package signal.impl.signal.block;

import net.minecraft.world.level.block.PressurePlateBlock;
import signal.api.signal.SignalType;
import signal.api.signal.block.SignalSource;

public class SignalPressurePlateBlock extends PressurePlateBlock implements SignalSource {

	protected final SignalType signalType;

	public SignalPressurePlateBlock(Sensitivity sensitivity, Properties properties, SignalType signalType) {
		super(sensitivity, properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}
}
