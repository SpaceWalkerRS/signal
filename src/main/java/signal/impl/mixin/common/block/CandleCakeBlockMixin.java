package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.AnalogSignalSource;
import signal.impl.signal.block.CakeHelper;

@Mixin(CandleCakeBlock.class)
public class CandleCakeBlockMixin implements AnalogSignalSource {

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		return CakeHelper.getSignal(0, min, max);
	}
}
