package signal.impl.mixin.client;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.SignalRegistries;
import signal.api.signal.SignalType;
import signal.api.signal.SignalTypes;
import signal.api.signal.wire.WireType;
import signal.api.signal.wire.WireTypes;
import signal.util.Utils;

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
		Collection<SignalType> signalTypes = Utils.collectTypes(SignalRegistries.SIGNAL_TYPE, state::isSignalSource);
		Collection<WireType> wireTypes = Utils.collectTypes(SignalRegistries.WIRE_TYPE, state::isWire);

		if (!signalTypes.isEmpty()) {
			info.add(listTypes("signal type(s): ", signalTypes, SignalTypes::getKey));
		}
		if (!wireTypes.isEmpty()) {
			info.add(listTypes("wire type(s): ", wireTypes, WireTypes::getKey));
		}
	}

	private static <T> String listTypes(String name, Collection<T> types, Function<T, ResourceLocation> idGetter) {
		StringBuilder sb = new StringBuilder();

		for (T type : types) {
			if (!sb.isEmpty())
				sb.append(", ");
			sb.append(idGetter.apply(type));
		}

		return sb.insert(0, name).toString();
	}
}
