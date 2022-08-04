package signal.api.signal.wire;

import net.minecraft.resources.ResourceLocation;

import signal.api.registry.SignalRegistry;
import signal.api.registry.SignalRegistryCallbacks;
import signal.api.signal.SignalTypes;

public class WireTypes {

	public static final SignalRegistry<WireType> REGISTRY = new SignalRegistry<>();

	public static final WireType REDSTONE = new BasicWireType(SignalTypes.REDSTONE);

	/**
	 * Register a custom wire type.
	 * For the id, it is customary to use your mod id as the namespace.
	 */
	public static void register(ResourceLocation id, WireType type) {
		SignalRegistryCallbacks.register(REGISTRY, id, type);
	}

	/**
	 * Schedule a callback that runs after all wire types have been registered.
	 */
	public static void postRegister(Runnable callback) {
		SignalRegistryCallbacks.postRegister(REGISTRY, callback);
	}

	public static boolean areCompatible(WireType a, WireType b) {
		return a.isCompatible(b) && b.isCompatible(a);
	}
}
