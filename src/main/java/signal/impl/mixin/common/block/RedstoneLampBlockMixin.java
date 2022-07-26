package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedstoneLampBlock;

import signal.api.signal.block.redstone.RedstoneSignalConsumer;

@Mixin(RedstoneLampBlock.class)
public class RedstoneLampBlockMixin implements RedstoneSignalConsumer {

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

	@Redirect(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean hasNeighborSignal(ServerLevel level, BlockPos pos) {
		return hasReceivedSignal(level, pos);
	}
}
