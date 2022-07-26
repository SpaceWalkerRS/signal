package signal.api.signal.wire;

import net.minecraft.resources.ResourceLocation;

import signal.api.registry.SignalRegistry;
import signal.api.registry.SignalRegistryCallbacks;
import signal.api.signal.SignalTypes;
import signal.api.signal.wire.redstone.RedstoneWireType;

public class WireTypes {

	public static final SignalRegistry<WireType> REGISTRY = new SignalRegistry<>();

	public static final RedstoneWireType REDSTONE = new RedstoneWireType(SignalTypes.REDSTONE);

	public static void register(ResourceLocation id, WireType type) {
		SignalRegistryCallbacks.register(REGISTRY, id, type);
	}

	public static void postRegister(Runnable callback) {
		SignalRegistryCallbacks.postRegister(REGISTRY, callback);
	}

	private static boolean enabled;

	public static void enableAll() {
		enabled = true;
	}

	public static void disableAll() {
		enabled = false;
	}

	public static boolean enabled() {
		return enabled;
	}
}
