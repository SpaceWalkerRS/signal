package signal.util;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.core.Registry;

public class Utils {

	public static <T> Collection<T> collectTypes(Registry<T> registry, Predicate<T> filter) {
		Set<T> types = new LinkedHashSet<>();

		for (T type : registry) {
			if (filter.test(type)) types.add(type);
		}

		return types;
	}
}
