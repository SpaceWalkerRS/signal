package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.BasicAnalogSignalSource;
import signal.api.signal.block.BasicSignalConsumer;

@Mixin(CommandBlock.class)
public class CommandBlockMixin implements BasicAnalogSignalSource, BasicSignalConsumer {

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

	@Redirect(
		method = "setPlacedBy",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean signal$hasNeighborSignal2(Level level, BlockPos pos) {
		return hasNeighborSignal(level, pos);
	}

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (blockEntity instanceof CommandBlockEntity) {
			CommandBlockEntity commandBlockEntity = (CommandBlockEntity)blockEntity;
			return BasicAnalogSignalSource.getAnalogSignal(commandBlockEntity.getCommandBlock().getSuccessCount(), min, max);
		}

		return min;
	}
}
