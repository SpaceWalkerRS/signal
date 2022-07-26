package signal.api.signal.wire;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Vec3i;

public enum ConnectionSide {

//	           i   o   px  py  pz  dx  dy  dz
	DOWN      ( 0,  1, -1,  0, -1,  0, -1,  0),
	UP        ( 1,  0, -1,  1, -1,  0,  1,  0),
	NORTH     ( 2,  3, -1, -1,  2,  0,  0, -1),
	SOUTH     ( 3,  2, -1, -1,  3,  0,  0,  1),
	WEST      ( 4,  5,  4, -1, -1, -1,  0,  0),
	EAST      ( 5,  4,  5, -1, -1,  1,  0,  0),
	DOWN_NORTH( 6,  7, -1,  0,  2,  0, -1, -1),
	UP_SOUTH  ( 7,  6, -1,  1,  3,  0,  1,  1),
	DOWN_SOUTH( 8,  9, -1,  0,  3,  0, -1,  1),
	UP_NORTH  ( 9,  8, -1,  1,  2,  0,  1, -1),
	DOWN_WEST (10, 11,  4,  0, -1, -1, -1,  0),
	UP_EAST   (11, 10,  5,  1, -1,  1,  1,  0),
	DOWN_EAST (12, 13,  5,  0, -1,  1, -1,  0),
	UP_WEST   (13, 12,  4,  1, -1, -1,  1,  0),
	NORTH_WEST(14, 15,  4, -1,  2, -1,  0, -1),
	SOUTH_EAST(15, 14,  5, -1,  3,  1,  0,  1),
	NORTH_EAST(16, 17,  5, -1,  2,  1,  0, -1),
	SOUTH_WEST(17, 16,  4, -1,  3, -1,  0,  1);

	public static final ConnectionSide[] ALL;
	private static final Long2ObjectMap<ConnectionSide> BY_NORMAL;

	static {

		ConnectionSide[] values = values();

		ALL = new ConnectionSide[values.length];
		BY_NORMAL = new Long2ObjectOpenHashMap<>();

		for (ConnectionSide side : values) {
			ALL[side.index] = side;
			BY_NORMAL.put(new BlockPos(side.normal).asLong(), side);
		}
	}

	private final int index;

	private final int oppositeIndex;
	private final int xProjectedIndex;
	private final int yProjectedIndex;
	private final int zProjectedIndex;
	private final boolean isAligned;

	private final int dx;
	private final int dy;
	private final int dz;
	private final Vec3i normal;

	private ConnectionSide(int index, int oppositeIndex, int xProjectedIndex, int yProjectedIndex, int zProjectedIndex, int dx, int dy, int dz) {
		this.index = index;

		this.oppositeIndex = oppositeIndex;
		this.xProjectedIndex = xProjectedIndex;
		this.yProjectedIndex = yProjectedIndex;
		this.zProjectedIndex = zProjectedIndex;
		this.isAligned = isAlignedX() || isAlignedY() || isAlignedZ();

		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.normal = new Vec3i(this.dx, this.dy, this.dz);
	}

	public int getIndex() {
		return index;
	}

	public static ConnectionSide fromIndex(int index) {
		return (index >= 0 && index < ALL.length) ? ALL[index] : null;
	}

	public ConnectionSide getOpposite() {
		return ALL[oppositeIndex];
	}

	public ConnectionSide projectX() {
		return fromIndex(xProjectedIndex);
	}

	public ConnectionSide projectY() {
		return fromIndex(yProjectedIndex);
	}

	public ConnectionSide projectZ() {
		return fromIndex(zProjectedIndex);
	}

	public ConnectionSide project(Axis axis) {
		return fromIndex(axis.choose(xProjectedIndex, yProjectedIndex, zProjectedIndex));
	}

	public ConnectionSide projectHorizontal() {
		ConnectionSide px = projectX();
		ConnectionSide pz = projectX();

		return px == null ? pz : (pz == null ? px : this);
	}

	public ConnectionSide projectVertical() {
		return projectY();
	}

	public boolean isAlignedX() {
		return xProjectedIndex == index;
	}

	public boolean isAlignedY() {
		return yProjectedIndex == index;
	}

	public boolean isAlignedZ() {
		return zProjectedIndex == index;
	}

	public boolean isAligned(Axis axis) {
		return axis.choose(xProjectedIndex, yProjectedIndex, zProjectedIndex) == index;
	}

	public boolean isAlignedHorizontal() {
		return isAlignedX() || isAlignedZ();
	}

	public boolean isAlignedVertical() {
		return isAlignedY();
	}

	public boolean isAligned() {
		return isAligned;
	}

	public int getStepX() {
		return dx;
	}

	public int getStepY() {
		return dy;
	}

	public int getStepZ() {
		return dz;
	}

	public Vec3i getNormal() {
		return normal;
	}

	public Direction getDirection() {
		return isAligned() ? Direction.from3DDataValue(index) : null;
	}

	public static ConnectionSide fromDirection(Direction dir) {
		return ALL[dir.get3DDataValue()];
	}

	public static ConnectionSide fromDirections(Direction dir1, Direction dir2) {
		return BY_NORMAL.get(new BlockPos(dir1.getNormal()).offset(dir2.getNormal()).asLong());
	}

	public boolean is(Direction dir) {
		return index == dir.get3DDataValue();
	}

	public boolean is(Direction dir1, Direction dir2) {
		return dx == (dir1.getStepX() + dir2.getStepX()) && dy == (dir1.getStepY() + dir2.getStepY()) && dz == (dir1.getStepZ() + dir2.getStepZ());
	}

	public BlockPos offset(BlockPos pos) {
		return pos.offset(dx, dy, dz);
	}
}
