package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.AnalogSignalSource;

@Mixin(ComposterBlock.class)
public class ComposterBlockMixin implements AnalogSignalSource {

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		return AnalogSignalSource.getAnalogSignal(state.getValue(ComposterBlock.LEVEL), min, max);
	}
}
