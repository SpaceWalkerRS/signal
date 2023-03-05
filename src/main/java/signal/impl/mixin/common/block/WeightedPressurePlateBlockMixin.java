package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.block.WeightedPressurePlateBlock;

import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(WeightedPressurePlateBlock.class)
public class WeightedPressurePlateBlockMixin implements RedstoneSignalSource {

	@ModifyConstant(
		method = "getSignalStrength",
		constant = @Constant(
			floatValue = 15.0F
		)
	)
	private float signal$modifyMaxOutputSignal(float fifteen) {
		return getSignalType().max() - getSignalType().min();
	}

	@Inject(
		method = "getSignalStrength",
		cancellable = true,
		at = @At(
			value = "RETURN"
		)
	)
	private void signal$modifyOutputSignal(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(cir.getReturnValue() - getSignalType().min());
	}
}
