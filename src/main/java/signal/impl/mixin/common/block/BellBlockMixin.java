package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;

import signal.api.signal.block.BasicSignalConsumer;

@Mixin(BellBlock.class)
public class BellBlockMixin implements BasicSignalConsumer {

	@Redirect(
		method = "neighborChanged",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean signal$hasNeighborSignal(Level level, BlockPos pos) {
		return hasNeighborSignal(level, pos);
	}
}
