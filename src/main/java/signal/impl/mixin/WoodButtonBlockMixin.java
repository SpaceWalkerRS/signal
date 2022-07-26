package signal.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.WoodButtonBlock;

import signal.impl.signal.block.RedstoneSignalSource;

@Mixin(WoodButtonBlock.class)
public class WoodButtonBlockMixin implements RedstoneSignalSource {

}
