package signal.api.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.Block;

import signal.api.interfaces.mixin.IBlock;

@Mixin(Block.class)
public class BlockMixin implements IBlock {

}
