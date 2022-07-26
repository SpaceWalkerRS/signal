package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin implements RedstoneSignalSource {

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(SculkSensorBlock.POWER);
	}
}
