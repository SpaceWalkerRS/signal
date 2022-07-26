package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TargetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(TargetBlock.class)
public class TargetBlockMixin implements RedstoneSignalSource {

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(BlockStateProperties.POWER);
	}
}
