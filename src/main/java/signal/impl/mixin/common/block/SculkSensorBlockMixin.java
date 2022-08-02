package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;

import signal.api.signal.block.AnalogSignalSource;
import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(SculkSensorBlock.class)
public class SculkSensorBlockMixin implements RedstoneSignalSource, AnalogSignalSource {

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return state.getValue(SculkSensorBlock.POWER);
	}

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, BlockState state, int min, int max) {
		SculkSensorPhase phase = SculkSensorBlock.getPhase(state);

		if (phase == SculkSensorPhase.ACTIVE) {
			BlockEntity blockEntity = level.getBlockEntity(pos);

			if (blockEntity instanceof SculkSensorBlockEntity) {
				SculkSensorBlockEntity sculkSensorBlockEntity = (SculkSensorBlockEntity)blockEntity;
				int frequency = sculkSensorBlockEntity.getLastVibrationFrequency();

				return AnalogSignalSource.getAnalogSignal(frequency, min, max);
			}
		}

		return min;
	}
}
