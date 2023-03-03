package signal.api.signal.wire;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import signal.api.SignalRegistries;
import signal.api.signal.SignalTypes;

public class WireTypes {

	public static final WireType      ANY      = new WireType();
	public static final BasicWireType REDSTONE = register("redstone", new BasicWireType(SignalTypes.REDSTONE));

	private static <T extends WireType> T register(String name, T type) {
		return register(new ResourceLocation(name), type);
	}

	/**
	 * Register a custom wire type.
	 * For the key, it is customary to use your mod id as the namespace.
	 */
	public static <T extends WireType> T register(ResourceLocation key, T type) {
		return Registry.register(SignalRegistries.WIRE_TYPE, key, type);
	}

	/**
	 * Register a custom wire type.
	 */
	public static <T extends WireType> T register(ResourceKey<WireType> key, T type) {
		return Registry.register(SignalRegistries.WIRE_TYPE, key, type);
	}

	public static WireType get(ResourceLocation key) {
		return SignalRegistries.WIRE_TYPE.get(key);
	}

	public static ResourceLocation getKey(WireType type) {
		return SignalRegistries.WIRE_TYPE.getKey(type);
	}

	public static boolean areCompatible(WireType a, WireType b) {
		return a.isCompatible(b) && b.isCompatible(a);
	}

	public static void bootstrap() { }
}
