package signal.api.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.fabricmc.loader.impl.entrypoint.EntrypointUtils;
import net.minecraft.resources.ResourceLocation;

import signal.SignalMod;
import signal.api.SignalInitializer;
import signal.api.signal.SignalTypes;
import signal.api.signal.wire.WireTypes;

public class SignalRegistryCallbacks {

	private static final Map<SignalRegistry<?>, Map<CallbackType, Collection<Runnable>>> CALLBACKS = new HashMap<>();

	private static boolean locked = true;
	private static int runs = 0;

	public static <T> void register(SignalRegistry<T> registry, ResourceLocation id, T value) {
		add(registry, CallbackType.REGISTER, () -> registry.register(id, value));
	}

	public static void postRegister(SignalRegistry<?> registry, Runnable callback) {
		add(registry, CallbackType.POST_REGISTER, callback);
	}

	private static void add(SignalRegistry<?> registry, CallbackType type, Runnable callback) {
		if (!locked) {
			CALLBACKS.computeIfAbsent(registry, key -> new HashMap<>()).computeIfAbsent(type, key -> new LinkedList<>()).add(callback);
		}
	}

	private static Collection<Runnable> get(SignalRegistry<?> registry, CallbackType type) {
		return CALLBACKS.getOrDefault(registry, Collections.emptyMap()).getOrDefault(type, Collections.emptyList());
	}

	public static void run() {
		if (runs++ > 0) {
			if (SignalMod.DEBUG) {
				SignalMod.LOGGER.warn("Signal registry callbacks have been run more than once (" + runs + " times so far)!");
			} else {
				throw new IllegalStateException("Signal registry callbacks have been run more than once (" + runs + " times so far)!");
			}
		}

		locked = false;

		EntrypointUtils.invoke(SignalMod.MOD_ID, SignalInitializer.class, SignalInitializer::onInitializeSignal);

		run(SignalTypes.REGISTRY);
		run(WireTypes.REGISTRY);

		CALLBACKS.clear();

		locked = true;
	}

	public static void run(SignalRegistry<?> registry) {
		registry.unlock();

		for (Runnable callback : get(registry, CallbackType.REGISTER)) {
			callback.run();
		}

		registry.lock();

		for (Runnable callback : get(registry, CallbackType.POST_REGISTER)) {
			callback.run();
		}
	}

	private static enum CallbackType {
		REGISTER, POST_REGISTER
	}
}
