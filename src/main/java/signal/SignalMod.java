package signal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import signal.api.SignalInitializer;
import signal.api.registry.SignalRegistryCallbacks;
import signal.api.signal.SignalTypes;
import signal.api.signal.wire.WireTypes;

public class SignalMod implements ModInitializer, SignalInitializer {

	public static final String MOD_ID = "signal";
	public static final String MOD_NAME = "Signal";
	public static final String MOD_VERSION = "1.0.0";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	public static final boolean DEBUG = true;

	@Override
	public void onInitialize() {
		SignalRegistryCallbacks.run();

		if (DEBUG) {
			LOGGER.warn("== RUNNING DEBUG VERSION OF " + MOD_NAME + " ==");
		}
	}

	@Override
	public void onInitializeSignal() {
		SignalTypes.register(new ResourceLocation("redstone"), SignalTypes.REDSTONE);
		WireTypes.register(new ResourceLocation("redstone"), WireTypes.REDSTONE);
	}
}
