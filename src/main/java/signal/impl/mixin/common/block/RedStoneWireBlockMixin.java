package signal.impl.mixin.common.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RedstoneSide;

import signal.api.IBlockState;
import signal.api.signal.block.SignalState;
import signal.api.signal.wire.ConnectionSide;
import signal.api.signal.wire.block.Wire;
import signal.api.signal.wire.block.redstone.RedstoneWire;
import signal.impl.interfaces.mixin.IRedStoneWireBlock;

@Mixin(RedStoneWireBlock.class)
public class RedStoneWireBlockMixin extends Block implements IRedStoneWireBlock, RedstoneWire, SignalState {

	@Shadow @Mutable private BlockState crossState;

	private RedStoneWireBlockMixin(Properties properties) {
		super(properties);
	}

	@Shadow private BlockState getConnectionState(BlockGetter level, BlockState state, BlockPos pos) { return null; }
	@Shadow private static boolean shouldConnectTo(BlockState state) { return false; }
	@Shadow private static boolean shouldConnectTo(BlockState state, Direction dir) { return false; }

	@Redirect(
		method = "getConnectingSide(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Lnet/minecraft/world/level/block/state/properties/RedstoneSide;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean isRedstoneConductor(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return blockGetter instanceof Level && ((IBlockState)state).isSignalConductor((Level)blockGetter, pos, getSignalType());
	}

