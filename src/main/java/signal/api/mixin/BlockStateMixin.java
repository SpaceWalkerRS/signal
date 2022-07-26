package signal.api.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import signal.api.interfaces.mixin.IBlock;
import signal.api.interfaces.mixin.IBlockState;
import signal.api.signal.SignalType;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.WireType;

@Mixin(BlockState.class)
public abstract class BlockStateMixin extends BlockStateBase implements IBlockState {

	private BlockStateMixin(Block block, ImmutableMap<Property<?>, Comparable<?>> values, MapCodec<BlockState> codec) {
		super(block, values, codec);
	}

	@Override
	public IBlock getIBlock() {
		return (IBlock)getBlock();
	}

	@Override
	public boolean isSignalSource() {
		return getIBlock().isSignalSource(asState());
	}

	@Override
	public boolean isSignalSource(SignalType types) {
		return getIBlock().isSignalSource(asState(), types);
	}

	@Override
	public int getSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return getIBlock().getSignal(level, pos, asState(), dir, type);
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return getIBlock().getDirectSignal(level, pos, asState(), dir, type);
	}

	@Override
	public boolean hasSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return getIBlock().hasSignal(level, pos, asState(), dir, type);
	}

	@Override
	public boolean hasDirectSignal(Level level, BlockPos pos, Direction dir, SignalType type) {
		return getIBlock().hasDirectSignal(level, pos, asState(), dir, type);
	}

	@Override
	public boolean isAnalogSignalSource() {
		return getIBlock().isAnalogSignalSource(asState());
	}

	@Override
	public boolean isAnalogSignalSource(SignalType types) {
		return getIBlock().isAnalogSignalSource(asState(), types);
	}

	@Override
	public int getAnalogSignal(Level level, BlockPos pos, SignalType type) {
		return getIBlock().getAnalogSignal(level, pos, asState(), type);
	}

	@Override
	public boolean hasAnalogSignal(Level level, BlockPos pos, SignalType type) {
		return getIBlock().hasAnalogSignal(level, pos, asState(), type);
	}

	@Override
	public boolean isSignalConsumer() {
		return getIBlock().isSignalConsumer(asState());
	}

	@Override
	public boolean isSignalConsumer(SignalType type) {
		return getIBlock().isSignalConsumer(asState(), type);
	}

	@Override
	public boolean isSignalConductor(Level level, BlockPos pos, SignalType type) {
		return getIBlock().isSignalConductor(level, pos, asState(), type);
	}

	@Override
	public boolean isWire() {
		return getIBlock().isWire(asState());
	}

	@Override
	public boolean isWire(WireType type) {
		return getIBlock().isWire(asState(), type);
	}

	@Override
	public boolean shouldConnectToWire(Level level, BlockPos pos, ConnectionSide dir, WireType type) {
		return getIBlock().shouldConnectToWire(level, pos, asState(), dir, type);
	}
}
