package signal.api.signal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.SignalBlockBehavior;
import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

/**
 * This interface represents a block that can emit analog signals of one type.
 * 
 * @author Space Walker
 */
public interface BasicAnalogSignalSource extends SignalBlockBehavior {

	@Override
	default boolean isAnalogSignalSource(BlockState state, SignalType type) {
		return getAnalogSignalType().is(type);
	}

	@Override
	default int getAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return isAnalogSignalSource(state, type) ? getAnalogSignal(level, pos, state, type.min(), type.max()) : type.min();
	}


	// override these methods to control the analog output signal of your block

	/**
	 * Returns the type of analog signal this block emits.
	 */
	default SignalType getAnalogSignalType() {
		return SignalTypes.ANY;
	}

	default int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		return min;
	}


	// helper methods for getting analog signals from containers

	public static int getAnalogSignalFromBlockEntity(BlockEntity blockEntity, int min, int max) {
		return blockEntity instanceof Container ? getAnalogSignalFromContainer((Container)blockEntity, min, max) : min;
	}

	public static int getAnalogSignalFromContainer(Container container, int min, int max) {
		if (container == null) {
			return min;
		}

		int stackCount = 0;
		float filled = 0.0F;

		for (int i = 0; i < container.getContainerSize(); i++) {
			ItemStack stack = container.getItem(i);

			if (!stack.isEmpty()) {
				int maxStackSize = Math.min(container.getMaxStackSize(), stack.getMaxStackSize());

				stackCount++;
				filled += (float)stack.getCount() / maxStackSize;
			}
		}

		filled /= container.getContainerSize();

		return min + Mth.floor(filled * (max - min - 1)) + (stackCount == 0 ? 0 : 1);
	}

	public static int getAnalogSignal(int level, int min, int max) {
		return Mth.clamp(min + level, min, max);
	}
}
