package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin implements RedstoneSignalSource {

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(LightningRodBlock.POWERED) ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(LightningRodBlock.POWERED) && state.getValue(LightningRodBlock.FACING) == dir ? getSignalType().max() : getSignalType().min();
	}
}
