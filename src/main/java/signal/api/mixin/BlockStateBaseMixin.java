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
public abstract class BlockStateBaseMixin implements IBlockStateBase {

	@Shadow private Block getBlock() { return null; }
	@Shadow private BlockState asState() { return null; }

	@Inject(
		method = "getSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void deprecateGetSignal(CallbackInfoReturnable<Integer> cir) {
		if (SignalMod.DEBUG) {
			throw new IllegalStateException("Method BlockStateBase#getSignal is deprecated! Use IBlockState#getSignal instead.");
		} else {
			SignalMod.LOGGER.warn("Method BlockStateBase#getSignal is deprecated! Use IBlockState#getSignal instead.");
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
			throw new IllegalStateException("Method BlockStateBase#getDirectSignal is deprecated! Use IBlockState#getDirectSignal instead.");
		} else {
			SignalMod.LOGGER.warn("Method BlockStateBase#getDirectSignal is deprecated! Use IBlockState#getDirectSignal instead.");
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
			throw new IllegalStateException("Method BlockStateBase#getAnalogOutputSignal is deprecated! Use IBlockState#getAnalogSignal instead.");
		} else {
			SignalMod.LOGGER.warn("Method BlockStateBase#getAnalogOutputSignal is deprecated! Use IBlockState#getAnalogSignal instead.");
		}
	}

	@Override
	public IBlockBehaviour getIBlock() {
		return (IBlockBehaviour)getBlock();
	}

	@Override
	public boolean isSignalSource() {
		return getIBlock().isSignalSource(asState());
	}

	@Override
	public boolean isSignalSource(SignalType types) {
		return getIBlock().isSignalSource(asState(), types);
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
	public boolean isAnalogSignalSource() {
		return getIBlock().isAnalogSignalSource(asState());
	}

	@Override
	public boolean isAnalogSignalSource(SignalType types) {
		return getIBlock().isAnalogSignalSource(asState(), types);
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
	public boolean isSignalConsumer() {
		return getIBlock().isSignalConsumer(asState());
	}

	@Override
	public boolean isSignalConsumer(SignalType type) {
		return getIBlock().isSignalConsumer(asState(), type);
	}

	@Override
	public boolean isSignalConductor(Level level, BlockPos pos, SignalType type) {
		return getIBlock().isSignalConductor(level, pos, asState(), type);
	}

	@Override
	public boolean isWire() {
		return getIBlock().isWire(asState());
	}

	@Override
	public boolean isWire(WireType type) {
		return getIBlock().isWire(asState(), type);
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, ConnectionSide dir, WireType type) {
		return getIBlock().shouldConnectToWire(level, pos, asState(), dir, type);
	}
}
