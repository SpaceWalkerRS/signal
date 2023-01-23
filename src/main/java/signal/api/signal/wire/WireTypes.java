package signal.api.signal.wire;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import signal.api.SignalRegistries;
import signal.api.signal.SignalTypes;

public class WireTypes {

	public static final WireType      ANY      = new WireType(SignalTypes.ANY, SignalTypes.ANY.min(), SignalTypes.ANY.max(), 0) {

		@Override
		public void findPotentialConnections(Level level, BlockPos pos, PotentialConnectionConsumer consumer) {

		}

		@Override
		public ConnectionType getPotentialConnection(Level level, BlockPos pos, ConnectionSide side) {
			return ConnectionType.NONE;
		}
	};
	public static final BasicWireType REDSTONE = register(new ResourceLocation("redstone"), new BasicWireType(SignalTypes.REDSTONE));

	/**
	 * Register a custom wire type.
	 * For the id, it is customary to use your mod id as the namespace.
	 */
	public static <T extends WireType> T register(ResourceLocation id, T type) {
		return Registry.register(SignalRegistries.WIRE_TYPE, id, type);
	}

	public static WireType get(ResourceLocation id) {
		return SignalRegistries.WIRE_TYPE.get(id);
	}

	public static ResourceLocation getId(WireType type) {
		return SignalRegistries.WIRE_TYPE.getKey(type);
	}

	public static boolean areCompatible(WireType a, WireType b) {
		return a.isCompatible(b) && b.isCompatible(a);
	}

	public static void bootstrap() {

	}
}
