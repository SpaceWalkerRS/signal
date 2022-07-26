package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlockState;

@Mixin(RailBlock.class)
public class RailBlockMixin {

	@Redirect(
		method = "updateState",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isSignalSource()Z"
		)
	)
	private boolean isSignalSource(BlockState state) {
		return ((IBlockState)state).signalSource();
	}
}
