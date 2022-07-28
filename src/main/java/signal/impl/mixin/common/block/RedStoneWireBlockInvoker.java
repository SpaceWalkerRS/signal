package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

@Mixin(RedStoneWireBlock.class)
public interface RedStoneWireBlockInvoker {

	@Invoker VoxelShape calculateShape(BlockState state);

	@Invoker void spawnParticlesAlongLine(Level level, RandomSource random, BlockPos pos, Vec3 color, Direction dir1, Direction dir2, float f, float g);

}
