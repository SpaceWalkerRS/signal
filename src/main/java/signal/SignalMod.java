package signal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import signal.api.SignalRegistries;
import signal.api.signal.SignalTypes;
import signal.api.signal.wire.WireTypes;

public class SignalMod implements ModInitializer {

	public static final String MOD_ID = "signal";
	public static final String MOD_NAME = "Signal";
	public static final String MOD_VERSION = "0.1.0";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	public static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

	@Override
	public void onInitialize() {
		if (DEBUG) {
			LOGGER.warn("== RUNNING " + MOD_NAME + " IN DEBUG MODE ==");
		}

		SignalRegistries.bootstrap();
		SignalTypes.bootstrap();
		WireTypes.bootstrap();
	}

	public static void deprecate(String message) {
		if (DEBUG) {
			throw new IllegalStateException(message);
		}
	}
}
