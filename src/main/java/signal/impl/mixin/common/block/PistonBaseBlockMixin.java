package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;

import signal.api.signal.block.SignalConsumer;

@Mixin(PistonBaseBlock.class)
public class PistonBaseBlockMixin implements SignalConsumer {

	@Redirect(
		method = "getNeighborSignal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;hasSignal(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"
		)
	)
	private boolean signal$hasSignal(Level level, BlockPos pos, Direction dir) {
		return level.hasSignalFrom(pos, dir, this);
	}
}