	@Redirect(
		method = "getConnectingSide(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Lnet/minecraft/world/level/block/state/properties/RedstoneSide;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
			)
		)
	private boolean isRedstoneConductor2(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return blockGetter instanceof Level && ((IBlockState)state).isSignalConductor((Level)blockGetter, pos, getSignalType());
	}

	@Redirect(
		method = "getMissingConnections",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean isRedstoneCondcutor3(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return blockGetter instanceof Level && ((IBlockState)state).isSignalConductor((Level)blockGetter, pos, getSignalType());
	}

	@Redirect(
		method = "updateNeighborsOfNeighboringWires",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean isRedstoneCondcutor4(BlockState state, BlockGetter blockGetter, BlockPos pos, Level level) {
		return ((IBlockState)state).isSignalConductor(level, pos, getSignalType());
	}

	@Redirect(
		method = "updatesOnShapeChange",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;isRedstoneConductor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean isRedstoneCondcutor5(BlockState state, BlockGetter blockGetter, BlockPos pos, Level level) {
		return ((IBlockState)state).isSignalConductor(level, pos, getSignalType());
	}

	@Redirect(
		method = "getConnectionState",
		at = @At(
			value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
		)
	)
	@SuppressWarnings("unchecked")
	private <T extends Comparable<T>> T getConnectionStateGetSignal(BlockState state, Property<T> property) {
		return (T)(Integer)getSignal(state);
	}

	@Redirect(
		method = "getConnectionState",
		at = @At(
			value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
		)
	)
	private <T extends Comparable<T>> Object getConnectionStateSetSignal(BlockState state, Property<T> property, T signal) {
		return setSignal(state, (Integer)signal);
	}

	@Redirect(
		method = "updateShape",
		at = @At(
			value = "INVOKE",
			ordinal = 1,
			target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
		)
	)
	private <T extends Comparable<T>> Object updateShapeSetSignal(BlockState state, Property<T> property, T signal) {
		return setSignal(state, (Integer)signal);
	}

	@Redirect(
		method = "updateIndirectNeighbourShapes",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"
		)
	)
	private boolean onUpdateIndirectNeighbourShapesRedirectIsThis(BlockState state, Block block) {
		// Diagonal shape updates are only sent to neighbors of the same block.
		// This redirect makes it so they are also sent to wire blocks that
		// can connect to this wire block.
		IBlockState istate = (IBlockState)state;

		if (!istate.isWire()) {
			return false;
		}

		return isCompatible(((Wire)istate.getIBlock()));
	}

	@Redirect(
		method = "getConnectingSide(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Lnet/minecraft/world/level/block/state/properties/RedstoneSide;",
		at = @At(
			value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/world/level/block/RedStoneWireBlock;shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;)Z"
		)
	)
	private boolean shouldConnectUp(BlockState neighborState, BlockGetter level, BlockPos pos, Direction hor, boolean allowUpConnection) {
		if (!(level instanceof Level)) {
			return shouldConnectTo(neighborState); // we should never get here
		}

		ConnectionSide side = ConnectionSide.fromDirection(hor).withUp();
		BlockPos neighborPos = side.offset(pos);

		return ((IBlockState)neighborState).shouldConnectToWire((Level)level, neighborPos, side.getOpposite(), getWireType());
	}

	@Redirect(
		method = "getConnectingSide(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Lnet/minecraft/world/level/block/state/properties/RedstoneSide;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/RedStoneWireBlock;shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z"
		)
	)
	private boolean shouldConnectSide(BlockState neighborState, Direction neighborDir, BlockGetter level, BlockPos pos, Direction hor, boolean allowUpConnection) {
		if (!(level instanceof Level)) {
			return shouldConnectTo(neighborState, neighborDir); // we should never get here
		}

		ConnectionSide side = ConnectionSide.fromDirection(hor);
		BlockPos neighborPos = side.offset(pos);

		return ((IBlockState)neighborState).shouldConnectToWire((Level)level, neighborPos, side.getOpposite(), getWireType());
	}

	@Redirect(
		method = "getConnectingSide(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Lnet/minecraft/world/level/block/state/properties/RedstoneSide;",
		at = @At(
			value = "INVOKE",
			ordinal = 1,
			target = "Lnet/minecraft/world/level/block/RedStoneWireBlock;shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;)Z"
		)
	)
	private boolean shouldConnectDown(BlockState neighborState, BlockGetter level, BlockPos pos, Direction hor, boolean allowUpConnection) {
		if (!(level instanceof Level)) {
			return shouldConnectTo(neighborState); // we should never get here
		}

		ConnectionSide side = ConnectionSide.fromDirection(hor).withDown();
		BlockPos neighborPos = side.offset(pos);

		return ((IBlockState)neighborState).shouldConnectToWire((Level)level, neighborPos, side.getOpposite(), getWireType());
	}

	@Redirect(
		method = "updatePowerStrength",
		at = @At(
			value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
		)
	)
	@SuppressWarnings("unchecked")
	private <T extends Comparable<T>> T updatePowerStrengthGetSignal(BlockState state, Property<T> property) {
		return (T)(Integer)getSignal(state);
	}

	@Redirect(
		method = "updatePowerStrength",
		at = @At(
			value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
		)
	)
	private <T extends Comparable<T>> Object updatePowerStrengthSetSignal(BlockState state, Property<T> property, T signal) {
		return setSignal(state, (Integer)signal);
	}

	@Inject(
		method = "calculateTargetStrength",
		cancellable = true,
		at = @At(
			value = "HEAD"
		)
	)
	private void modifyCalculateTargetStrength(Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(getNeighborSignal(level, pos));
	}

	@Redirect(
		method = "checkCornerChangeAt",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"
		)
	)
	private boolean onCheckCornerChangeAtRedirectIsThis(BlockState state, Block block) {
		// Vanilla redstone dust is weird. When its connection properties change
		// it does not emit block updates to notify neighboring blocks that it
		// has done so. Instead, when a wire is placed or removed, neighboring
		// wires are told to emit block updates to their neighbors.
		// We do not change this, but instead extend this condition to include
		// all wires that can connect to this wire.
		IBlockState istate = (IBlockState)state;

		if (!istate.isWire()) {
			return false;
		}

		return isCompatible(((Wire)istate.getIBlock()));
	}

	@Redirect(
		method = "use",
		at = @At(
			value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
		)
	)
	@SuppressWarnings("unchecked")
	private <T extends Comparable<T>> T useGetSignal(BlockState state, Property<T> property) {
		return (T)(Integer)getSignal(state);
	}

	@Redirect(
		method = "use",
		at = @At(
			value = "INVOKE",
			ordinal = 0,
			target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
		)
	)
	private <T extends Comparable<T>> Object useSetSignal(BlockState state, Property<T> property, T signal) {
		return setSignal(state, (Integer)signal);
	}

	@Override
	public void fixCrossState() {
		crossState = defaultBlockState().
			setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE).
			setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE).
			setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE).
			setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE);
	}

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		if (dir == Direction.DOWN) {
			return getWireType().min();
		}
		if (dir == Direction.UP) {
			return getSignal(level, pos, state);
		}

		state = getConnectionState(level, state, pos);

		EnumProperty<RedstoneSide> property = RedStoneWireBlock.PROPERTY_BY_DIRECTION.get(dir.getOpposite());
		RedstoneSide side = state.getValue(property);

		if (!side.isConnected()) {
			return getWireType().min();
		}

		return getSignal(level, pos, state);
	}

	@Override
	public int getDirectSignal(Level level, BlockPos pos, BlockState state, Direction dir) {
		return getSignal(level, pos, state, dir);
	}

	@Override
	public int getSignal(Level level, BlockPos pos, BlockState state) {
		return getSignal(state);
	}

	@Override
	public BlockState setSignal(Level level, BlockPos pos, BlockState state, int signal) {
		return setSignal(state, signal);
	}
}
