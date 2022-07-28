package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.block.AnalogSignalSource;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin implements AnalogSignalSource {

	@Override
	public int calculateAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return AnalogSignalSource.getAnalogSignalFromBlockEntity(level.getBlockEntity(pos), type);
	}
}
