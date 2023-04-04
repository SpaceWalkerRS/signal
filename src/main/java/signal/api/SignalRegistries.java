package signal.api;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import signal.SignalMod;
import signal.api.signal.SignalType;
import signal.api.signal.wire.WireType;

public class SignalRegistries {

	public static final Registry<SignalType> SIGNAL_TYPE = registerSimple("signal_type");
	public static final Registry<WireType>   WIRE_TYPE   = registerSimple("wire_type");

	private static <T> Registry<T> registerSimple(String key) {
		ResourceKey<Registry<T>> resourceKey = ResourceKey.createRegistryKey(new ResourceLocation(SignalMod.MOD_ID, key));
		return FabricRegistryBuilder.createSimple(resourceKey).buildAndRegister();
	}

	public static void bootstrap() { }
}
