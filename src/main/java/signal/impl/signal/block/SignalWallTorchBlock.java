package signal.impl.signal.block;

import net.minecraft.world.level.block.RedstoneWallTorchBlock;

import signal.api.signal.SignalType;

public class SignalWallTorchBlock extends RedstoneWallTorchBlock {

	protected final SignalType signalType;

	public SignalWallTorchBlock(Properties properties, SignalType signalType) {
		super(properties);

		this.signalType = signalType;
	}

	@Override
	public SignalType getSignalType() {
		return signalType;
	}

	@Override
	public SignalType getConsumedSignalType() {
		return signalType;
	}
}
