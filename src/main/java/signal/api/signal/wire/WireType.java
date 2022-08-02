package signal.api.signal.wire;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import signal.api.IBlockState;
import signal.api.signal.SignalType;
import signal.api.signal.wire.block.Wire;

public abstract class WireType {

	protected final SignalType signal;
	protected final int min;
	protected final int max;
	protected final int step;

	protected WireType(SignalType signal, int min, int max, int step) {
		if (signal == null || signal.isAny()) {
			throw new IllegalArgumentException("signal type cannot be null or any!");
		}
		if (max < min) {
			throw new IllegalArgumentException("max cannot be less than min!");
		}
		if (min < signal.min() || min > signal.max()) {
			throw new IllegalArgumentException("min must be bound by signal type's min/max!");
		}
		if (max < signal.min() || max > signal.max()) {
			throw new IllegalArgumentException("max must be bound by signal type's min/max!");
		}
		if (step < 0) {
			throw new IllegalArgumentException("step cannot be less than 0!");
		}

		this.signal = signal;
		this.min = min;
		this.max = max;
		this.step = step;
	}

	@Override
	public final String toString() {
		return String.format("WireType[signal type: %s, min: %d, max: %d, step: %d]", signal, min, max, step);
	}

	public final SignalType signal() {
		return signal;
	}

	public final int min() {
		return min;
	}

	public final int max() {
		return max;
	}

	public final int step() {
		return step;
	}

	public final int clamp(int signal) {
		return Mth.clamp(signal, min, max);
	}

	public boolean isCompatible(WireType type) {
		return signal.is(type.signal);
	}

	public void findConnections(Level level, BlockPos pos, ConnectionConsumer consumer) {
		findPotentialConnections(level, pos, (side, neighborPos, neighborState, connection) -> {
			connection = validateConnection(level, pos, side, neighborPos, neighborState, connection);

			if (connection != ConnectionType.NONE) {
				boolean findNext = consumer.accept(side, neighborPos, neighborState, connection);

				if (!findNext) {
					return false;
				}
			}

			return true;
		});
	}

	public abstract void findPotentialConnections(Level level, BlockPos pos, PotentialConnectionConsumer consumer);

	public ConnectionType getConnection(Level level, BlockPos pos, ConnectionSide side) {
		ConnectionType connection = getPotentialConnection(level, pos, side);

		if (connection == ConnectionType.NONE) {
			return ConnectionType.NONE;
		}

		BlockPos neighborPos = side.offset(pos);
		BlockState neighborState = level.getBlockState(neighborPos);

		return validateConnection(level, pos, side, neighborPos, neighborState, connection);
	}

	public abstract ConnectionType getPotentialConnection(Level level, BlockPos pos, ConnectionSide side);

	private ConnectionType validateConnection(Level level, BlockPos pos, ConnectionSide side, BlockPos neighborPos, BlockState neighborState, ConnectionType connection) {
		if (connection == ConnectionType.NONE) {
			return ConnectionType.NONE;
		}

		IBlockState ineighborState = (IBlockState)neighborState;

		if (!ineighborState.isWire()) {
			return ConnectionType.NONE;
		}

		Wire neighborWire = (Wire)ineighborState.getIBlock();
		WireType neighborType = neighborWire.getWireType();

		if (this == neighborType) {
			return connection;
		}
		if (!isCompatible(neighborType)) {
			return ConnectionType.NONE;
		}

		return connection.and(neighborType.getPotentialConnection(level, neighborPos, side.getOpposite()));
	}

	@FunctionalInterface
	public interface ConnectionConsumer {

		/**
		 * @return whether to continue looking for connections
		 */
		boolean accept(ConnectionSide side, BlockPos neighborPos, BlockState neighborState, ConnectionType connection);

	}

	@FunctionalInterface
	public interface PotentialConnectionConsumer {

		/**
		 * @return whether to continue looking for connections
		 */
		boolean accept(ConnectionSide side, BlockPos neighborPos, BlockState neighborState, ConnectionType connection);

		/**
		 * @return whether to continue looking for connections
		 */
		default boolean accept(Level level, BlockPos pos, ConnectionSide side, ConnectionType connection) {
			BlockPos neighborPos = side.offset(pos);
			BlockState neighborState = level.getBlockState(neighborPos);

			return accept(side, neighborPos, neighborState, connection);
		}
	}
}
