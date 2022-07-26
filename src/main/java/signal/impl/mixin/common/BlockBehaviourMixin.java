package signal.impl.mixin.common;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;

import signal.api.IBlock;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin implements IBlock {

}
