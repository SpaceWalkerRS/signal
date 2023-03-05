package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Constant.Condition;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.Redstone;

import signal.api.signal.block.BasicSignalSource;

@Mixin(BasePressurePlateBlock.class)
public abstract class BasePressurePlateBlockMixin implements BasicSignalSource {

	@Shadow private int getSignalForState(BlockState state) { return Redstone.SIGNAL_NONE; }
	@Shadow private void checkPressed(Entity entity, Level level, BlockPos pos, BlockState state, int signal) { }

	@ModifyConstant(
		method = "getShape",
		constant = @Constant(
			expandZeroConditions = Condition.GREATER_THAN_ZERO
		)
	)
	private int signal$modifyMinOutputSignal(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "tick",
		constant = @Constant(
			expandZeroConditions = Condition.GREATER_THAN_ZERO
		)
	)
	private int signal$modifyMinOutputSignal1(int zero) {
		return getSignalType().min();
	}

	@Inject(
		method = "entityInside",
		cancellable = true,
		at = @At(
			value = "HEAD"
		)
	)
	private void signal$modifyEntityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
		if (!level.isClientSide()) {
			int signal = getSignalForState(state);

			if (signal == getSignalType().min()) {
				checkPressed(entity, level, pos, state, signal);
			}
		}

		ci.cancel();
	}

	@ModifyConstant(
		method = "checkPressed",
		constant = @Constant(
			expandZeroConditions = Condition.GREATER_THAN_ZERO
		)
	)
	private int signal$modifyMinOutputSignal3(int zero) {
		return getSignalType().min();
	}

	@ModifyConstant(
		method = "onRemove",
		constant = @Constant(
			expandZeroConditions = Condition.GREATER_THAN_ZERO
		)
	)
	private int signal$modifyMinOutputSignal4(int zero) {
		return getSignalType().min();
	}

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return getSignalForState(state);
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return dir == Direction.UP ? getSignalForState(state) : getSignalType().min();
	}
}
