package signal.api.signal.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlock;
import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;

public interface AnalogSignalSource extends IBlock {

	@Override
	default boolean isAnalogSignalSource(SignalType type) {
		return getAnalogSignalType().is(type);
	}

	@Override
	default int getAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return !type.isAny() && isAnalogSignalSource(type) ? calculateAnalogSignal(level, pos, state, type) : type.min();
	}

	@Override
	default boolean hasAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return getAnalogSignal(level, pos, state, type) > type.min();
	}

	/**
	 * Returns the type of analog signal this block emits.
	 */
	default SignalType getAnalogSignalType() {
		return SignalTypes.ANY;
	}


	// override this method to control the analog output signal of your block

	default int calculateAnalogSignal(Level level, BlockPos pos, BlockState state, SignalType type) {
		return type.min();
	}

	public static int getAnalogSignalFromBlockEntity(BlockEntity blockEntity, SignalType type) {
		return blockEntity instanceof Container ? getAnalogSignalFromContainer((Container)blockEntity, type) : type.min();
	}

	public static int getAnalogSignalFromContainer(Container container, SignalType type) {
		if (container == null) {
			return type.min();
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

		int min = type.min();
		int max = type.max();

		return min + Mth.floor(filled * (max - min - 1)) + (stackCount == 0 ? 0 : 1);
	}

	public static int getAnalogSignal(int level, SignalType type) {
		return type.clamp(type.min() + level);
	}
}
