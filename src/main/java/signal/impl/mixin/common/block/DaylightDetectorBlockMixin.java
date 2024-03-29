package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DaylightDetectorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import signal.api.signal.SignalType;
import signal.api.signal.block.BasicSignalSource;
import signal.api.signal.block.SignalState;
import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(DaylightDetectorBlock.class)
public class DaylightDetectorBlockMixin implements RedstoneSignalSource, SignalState {

	@Redirect(
		method = "updateSignalStrength",
		at = @At(
			value = "INVOKE",
			ordinal = 1,
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
		)
	)
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> T signal$updateSignalStrengthGetSignal(BlockState state, Property<T> property) {
		return (T)(Integer)((SignalState)state.getBlock()).getSignal(state);
	}

	@Redirect(
		method = "updateSignalStrength",
		at = @At(
			value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
		)
	)
	private static <T extends Comparable<T>> Object signal$updateSignalStrengthSetSignal(BlockState state, Property<T> property, T signal) {
		return ((SignalState)state.getBlock()).setSignal(state, (Integer)signal);
	}

	@Redirect(
		method = "updateSignalStrength",
		at = @At(
			value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/util/Mth;clamp(III)I"
		)
	)
	private static int signal$modifyUpdateSignalStrength(int brightness, int min, int max, BlockState state, Level level, BlockPos pos) {
		brightness = Mth.clamp(brightness, min, max);
		float f = (float)(brightness - min) / (max - min);

		BasicSignalSource source = (BasicSignalSource)state.getBlock();
		SignalType type = source.getSignalType();

		return type.min() + (int)(f * (type.max() - type.min()));
	}

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return getSignal(state);
	}
}
