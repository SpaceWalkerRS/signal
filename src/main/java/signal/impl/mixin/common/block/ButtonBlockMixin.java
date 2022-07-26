package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.SignalSource;

@Mixin(ButtonBlock.class)
public abstract class ButtonBlockMixin extends FaceAttachedHorizontalDirectionalBlock implements SignalSource {

	private ButtonBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(ButtonBlock.POWERED) ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(ButtonBlock.POWERED) && getConnectedDirection(state) == dir ? getSignalType().max() : getSignalType().min();
	}
}
