package signal.api.signal.block;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.BlockState;

/**
 * This interface represents a block that holds its signal in block states.
 * 
 * @author Space Walker
 */
public interface SignalState {

	default int getSignal(BlockState state) {
		return state.getValue(BlockStateProperties.POWER);
	}

	default BlockState setSignal(BlockState state, int signal) {
		return state.setValue(BlockStateProperties.POWER, signal);
	}
}
