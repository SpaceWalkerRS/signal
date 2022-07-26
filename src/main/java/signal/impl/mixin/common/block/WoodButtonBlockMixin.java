package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.WoodButtonBlock;

import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(WoodButtonBlock.class)
public class WoodButtonBlockMixin implements RedstoneSignalSource {

}
