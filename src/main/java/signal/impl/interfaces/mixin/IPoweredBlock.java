package signal.impl.interfaces.mixin;

public interface IPoweredBlock {

	/* impl in signal.impl.mixin.PoweredBlockMixin */
	default int signal$getSignal() {
		return 0;
	}
}
