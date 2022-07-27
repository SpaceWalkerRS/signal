package signal.impl.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlockState;
import signal.api.signal.SignalTypes;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

	@Redirect(
		method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private static boolean isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos, MobCategory category, ServerLevel level) {
		return ((IBlockState)state).isSignalConductor(level, pos, SignalTypes.ANY);
	}

	@Redirect(
		method = "isSpawnPositionOk",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private static boolean isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return blockGetter instanceof Level && ((IBlockState)state).isSignalConductor((Level)blockGetter, pos, SignalTypes.ANY);
	}

	@Redirect(
		method = "isValidEmptySpawnBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isSignalSource()Z"
		)
	)
	private static boolean isSignalSource(BlockState state) {
		return ((IBlockState)state).isSignalSource(SignalTypes.ANY);
	}
}
