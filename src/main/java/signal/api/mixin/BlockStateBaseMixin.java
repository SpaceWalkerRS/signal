package signal.api.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockState;

import signal.SignalMod;
import signal.api.interfaces.mixin.IBlockBehaviour;
import signal.api.interfaces.mixin.IBlockStateBase;
import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

@Mixin(BlockStateBase.class)
public class BlockStateBaseMixin implements IBlockStateBase {

	@Shadow private Block getBlock() { return null; }
	@Shadow private BlockState asState() { return null; }

	@Inject(
		method = "isSignalSource",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateIsSignalSource(CallbackInfoReturnable<Boolean> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method BlockStateBase#isSignalSource is deprecated! Use IBlockStateBase#signalSource instead.");
		} else {
			SignalMod.LOGGER.warn("Method BlockStateBase#isSignalSource is deprecated! Use IBlockStateBase#signalSource instead.");
		}
	}

	@Inject(
		method = "hasAnalogOutputSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateHasAnalogOutputSignal(CallbackInfoReturnable<Boolean> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method BlockStateBase#hasAnalogOutputSignal is deprecated! Use IBlockStateBase#analogSignalSource instead.");
		} else {
			SignalMod.LOGGER.warn("Method BlockStateBase#hasAnalogOutputSignal is deprecated! Use IBlockStateBase#analogSignalSource instead.");
		}
	}

	@Inject(
		method = "isRedstoneConductor",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateIsRedstoneConductor(CallbackInfoReturnable<Boolean> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method BlockStateBase#isRedstoneConductor is deprecated! Use IBlockStateBase#signalConductor instead.");
		} else {
			SignalMod.LOGGER.warn("Method BlockStateBase#isRedstoneConductor is deprecated! Use IBlockStateBase#signalConductor instead.");
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
			throw new IllegalStateException("Method BlockStateBase#getSignal is deprecated! Use IBlockStateBase#getSignal instead.");
		} else {
			SignalMod.LOGGER.warn("Method BlockStateBase#getSignal is deprecated! Use IBlockStateBase#getSignal instead.");
		}
	}

	@Inject(
		method = "getDirectSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateGetDirectSignal(CallbackInfoReturnable<Integer> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method BlockStateBase#getDirectSignal is deprecated! Use IBlockStateBase#getDirectSignal instead.");
		} else {
			SignalMod.LOGGER.warn("Method BlockStateBase#getDirectSignal is deprecated! Use IBlockStateBase#getDirectSignal instead.");
		}
	}

	@Inject(
		method = "getAnalogOutputSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateGetAnalogOutputSignal(CallbackInfoReturnable<Integer> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method BlockStateBase#getAnalogOutputSignal is deprecated! Use IBlockStateBase#getAnalogSignal instead.");
		} else {
			SignalMod.LOGGER.warn("Method BlockStateBase#getAnalogOutputSignal is deprecated! Use IBlockStateBase#getAnalogSignal instead.");
		}
	}

	@Override
	public IBlockBehaviour getIBlock() {
		return (IBlockBehaviour)getBlock();
	}

	@Override
	public boolean signalSource() {
		return getIBlock().signalSource(asState());
	}

	@Override
	public boolean signalSource(SignalType types) {
		return getIBlock().signalSource(asState(), types);
	}

	@Override
	public int getSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return getIBlock().getSignal(level, pos, asState(), dir, type);
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return getIBlock().getDirectSignal(level, pos, asState(), dir, type);
	}

	@Override
	public boolean hasSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return getIBlock().hasSignal(level, pos, asState(), dir, type);
	}

	@Override
	public boolean hasDirectSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return getIBlock().hasDirectSignal(level, pos, asState(), dir, type);
	}

	@Override
	public boolean analogSignalSource() {
		return getIBlock().analogSignalSource(asState());
	}

	@Override
	public boolean analogSignalSource(SignalType types) {
		return getIBlock().analogSignalSource(asState(), types);
	}

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, SignalType type) {
		return getIBlock().getAnalogSignal(level, pos, asState(), type);
	}

	@Override
	public boolean hasAnalogSignal(Level level, BlockPos pos, SignalType type) {
		return getIBlock().hasAnalogSignal(level, pos, asState(), type);
	}

	@Override
	public boolean signalConsumer() {
		return getIBlock().signalConsumer(asState());
	}

	@Override
	public boolean signalConsumer(SignalType type) {
		return getIBlock().signalConsumer(asState(), type);
	}

	@Override
	public boolean signalConductor(Level level, BlockPos pos, SignalType type) {
		return getIBlock().signalConductor(level, pos, asState(), type);
	}

	@Override
	public boolean wire() {
		return getIBlock().wire(asState());
	}

	@Override
	public boolean wire(WireType type) {
		return getIBlock().wire(asState(), type);
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, ConnectionSide dir, WireType type) {
		return getIBlock().shouldConnectToWire(level, pos, asState(), dir, type);
	}
}
