package signal.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TrappedChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import signal.impl.signal.block.RedstoneSignalSource;

@Mixin(TrappedChestBlock.class)
public class TrappedChestBlockMixin implements RedstoneSignalSource {

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return getSignalType().clamp(ChestBlockEntity.getOpenCount(level, pos));
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return dir == Direction.UP ? getSignal(level, pos, state, dir) : getSignalType().min();
	}
}
