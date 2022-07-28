package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.block.AnalogSignalSource;

@Mixin(RespawnAnchorBlock.class)
public class RespawnAnchorBlockMixin implements AnalogSignalSource {

	@Override
	public int calculateAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return type.min() + RespawnAnchorBlock.getScaledChargeLevel(state, type.max() - type.min());
	}
}
