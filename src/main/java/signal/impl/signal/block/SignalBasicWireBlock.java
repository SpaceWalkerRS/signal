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
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import signal.api.signal.wire.WireType;
import signal.impl.interfaces.mixin.IRedStoneWireBlock;
import signal.impl.mixin.common.block.RedStoneWireBlockInvoker;

public class SignalBasicWireBlock extends RedStoneWireBlock {

	protected final WireType wireType;
	protected final Vec3[] colors;
	protected final Map<BlockState, VoxelShape> shapesCache;
	
	public SignalBasicWireBlock(Properties properties, Vec3 baseColor, WireType wireType) {
		super(properties);

		if (wireType.min() < Redstone.SIGNAL_MIN || wireType.max() > Redstone.SIGNAL_MAX) {
			throw new IllegalArgumentException("illegal wire type given - its min/max must be between [0, 15]");
		}

		this.wireType = wireType;
		this.colors = new Vec3[(this.wireType.max() + 1) - this.wireType.min()];
		this.shapesCache = new HashMap<>();

		populateColorTable(baseColor);
		populateShapesCache();

		fixDefaultState();
		((IRedStoneWireBlock)this).fixCrossState();
	}

	@Override
	public WireType getWireType() {
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
				((RedStoneWireBlockInvoker)this).invokeSpawnParticlesAlongLine(level, rand, pos, color, dir, Direction.UP, -0.5F, 0.5F);
			case SIDE:
				((RedStoneWireBlockInvoker)this).invokeSpawnParticlesAlongLine(level, rand, pos, color, Direction.DOWN, dir, 0.0F, 0.5F);
				break;
			default:
				((RedStoneWireBlockInvoker)this).invokeSpawnParticlesAlongLine(level, rand, pos, color, Direction.DOWN, dir, 0.0F, 0.3F);
			}
		}
	}

	private void populateColorTable(Vec3 baseColor) {
		if (colors.length == 1) {
			colors[0] = baseColor;
		} else {
			for (int i = 0; i < colors.length; i++) {
				float f = (float)i / (colors.length - 1);

				float r = (float)baseColor.x * (f * 0.6F + (f > 0.0F ? 0.4F : 0.3F));
				float g = (float)baseColor.y * (f * 0.6F + (f > 0.0F ? 0.4F : 0.3F));
				float b = (float)baseColor.z * (f * 0.6F + (f > 0.0F ? 0.4F : 0.3F));

				colors[i] = new Vec3(r, g, b);
			}
		}
	}

	private void populateShapesCache() {
		for (BlockState state : getStateDefinition().getPossibleStates()) {
			if (getSignal(state) == wireType.min()) {
				shapesCache.put(state, ((RedStoneWireBlockInvoker)this).invokeCalculateShape(state));
			}
		}
	}

	private void fixDefaultState() {
		registerDefaultState(setSignal(getStateDefinition().any().
			setValue(NORTH, RedstoneSide.NONE).
			setValue(EAST, RedstoneSide.NONE).
			setValue(SOUTH, RedstoneSide.NONE).
			setValue(WEST, RedstoneSide.NONE), wireType.min()));
	}

	public int getColorForSignalInt(int signal) {
		Vec3 c = getColorForSignalVec(signal);
		return Mth.color((float)c.x, (float)c.y, (float)c.z);
	}

	public Vec3 getColorForSignalVec(int signal) {
		if (signal < wireType.min()) {
			return Vec3.ZERO;
		}
		if (signal > wireType.max()) {
			return Vec3.ZERO;
		}

		return colors[signal - wireType.min()];
	}
}
