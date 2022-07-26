package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Constant.Condition;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.block.redstone.RedstoneSignalConsumer;
import signal.api.signal.block.redstone.RedstoneSignalSource;
import signal.api.signal.wire.ConnectionSide;

@Mixin(RepeaterBlock.class)
public class RepeaterBlockMixin implements RedstoneSignalSource, RedstoneSignalConsumer {

	@ModifyConstant(
		method = "isLocked",
		constant = @Constant(
			expandZeroConditions = Condition.GREATER_THAN_ZERO
		)
	)
	private int modifyMinSideInputSignal(int zero) {
		return getSignalType().min();
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, BlockState state, ConnectionSide side) {
		return side.isAligned(state.getValue(RepeaterBlock.FACING).getAxis());
	}
}
