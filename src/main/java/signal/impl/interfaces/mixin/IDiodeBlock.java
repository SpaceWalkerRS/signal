package signal.impl.interfaces.mixin;

import net.minecraft.world.level.block.state.BlockState;

public interface IDiodeBlock {

	/* impl in signal.impl.mixin.DiodeBlockMixin */
	default boolean isCompatibleSideInput(BlockState state) {
		return false;
	}
}
