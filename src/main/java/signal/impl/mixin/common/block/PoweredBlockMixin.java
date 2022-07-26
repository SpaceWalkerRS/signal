package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PoweredBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.redstone.RedstoneSignalSource;
import signal.impl.interfaces.mixin.IPoweredBlock;

@Mixin(PoweredBlock.class)
public class PoweredBlockMixin implements IPoweredBlock, RedstoneSignalSource {

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return getSignal();
	}

	@Override
	public int getSignal() {
		return getSignalType().max();
	}
}
