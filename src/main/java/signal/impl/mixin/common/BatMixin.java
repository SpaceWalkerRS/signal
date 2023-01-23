package signal.impl.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalTypes;

@Mixin(Bat.class)
public abstract class BatMixin extends Entity {

	private BatMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Redirect(
		method = "customServerAiStep",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean signal$isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return state.isSignalConductor(level, pos, SignalTypes.ANY);
	}
}
