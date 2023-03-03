package signal.api;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import signal.SignalMod;
import signal.api.signal.SignalType;
import signal.api.signal.wire.WireType;

public class SignalRegistries {

	public static final Registry<SignalType> SIGNAL_TYPE = FabricRegistryBuilder.createSimple(SignalType.class, new ResourceLocation(SignalMod.MOD_ID, "signal_type")).buildAndRegister();
	public static final Registry<WireType> WIRE_TYPE = FabricRegistryBuilder.createSimple(WireType.class, new ResourceLocation(SignalMod.MOD_ID, "wire_type")).buildAndRegister();

	public static void bootstrap() { }
}
