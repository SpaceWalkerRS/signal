package signal.api.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.interfaces.mixin.IBlockState;
import signal.api.interfaces.mixin.ILevel;
import signal.api.signal.SignalType;

@Mixin(Level.class)
public abstract class LevelMixin implements BlockGetter, ILevel {

	@Shadow @Final private static Direction[] DIRECTIONS;

	private boolean allowWireSignals = true;

	@Override
	public void setAllowWireSignals(boolean allowWireSignals) {
		this.allowWireSignals = allowWireSignals;
	}

	@Override
	public int getSignal(BlockPos pos, SignalType type) {
		int signal = type.min();

		for (Direction dir : DIRECTIONS) {
			signal = Math.max(signal, getSignalFrom(pos.relative(dir), dir, type));

			if (signal >= type.max()) {
				return type.max();
			}
		}

		return signal;
	}

	@Override
	public int getDirectSignal(BlockPos pos, SignalType type) {
		int signal = type.min();

		for (Direction dir : DIRECTIONS) {
			signal = Math.max(signal, getDirectSignalFrom(pos.relative(dir), dir, type));

			if (signal >= type.max()) {
				return type.max();
			}
		}

		return signal;
	}

	@Override
	public int getSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		BlockState state = getBlockState(pos);
		IBlockState istate = (IBlockState)state;

		if (!allowWireSignals && istate.isWire()) {
			return type.min();
		}

		int signal = istate.getSignal(asLevel(), pos, dir, type);

		if (signal >= type.max()) {
			return type.max();
		}

		if (istate.isSignalConductor(asLevel(), pos, type)) {
			signal = Math.max(signal, getDirectSignal(pos, type));
		}

		return type.clamp(signal);
	}

	@Override
	public int getDirectSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		BlockState state = getBlockState(pos);
		IBlockState istate = (IBlockState)state;

		if (!allowWireSignals && istate.isWire()) {
			return type.min();
		}

		return type.clamp(istate.getDirectSignal(asLevel(), pos, dir, type));
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
		IBlockState istate = (IBlockState)state;

		if (!allowWireSignals && istate.isWire()) {
			return false;
		}
		if (istate.hasSignal(asLevel(), pos, dir, type)) {
			return true;
		}
		if (!istate.isSignalConductor(asLevel(), pos, type)) {
			return false;
		}

		return hasDirectSignal(pos, type);
	}

	@Override
	public boolean hasDirectSignalFrom(BlockPos pos, Direction dir, SignalType type) {
		BlockState state = getBlockState(pos);
		IBlockState istate = (IBlockState)state;

		if (!allowWireSignals && istate.isWire()) {
			return false;
		}

		return istate.hasSignal(asLevel(), pos, dir, type);
	}

	private Level asLevel() {
		return (Level)(Object)this;
	}
}
