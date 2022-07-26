package signal.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Constant.Condition;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.level.block.PressurePlateBlock;

import signal.impl.signal.block.RedstoneSignalSource;

@Mixin(PressurePlateBlock.class)
public class PressurePlateBlockMixin implements RedstoneSignalSource {

	@ModifyConstant(
		method = "getSignalForState",
		constant = @Constant(
			intValue = 15
		)
	)
	private int modifyMaxOutputSignal(int fifteen) {
		return getSignalType().max();
	}

	@ModifyConstant(
		method = "getSignalForState",
		constant = @Constant(
			intValue = 0
		)
	)
	private int modifyMinOutputSignal(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "setSignalForState",
		constant = @Constant(
			expandZeroConditions = Condition.GREATER_THAN_ZERO
		)
	)
	private int modifyMinOutputSignal1(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "getSignalStrength",
		constant = @Constant(
			intValue = 15
		)
	)
	private int modifyMaxOutputSignal1(int fifteen) {
		return getSignalType().max();
	}

	@ModifyConstant(
		method = "getSignalStrength",
		constant = @Constant(
			intValue = 0
		)
	)
	private int modifyMinOutputSignal2(int zero) {
		return getSignalType().min();
	}
}
