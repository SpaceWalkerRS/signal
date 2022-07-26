package signal.impl.signal.block;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import signal.api.signal.block.SignalState;
import signal.api.signal.wire.block.redstone.RedstoneWire;
import signal.api.signal.wire.redstone.RedstoneWireType;
import signal.impl.interfaces.mixin.IRedStoneWireBlock;

public class SignalWireBlock extends RedStoneWireBlock implements IRedStoneWireBlock, RedstoneWire, SignalState {

	protected final RedstoneWireType wireType;
	protected final Vec3[] colors;
	protected final Map<BlockState, VoxelShape> shapesCache;
	
	public SignalWireBlock(Properties properties, Vec3 baseColor, RedstoneWireType wireType) {
		super(properties);

		this.wireType = wireType;
		this.colors = new Vec3[(this.wireType.max() + 1) - this.wireType.min()];
		this.shapesCache = new HashMap<>();

		registerDefaultState(setSignal(getStateDefinition().any().
			setValue(NORTH, RedstoneSide.NONE).
			setValue(EAST, RedstoneSide.NONE).
			setValue(SOUTH, RedstoneSide.NONE).
			setValue(WEST, RedstoneSide.NONE),
			this.wireType.min()));

		for (int i = 0; i < this.colors.length; i++) {
			float f = (float)i / (this.colors.length - 1);

			float r = (float)baseColor.x * (f * 0.6F + (f > 0.0F ? 0.4F : 0.3F));
			float g = (float)baseColor.y * (f * 0.6F + (f > 0.0F ? 0.4F : 0.3F));
			float b = (float)baseColor.z * (f * 0.6F + (f > 0.0F ? 0.4F : 0.3F));

			this.colors[i] = new Vec3(r, g, b);
		}
		for (BlockState state : getStateDefinition().getPossibleStates()) {
			if (getSignal(state) == this.wireType.min()) {
				shapesCache.put(state, calculateShape(state));
			}
		}

		fixCrossState();
	}

	@Override
	public RedstoneWireType getWireType() {
		return wireType;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return shapesCache.get(setSignal(state, wireType.min()));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
		int signal = getSignal(state);

		if (signal == wireType.min()) {
			return;
		}

		Vec3 color = getColorForSignalVec(signal);

		for (Direction dir : Direction.Plane.HORIZONTAL) {
			EnumProperty<RedstoneSide> property = PROPERTY_BY_DIRECTION.get(dir);
			RedstoneSide side = state.getValue(property);

			switch (side) {
			case UP:
				spawnParticlesAlongLine(level, rand, pos, color, dir, Direction.UP, -0.5F, 0.5F);
			case SIDE:
				spawnParticlesAlongLine(level, rand, pos, color, Direction.DOWN, dir, 0.0F, 0.5F);
				break;
			default:
				spawnParticlesAlongLine(level, rand, pos, color, Direction.DOWN, dir, 0.0F, 0.3F);
			}
		}
	}

	public int getColorForSignalInt(int signal) {
		Vec3 c = getColorForSignalVec(signal);
		return Mth.color((float)c.x, (float)c.y, (float)c.z);
	}

	public Vec3 getColorForSignalVec(int signal) {
		return colors[signal - wireType.min()];
	}
}
