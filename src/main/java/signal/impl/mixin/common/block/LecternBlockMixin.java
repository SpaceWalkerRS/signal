package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.block.AnalogSignalSource;
import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(LecternBlock.class)
public class LecternBlockMixin implements RedstoneSignalSource, AnalogSignalSource {

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(LecternBlock.POWERED) ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(LecternBlock.POWERED) && dir == Direction.UP ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public int calculateAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (blockEntity instanceof LecternBlockEntity) {
			LecternBlockEntity lecternBlockEntity = (LecternBlockEntity)blockEntity;
			ItemStack book = lecternBlockEntity.getBook();

			int pageCount = WrittenBookItem.getPageCount(book);
			int page = lecternBlockEntity.getPage();

			int min = type.min();
			int max = type.max();

			return min + Mth.floor(((float)page / pageCount) * (max - min - 1)) + (lecternBlockEntity.hasBook() ? 1 : 0);
		}

		return type.min();
	}
}
