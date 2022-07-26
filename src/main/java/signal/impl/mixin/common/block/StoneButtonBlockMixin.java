package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.StoneButtonBlock;

import signal.api.signal.block.redstone.RedstoneSignalSource;

@Mixin(StoneButtonBlock.class)
public class StoneButtonBlockMixin implements RedstoneSignalSource {

}
