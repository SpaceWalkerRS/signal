package signal.impl.mixin.common;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockBehaviour.StatePredicate;
import net.minecraft.world.level.block.state.BlockState;

import signal.SignalMod;
import signal.api.SignalBlockStateBase;
import signal.impl.interfaces.mixin.IBlockStateBase;

@Mixin(BlockStateBase.class)
public class BlockStateBaseMixin implements SignalBlockStateBase, IBlockStateBase {

	@Shadow @Final private StatePredicate isRedstoneConductor;

	@Shadow private Block getBlock() { return null; }
	@Shadow private BlockState asState() { return null; }

	@Inject(
		method = "isSignalSource",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateIsSignalSource(CallbackInfoReturnable<Boolean> cir) {
		SignalMod.deprecate("Method BlockStateBase#isSignalSource is deprecated! Use SignalBlockStateBase#isSignalSource instead.");
	}

	@Inject(
		method = "hasAnalogOutputSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateHasAnalogOutputSignal(CallbackInfoReturnable<Boolean> cir) {
		SignalMod.deprecate("Method BlockStateBase#hasAnalogOutputSignal is deprecated! Use SignalBlockStateBase#isAnalogSignalSource instead.");
	}

	@Inject(
		method = "isRedstoneConductor",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateIsRedstoneConductor(CallbackInfoReturnable<Boolean> cir) {
		SignalMod.deprecate("Method BlockStateBase#isRedstoneConductor is deprecated! Use SignalBlockStateBase#isSignalConductor instead.");
	}

	@Inject(
		method = "getSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateGetSignal(CallbackInfoReturnable<Integer> cir) {
		SignalMod.deprecate("Method BlockStateBase#getSignal is deprecated! Use SignalBlockStateBase#getSignal instead.");
	}

	@Inject(
		method = "getDirectSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateGetDirectSignal(CallbackInfoReturnable<Integer> cir) {
		SignalMod.deprecate("Method BlockStateBase#getDirectSignal is deprecated! Use SignalBlockStateBase#getDirectSignal instead.");
	}

	@Inject(
		method = "getAnalogOutputSignal",
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$deprecateGetAnalogOutputSignal(CallbackInfoReturnable<Integer> cir) {
		SignalMod.deprecate("Method BlockStateBase#getAnalogOutputSignal is deprecated! Use SignalBlockStateBase#getAnalogSignal instead.");
	}

	@Override
	public BlockState signal$asState() {
		return asState();
	}

	@Override
	public Block signal$getBlock() {
		return getBlock();
	}

	@Override
	public boolean signal$isRedstoneConductor(Level level, BlockPos pos) {
		return isRedstoneConductor.test(asState(), level, pos);
	}
}
