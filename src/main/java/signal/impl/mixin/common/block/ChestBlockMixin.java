package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlockState;
import signal.api.signal.SignalType;
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
	private static boolean isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return blockGetter instanceof Level && ((IBlockState)state).isSignalConductor((Level)blockGetter, pos, SignalTypes.ANY);
	}

	@Override
	public int calculateAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return AnalogSignalSource.getAnalogSignalFromContainer(ChestBlock.getContainer((ChestBlock)(Object)this, state, level, pos, false), type);
	}
}
