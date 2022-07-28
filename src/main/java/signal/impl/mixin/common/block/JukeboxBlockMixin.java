package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.block.AnalogSignalSource;

@Mixin(JukeboxBlock.class)
public class JukeboxBlockMixin implements AnalogSignalSource {

	@Override
	public int calculateAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (blockEntity instanceof JukeboxBlockEntity) {
			JukeboxBlockEntity jukeboxBlockEntity = (JukeboxBlockEntity)blockEntity;
			ItemStack stack = jukeboxBlockEntity.getRecord();
			Item item = stack.getItem();

			if (item instanceof RecordItem) {
				return AnalogSignalSource.getAnalogSignal(((RecordItem)item).getAnalogOutput(), type);	
			}
		}

		return type.min();
	}
}
