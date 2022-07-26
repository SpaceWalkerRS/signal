package signal.impl.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.StoneButtonBlock;

import signal.impl.signal.block.RedstoneSignalSource;

@Mixin(StoneButtonBlock.class)
public class StoneButtonBlockMixin implements RedstoneSignalSource {

}
