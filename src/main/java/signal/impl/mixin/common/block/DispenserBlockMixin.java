package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.BasicAnalogSignalSource;
import signal.api.signal.block.BasicSignalConsumer;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin implements BasicAnalogSignalSource, BasicSignalConsumer {

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

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		return BasicAnalogSignalSource.getAnalogSignalFromBlockEntity(level.getBlockEntity(pos), min, max);
	}
}
