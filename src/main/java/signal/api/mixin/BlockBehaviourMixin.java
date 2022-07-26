package signal.api.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;

import signal.api.interfaces.mixin.IBlockBehaviour;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin implements IBlockBehaviour {

}
