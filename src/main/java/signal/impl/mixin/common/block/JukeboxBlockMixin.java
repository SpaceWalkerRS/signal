package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.BasicAnalogSignalSource;
import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(JukeboxBlock.class)
public class JukeboxBlockMixin implements RedstoneSignalSource, BasicAnalogSignalSource {

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (blockEntity instanceof JukeboxBlockEntity jukeboxBlockEntity) {
			if (jukeboxBlockEntity.isRecordPlaying()) {
				return getSignalType().max();
			}
		}

		return getSignalType().min();
	}

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (blockEntity instanceof JukeboxBlockEntity jukeboxBlockEntity) {
			ItemStack stack = jukeboxBlockEntity.getFirstItem();

			if (stack.getItem() instanceof RecordItem record) {
				return BasicAnalogSignalSource.getAnalogSignal(record.getAnalogOutput(), min, max);
			}
		}

		return min;
	}
}
