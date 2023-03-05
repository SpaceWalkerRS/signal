package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BigDripleafBlock;

import signal.api.signal.block.BasicSignalConsumer;

@Mixin(BigDripleafBlock.class)
public class BigDripleafBlockMixin implements BasicSignalConsumer {

	@Redirect(
		method = "entityInside",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean signal$hasNeighborSignal(Level level, BlockPos pos) {
		return hasNeighborSignal(level, pos);
	}

	@Redirect(
		method = "neighborChanged",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean signal$hasNeighborSignal2(Level level, BlockPos pos) {
		return hasNeighborSignal(level, pos);
	}

	@Redirect(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean signal$hasNeighborSignal(ServerLevel level, BlockPos pos) {
		return hasNeighborSignal(level, pos);
	}
}
