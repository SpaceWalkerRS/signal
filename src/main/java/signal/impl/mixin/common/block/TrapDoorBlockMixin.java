package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TrapDoorBlock;

import signal.api.signal.block.redstone.RedstoneSignalConsumer;

@Mixin(TrapDoorBlock.class)
public class TrapDoorBlockMixin implements RedstoneSignalConsumer {

	@Redirect(
		method = "getStateForPlacement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean hasNeighborSignal(Level level, BlockPos pos) {
		return hasReceivedSignal(level, pos);
	}

	@Redirect(
		method = "neighborChanged",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean hasNeighborSignal2(Level level, BlockPos pos) {
		return hasReceivedSignal(level, pos);
	}
}
