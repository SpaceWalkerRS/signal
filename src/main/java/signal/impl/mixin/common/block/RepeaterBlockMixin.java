package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Constant.Condition;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.redstone.RedstoneSignalConsumer;
import signal.api.signal.block.redstone.RedstoneSignalSource;
import signal.api.signal.wire.ConnectionSide;

@Mixin(RepeaterBlock.class)
public abstract class RepeaterBlockMixin extends DiodeBlock implements RedstoneSignalSource, RedstoneSignalConsumer {

	private RepeaterBlockMixin(Properties properties) {
		super(properties);
	}

	@ModifyConstant(
		method = "isLocked",
		constant = @Constant(
			expandZeroConditions = Condition.GREATER_THAN_ZERO
		)
	)
	private int signal$modifyMinSideInputSignal(int zero) {
		return getSignalType().min();
	}

	@Redirect(
		method = "isAlternateInput",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/RepeaterBlock;isDiode(Lnet/minecraft/world/level/block/state/BlockState;)Z"
		)
	)
	private boolean redirectIsDiode(BlockState state) {
		return RepeaterBlock.isDiode(state) && state.isSignalSource(getConsumedSignalType());
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return side.isAligned(state.getValue(RepeaterBlock.FACING).getAxis());
	}
}
