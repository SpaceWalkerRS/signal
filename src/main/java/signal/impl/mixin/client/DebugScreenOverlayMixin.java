package signal.impl.mixin.client;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.block.SignalSource;
import signal.api.signal.wire.WireType;
import signal.api.signal.wire.WireTypes;
import signal.api.signal.wire.block.Wire;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

	@Inject(
		method = "getSystemInformation",
		locals = LocalCapture.CAPTURE_FAILHARD,
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getTags()Ljava/util/stream/Stream;"
		)
	)
	private void signal$addSignalAndWireTypeInformation(CallbackInfoReturnable<List<String>> cir, long maxMemory, long totalMemory, long freeMemory, long usedMemory, List<String> info, BlockPos pos, BlockState state) {
		if (state.isSignalSource(SignalTypes.ANY)) {
			SignalSource source = (SignalSource)state.getBlock();
			SignalType signalType = source.getSignalType();

			info.add("signal type: " + SignalTypes.getKey(signalType));

			if (state.isWire(WireTypes.ANY)) {
				Wire wire = (Wire)source;
				WireType wireType = wire.getWireType();

				info.add("wire type: " + WireTypes.getKey(wireType));
			}
		}
	}
}
