package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.BasicAnalogSignalSource;

@Mixin(ChiseledBookShelfBlock.class)
public class ChiseledBookShelfBlockMixin implements BasicAnalogSignalSource {

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		if (!level.isClientSide()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);

			if (blockEntity instanceof ChiseledBookShelfBlockEntity) {
				ChiseledBookShelfBlockEntity bookShelf = (ChiseledBookShelfBlockEntity) blockEntity;
				return BasicAnalogSignalSource.getAnalogSignal(bookShelf.getLastInteractedSlot() + 1, min, max);
			}
		}

		return min;
	}
}
