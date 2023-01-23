package signal.impl.mixin.common.block;

import java.util.List;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DetectorRailBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.AnalogSignalSource;
import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(DetectorRailBlock.class)
public class DetectorRailBlockMixin implements RedstoneSignalSource, AnalogSignalSource {

	@Shadow private <T extends AbstractMinecart> List<T> getInteractingMinecartOfType(Level level, BlockPos pos, Class<T> type, Predicate<Entity> predicate) { return null; }

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(DetectorRailBlock.POWERED) ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(DetectorRailBlock.POWERED) && dir == Direction.UP ? getSignalType().max() : getSignalType().min();
	}

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		if (!state.getValue(DetectorRailBlock.POWERED)) {
			return min;
		}

		List<MinecartCommandBlock> minecartCommandBlocks = getInteractingMinecartOfType(level, pos, MinecartCommandBlock.class, entity -> true);

		if (!minecartCommandBlocks.isEmpty()) {
			MinecartCommandBlock minecart = minecartCommandBlocks.get(0);
			BaseCommandBlock commandBlock = minecart.getCommandBlock();

			return AnalogSignalSource.getAnalogSignal(commandBlock.getSuccessCount(), min, max);
		}

		List<AbstractMinecart> minecarts = getInteractingMinecartOfType(level, pos, AbstractMinecart.class, EntitySelector.CONTAINER_ENTITY_SELECTOR);

		if (!minecarts.isEmpty()) {
			return AnalogSignalSource.getAnalogSignalFromContainer((Container)minecarts.get(0), min, max);
		}

		return min;
	}
}
