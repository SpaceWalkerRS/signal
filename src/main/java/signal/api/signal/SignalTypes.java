package signal.api.signal;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import signal.api.SignalRegistries;

public class SignalTypes {

	public static final SignalType      ANY      = new SignalType();
	public static final BasicSignalType REDSTONE = register("redstone", new BasicSignalType());

	private static <T extends SignalType> T register(String name, T type) {
		return register(new ResourceLocation(name), type);
	}

	/**
	 * Register a custom signal type.
	 * For the key, it is customary to use your mod id as the namespace.
	 */
	public static <T extends SignalType> T register(ResourceLocation key, T type) {
		return Registry.register(SignalRegistries.SIGNAL_TYPE, key, type);
	}

	/**
	 * Register a custom signal type.
	 */
	public static <T extends SignalType> T register(ResourceKey<SignalType> key, T type) {
		return Registry.register(SignalRegistries.SIGNAL_TYPE, key, type);
	}

	public static SignalType get(ResourceLocation key) {
		return SignalRegistries.SIGNAL_TYPE.get(key);
	}

	public static ResourceLocation getKey(SignalType type) {
		return SignalRegistries.SIGNAL_TYPE.getKey(type);
	}

	public static void requireNotAny(SignalType type) {
		if (type == ANY)
			throw new IllegalStateException("signal type ANY is not allowed!");
	}

	public static void requireBounded(SignalType type, int signal) {
		if (signal < type.min || signal > type.max)
			throw new IllegalStateException("signal " + signal + " is not bounded by type " + type);
	}

	public static void bootstrap() { }
}
