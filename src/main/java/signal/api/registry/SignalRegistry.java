package signal.api.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.resources.ResourceLocation;

import signal.SignalMod;

public class SignalRegistry<T> {

	private final Map<ResourceLocation, T> idToValue = new HashMap<>();
	private final Map<T, ResourceLocation> valueToId = new HashMap<>();

	private boolean locked;

	void unlock() {
		locked = false;
	}

	void lock() {
		if (!locked) {
			locked = true;
		}
	}

	void register(ResourceLocation id, T value) {
		if (locked) {
			throw new IllegalStateException("registry already locked!");
		}

		if (idToValue.containsKey(id)) {
			SignalMod.LOGGER.warn("Registering duplicate id " + id);
		}
		if (valueToId.containsKey(value)) {
			SignalMod.LOGGER.warn("Registering duplicate value " + value);
		}

		idToValue.put(id, value);
		valueToId.put(value, id);
	}

	public T get(ResourceLocation id) {
		return idToValue.get(id);
	}

	public ResourceLocation getId(T value) {
		return valueToId.get(value);
	}

	public Set<T> getAll() {
		return valueToId.keySet();
	}

	public Set<ResourceLocation> getIds() {
		return idToValue.keySet();
	}
}
