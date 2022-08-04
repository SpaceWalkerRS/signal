package signal.api.signal;

import net.minecraft.resources.ResourceLocation;

import signal.api.registry.SignalRegistry;
import signal.api.registry.SignalRegistryCallbacks;

public class SignalTypes {

	public static final SignalRegistry<SignalType> REGISTRY = new SignalRegistry<>();

	public static final SignalType ANY      = new SignalType(Integer.MIN_VALUE, Integer.MAX_VALUE);
	public static final SignalType REDSTONE = new BasicSignalType();

	/**
	 * Register a custom signal type.
	 * For the id, it is customary to use your mod id as the namespace.
	 */
	public static void register(ResourceLocation id, SignalType type) {
		SignalRegistryCallbacks.register(REGISTRY, id, type);
	}

	/**
	 * Schedule a callback that runs after all signal types have been registered.
	 */
	public static void postRegister(Runnable callback) {
		SignalRegistryCallbacks.postRegister(REGISTRY, callback);
	}
}
