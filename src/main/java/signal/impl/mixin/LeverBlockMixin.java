package signal.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.impl.signal.block.RedstoneSignalSource;

@Mixin(LeverBlock.class)
public abstract class LeverBlockMixin extends FaceAttachedHorizontalDirectionalBlock implements RedstoneSignalSource {

	private LeverBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(LeverBlock.POWERED) ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(LeverBlock.POWERED) && getConnectedDirection(state) == dir ? getSignalType().max() : getSignalType().min();
	}
}
