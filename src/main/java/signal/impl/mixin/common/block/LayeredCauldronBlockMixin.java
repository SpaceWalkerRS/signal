package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.BasicAnalogSignalSource;

@Mixin(LayeredCauldronBlock.class)
public class LayeredCauldronBlockMixin implements BasicAnalogSignalSource {

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		return BasicAnalogSignalSource.getAnalogSignal(state.getValue(LayeredCauldronBlock.LEVEL), min, max);
	}
}
