package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.block.AnalogSignalSource;
import signal.impl.signal.block.CakeHelper;

@Mixin(CakeBlock.class)
public class CakeBlockMixin implements AnalogSignalSource {

	@Override
	public int calculateAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return CakeHelper.getSignal(state.getValue(CakeBlock.BITES), type);
	}
}
