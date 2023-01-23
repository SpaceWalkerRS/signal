package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalTypes;
import signal.api.signal.block.AnalogSignalSource;

@Mixin(ChestBlock.class)
public class ChestBlockMixin implements AnalogSignalSource {

	@Redirect(
		method = "isBlockedChestByBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private static boolean signal$isRedstoneConductor(BlockState state, BlockGetter level, BlockPos pos) {
		return level instanceof Level && state.isSignalConductor((Level)level, pos, SignalTypes.ANY);
	}

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		return AnalogSignalSource.getAnalogSignalFromContainer(ChestBlock.getContainer((ChestBlock)(Object)this, state, level, pos, false), min, max);
	}
}
