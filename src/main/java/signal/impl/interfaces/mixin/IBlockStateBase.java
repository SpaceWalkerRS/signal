package signal.impl.interfaces.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface IBlockStateBase {

	default boolean signal$isRedstoneConductor(Level level, BlockPos pos) {
		return false;
	}
}
