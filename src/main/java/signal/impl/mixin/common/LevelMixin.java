package signal.impl.mixin.common;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.SignalMod;
import signal.api.IBlockState;
import signal.api.ILevel;
import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.block.SignalConsumer;

@Mixin(Level.class)
public abstract class LevelMixin implements BlockGetter, ILevel {

	@Shadow @Final private static Direction[] DIRECTIONS;

	@Redirect(
		method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;hasAnalogOutputSignal()Z"
		)
	)
	private boolean hasAnalogOutputSignal(BlockState state) {
		return ((IBlockState)state).isAnalogSignalSource(SignalTypes.ANY);
	}

	@Redirect(
		method = "updateNeighbourForOutputSignal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return ((IBlockState)state).isSignalConductor(asLevel(), pos, SignalTypes.ANY);
	}

	@Inject(
		method = "getDirectSignalTo",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateGetDirectSignalTo(CallbackInfoReturnable<Integer> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method Level#getDirectSignalTo is deprecated! Use ILevel#getDirectSignal instead.");
		} else {
			SignalMod.LOGGER.warn("Method Level#getDirectSignalTo is deprecated! Use ILevel#getDirectSignal instead.");
		}
	}

	@Inject(
		method = "hasSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateHasSignal(CallbackInfoReturnable<Boolean> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method Level#hasSignal is deprecated! Use ILevel#hasSignalFrom instead.");
		} else {
			SignalMod.LOGGER.warn("Method Level#hasSignal is deprecated! Use ILevel#hasSignalFrom instead.");
		}
	}

	@Inject(
		method = "getSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateGetSignal(CallbackInfoReturnable<Integer> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method Level#getSignal is deprecated! Use ILevel#getSignalFrom instead.");
		} else {
			SignalMod.LOGGER.warn("Method Level#getSignal is deprecated! Use ILevel#getSignalFrom instead.");
		}
	}

	@Inject(
		method = "hasNeighborSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateHasNeighborSignal(CallbackInfoReturnable<Boolean> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method Level#hasNeighborSignal is deprecated! Use ILevel#hasSignal instead.");
		} else {
			SignalMod.LOGGER.warn("Method Level#hasNeighborSignal is deprecated! Use ILevel#hasSignal instead.");
		}
	}

	@Inject(
		method = "getBestNeighborSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateGetBestNeighborSignal(CallbackInfoReturnable<Integer> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method Level#getBestNeighborSignal is deprecated! Use ILevel#getSignal instead.");
		} else {
			SignalMod.LOGGER.warn("Method Level#getBestNeighborSignal is deprecated! Use ILevel#getSignal instead.");
		}
	}

	@Override
	public int getSignal(BlockPos pos, SignalConsumer consumer) {
		SignalType type = consumer.getConsumedSignalType();
		int signal = type.min();

		for (Direction dir : DIRECTIONS) {
			signal = Math.max(signal, getSignalFrom(pos.relative(dir), dir, consumer));

			if (signal >= type.max()) {
				return type.max();
			}
		}

		return signal;
	}

	@Override
	public int getDirectSignal(BlockPos pos, SignalConsumer consumer) {
		SignalType type = consumer.getConsumedSignalType();
		int signal = type.min();

		for (Direction dir : DIRECTIONS) {
			signal = Math.max(signal, getDirectSignalFrom(pos.relative(dir), dir, consumer));

			if (signal >= type.max()) {
				return type.max();
			}
		}

		return signal;
	}

	@Override
	public int getSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer) {
		SignalType type = consumer.getConsumedSignalType();

		BlockState state = getBlockState(pos);
		IBlockState istate = (IBlockState)state;

		if (consumer.isWire() && istate.isWire()) {
			return type.min();
		}

		int signal = istate.getSignal(asLevel(), pos, dir, type);

		if (signal >= type.max()) {
			return type.max();
		}

		if (istate.isSignalConductor(asLevel(), pos, type)) {
			signal = Math.max(signal, getDirectSignal(pos, consumer));
		}

		return type.clamp(signal);
	}

	@Override
	public int getDirectSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer) {
		SignalType type = consumer.getConsumedSignalType();

		BlockState state = getBlockState(pos);
		IBlockState istate = (IBlockState)state;

		if (consumer.isWire() && istate.isWire()) {
			return type.min();
		}

		return type.clamp(istate.getDirectSignal(asLevel(), pos, dir, type));
	}

	@Override
	public boolean hasSignal(BlockPos pos, SignalConsumer consumer) {
		for (Direction dir : DIRECTIONS) {
			if (hasSignalFrom(pos.relative(dir), dir, consumer)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasDirectSignal(BlockPos pos, SignalConsumer consumer) {
		for (Direction dir : DIRECTIONS) {
			if (hasDirectSignalFrom(pos.relative(dir), dir, consumer)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer) {
		SignalType type = consumer.getConsumedSignalType();

		BlockState state = getBlockState(pos);
		IBlockState istate = (IBlockState)state;

		if (consumer.isWire() && istate.isWire()) {
			return false;
		}
		if (istate.hasSignal(asLevel(), pos, dir, type)) {
			return true;
		}
		if (!istate.isSignalConductor(asLevel(), pos, type)) {
			return false;
		}

		return hasDirectSignal(pos, consumer);
	}

	@Override
	public boolean hasDirectSignalFrom(BlockPos pos, Direction dir, SignalConsumer consumer) {
		SignalType type = consumer.getConsumedSignalType();

		BlockState state = getBlockState(pos);
		IBlockState istate = (IBlockState)state;

		if (consumer.isWire() && istate.isWire()) {
			return false;
		}

		return istate.hasDirectSignal(asLevel(), pos, dir, type);
	}

	private Level asLevel() {
		return (Level)(Object)this;
	}
}
