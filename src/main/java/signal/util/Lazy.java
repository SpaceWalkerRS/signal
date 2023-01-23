package signal.util;

import java.util.Objects;
import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {

	private final Supplier<T> delegate;

	private T value;

	public Lazy(Supplier<T> delegate) {
		this.delegate = Objects.requireNonNull(delegate);
	}

	@Override
	public T get() {
		return value == null ? (value = delegate.get()) : value;
	}
}
