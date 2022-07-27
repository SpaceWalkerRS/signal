package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlockState;
import signal.api.signal.SignalTypes;

@Mixin(EnderChestBlock.class)
public class EnderChestBlockMixin {

	@Redirect(
		method = "use",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos, BlockState blockState, Level level) {
		return ((IBlockState)state).isSignalConductor(level, pos, SignalTypes.ANY);
	}
}
