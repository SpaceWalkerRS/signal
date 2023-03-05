package signal.impl.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
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
import signal.api.SignalLevel;
import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.wire.WireTypes;

@Mixin(Level.class)
public abstract class LevelMixin implements BlockGetter, SignalLevel {

	private static final Direction[] DIRECTIONS = Direction.values();

	private boolean ignoreWireSignals;

	@Redirect(
		method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;hasAnalogOutputSignal()Z"
		)
	)
	private boolean signal$hasAnalogOutputSignal(BlockState state) {
		return state.isAnalogSignalSource(SignalTypes.ANY);
	}

	@Redirect(
		method = "updateNeighbourForOutputSignal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean signal$isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return state.isSignalConductor(signal$asLevel(), pos, SignalTypes.ANY);
	}

	@Inject(
		method = "getDirectSignalTo",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateGetDirectSignalTo(CallbackInfoReturnable<Integer> cir) {
		SignalMod.deprecate("Method Level#getDirectSignalTo is deprecated! Use SignalLevel#getDirectSignal instead.");
	}

	@Inject(
		method = "hasSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateHasSignal(CallbackInfoReturnable<Boolean> cir) {
		SignalMod.deprecate("Method Level#hasSignal is deprecated! Use SignalLevel#hasSignalFrom instead.");
	}

	@Inject(
		method = "getSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateGetSignal(CallbackInfoReturnable<Integer> cir) {
		SignalMod.deprecate("Method Level#getSignal is deprecated! Use SignalLevel#getSignalFrom instead.");
	}

	@Inject(
		method = "hasNeighborSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateHasNeighborSignal(CallbackInfoReturnable<Boolean> cir) {
		SignalMod.deprecate("Method Level#hasNeighborSignal is deprecated! Use SignalLevel#hasSignal instead.");
	}

	@Inject(
		method = "getBestNeighborSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateGetBestNeighborSignal(CallbackInfoReturnable<Integer> cir) {
		SignalMod.deprecate("Method Level#getBestNeighborSignal is deprecated! Use SignalLevel#getSignal instead.");
	}

	@Override
	public void ignoreWireSignals(boolean ignore) {
		ignoreWireSignals = ignore;
	}

	@Override
	public int getSignal(BlockPos pos, SignalType type) {
		int signal = type.min();
		int max = type.max();

		for (Direction dir : DIRECTIONS) {
			signal = Math.max(signal, getSignalFrom(pos.relative(dir), dir, type));

			if (signal >= max) {
				return max;
			}
		}

		return signal;
	}

	@Override
	public int getDirectSignal(BlockPos pos, SignalType type) {
		int signal = type.min();
		int max = type.max();

		for (Direction dir : DIRECTIONS) {
			signal = Math.max(signal, getDirectSignalFrom(pos.relative(dir), dir, type));

			if (signal >= max) {
				return max;
			}
		}

		return signal;
	}

	@Override
	public int getSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		BlockState state = getBlockState(pos);

		int signal = type.min();
		int max = type.max();

		if (ignoreWireSignals && state.isWire(WireTypes.ANY)) {
			return signal;
		}

		signal = state.getSignal(signal$asLevel(), pos, dir, type);

		if (signal >= max) {
			return max;
		}

		if (state.isSignalConductor(signal$asLevel(), pos, type)) {
			signal = Math.max(signal, getDirectSignal(pos, type));
		}

		return type.clamp(signal);
	}

	@Override
	public int getDirectSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		BlockState state = getBlockState(pos);

		if (ignoreWireSignals && state.isWire(WireTypes.ANY)) {
			return type.min();
		}

		return type.clamp(state.getDirectSignal(signal$asLevel(), pos, dir, type));
	}

	@Override
	public boolean hasSignal(BlockPos pos, SignalType type) {
		for (Direction dir : DIRECTIONS) {
			if (hasSignalFrom(pos.relative(dir), dir, type)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasDirectSignal(BlockPos pos, SignalType type) {
		for (Direction dir : DIRECTIONS) {
			if (hasDirectSignalFrom(pos.relative(dir), dir, type)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		BlockState state = getBlockState(pos);

		if (ignoreWireSignals && state.isWire(WireTypes.ANY)) {
			return false;
		}
		if (state.hasSignal(signal$asLevel(), pos, dir, type)) {
			return true;
		}

		return state.isSignalConductor(signal$asLevel(), pos, type) && hasDirectSignal(pos, type);
	}

	@Override
	public boolean hasDirectSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		BlockState state = getBlockState(pos);

		if (ignoreWireSignals && state.isWire(WireTypes.ANY)) {
			return false;
		}

		return state.hasDirectSignal(signal$asLevel(), pos, dir, type);
	}

	private Level signal$asLevel() {
		return (Level)(Object)this;
	}
}
