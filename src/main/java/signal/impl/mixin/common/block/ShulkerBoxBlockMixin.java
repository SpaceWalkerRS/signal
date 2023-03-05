package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.BasicAnalogSignalSource;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin implements BasicAnalogSignalSource {

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		return BasicAnalogSignalSource.getAnalogSignalFromBlockEntity(level.getBlockEntity(pos), min, max);
	}
}
