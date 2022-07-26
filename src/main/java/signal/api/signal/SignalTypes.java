package signal.api.signal;

import net.minecraft.resources.ResourceLocation;

import signal.api.registry.SignalRegistry;
import signal.api.registry.SignalRegistryCallbacks;
import signal.api.signal.redstone.RedstoneSignalType;

public class SignalTypes {

	public static final SignalRegistry<SignalType> REGISTRY = new SignalRegistry<>();

	public static final SignalType         ANY      = new SignalType();
	public static final RedstoneSignalType REDSTONE = new RedstoneSignalType();

	public static void register(ResourceLocation id, SignalType type) {
		SignalRegistryCallbacks.register(REGISTRY, id, type);
	}

	public static void postRegister(Runnable callback) {
		SignalRegistryCallbacks.postRegister(REGISTRY, callback);
	}
}
