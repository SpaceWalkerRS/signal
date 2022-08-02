package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.AnalogSignalSource;

@Mixin(BeehiveBlock.class)
public class BeehiveBlockMixin implements AnalogSignalSource {

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		return AnalogSignalSource.getAnalogSignal(state.getValue(BeehiveBlock.HONEY_LEVEL), min, max);
	}
}
