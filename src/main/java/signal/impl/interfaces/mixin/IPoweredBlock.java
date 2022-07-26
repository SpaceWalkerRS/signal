package signal.impl.interfaces.mixin;

public interface IPoweredBlock {

	/* impl in signal.impl.mixin.PoweredBlockMixin */
	default int getSignal() {
		return 0;
	}
}
