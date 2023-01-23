package signal.api.signal;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import signal.api.SignalRegistries;

public class SignalTypes {

	public static final SignalType      ANY      = new SignalType(Integer.MIN_VALUE, Integer.MAX_VALUE);
	public static final BasicSignalType REDSTONE = register(new ResourceLocation("redstone"), new BasicSignalType());

	/**
	 * Register a custom signal type.
	 * For the id, it is customary to use your mod id as the namespace.
	 */
	public static <T extends SignalType> T register(ResourceLocation id, T type) {
		return Registry.register(SignalRegistries.SIGNAL_TYPE, id, type);
	}

	public static SignalType get(ResourceLocation id) {
		return SignalRegistries.SIGNAL_TYPE.get(id);
	}

	public static ResourceLocation getId(SignalType type) {
		return SignalRegistries.SIGNAL_TYPE.getKey(type);
	}

	public static void bootstrap() {

	}
}
