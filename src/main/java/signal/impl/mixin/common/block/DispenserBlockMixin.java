package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.block.AnalogSignalSource;
import signal.api.signal.block.SignalConsumer;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin implements AnalogSignalSource, SignalConsumer {

	@Redirect(
		method = "neighborChanged",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean hasNeighborSignal(Level level, BlockPos pos) {
		return hasReceivedSignal(level, pos);
	}

	@Override
	public int calculateAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return AnalogSignalSource.getAnalogSignalFromBlockEntity(level.getBlockEntity(pos), type);
	}
}
