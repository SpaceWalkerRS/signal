package signal.api.signal.wire;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;

public enum ConnectionSide {

//	           i   o   px  py  pz  wd  wu  wn  ws  ww  we  dx  dy  dz
	DOWN      ( 0,  1, -1,  0, -1,  0, -1,  6,  8, 10, 12,  0, -1,  0),
	UP        ( 1,  0, -1,  1, -1, -1,  1,  9,  7, 13, 11,  0,  1,  0),
	NORTH     ( 2,  3, -1, -1,  2,  6,  9,  2, -1, 14, 16,  0,  0, -1),
	SOUTH     ( 3,  2, -1, -1,  3,  8,  7, -1,  3, 17, 15,  0,  0,  1),
	WEST      ( 4,  5,  4, -1, -1, 10, 13, 14, 17,  4, -1, -1,  0,  0),
	EAST      ( 5,  4,  5, -1, -1, 12, 11, 16, 15, -1,  5,  1,  0,  0),
	DOWN_NORTH( 6,  7, -1,  0,  2,  6,  2,  6,  0, -1, -1,  0, -1, -1),
	UP_SOUTH  ( 7,  6, -1,  1,  3,  3,  7,  1,  7, -1, -1,  0,  1,  1),
	DOWN_SOUTH( 8,  9, -1,  0,  3,  8,  3,  0,  8, -1, -1,  0, -1,  1),
	UP_NORTH  ( 9,  8, -1,  1,  2,  2,  9,  9,  1, -1, -1,  0,  1, -1),
	DOWN_WEST (10, 11,  4,  0, -1, 10,  4, -1, -1, 10,  0, -1, -1,  0),
	UP_EAST   (11, 10,  5,  1, -1,  5, 11, -1, -1,  1, 11,  1,  1,  0),
	DOWN_EAST (12, 13,  5,  0, -1, 12,  5, -1, -1,  0, 12,  1, -1,  0),
	UP_WEST   (13, 12,  4,  1, -1,  4, 13, -1, -1, 13,  1, -1,  1,  0),
	NORTH_WEST(14, 15,  4, -1,  2, -1, -1, 14,  4, 14,  2, -1,  0, -1),
	SOUTH_EAST(15, 14,  5, -1,  3, -1, -1,  5, 15,  3, 15,  1,  0,  1),
	NORTH_EAST(16, 17,  5, -1,  2, -1, -1, 16,  5,  2, 16,  1,  0, -1),
	SOUTH_WEST(17, 16,  4, -1,  3, -1, -1,  4, 17, 17,  3, -1,  0,  1);

	public static final ConnectionSide[] ALL;

	static {

		ConnectionSide[] values = values();
		ALL = new ConnectionSide[values.length];

		for (ConnectionSide side : values) {
			ALL[side.index] = side;
		}
	}

	private final int index;

	private final int oppositeIndex;
	private final int xProjectedIndex;
	private final int yProjectedIndex;
	private final int zProjectedIndex;
	private final boolean isAligned;
	private final int withDownIndex;
	private final int withUpIndex;
	private final int withNorthIndex;
	private final int withSouthIndex;
	private final int withWestIndex;
	private final int withEastIndex;

	private final int dx;
	private final int dy;
	private final int dz;

	private ConnectionSide(int index, int oppositeIndex, int xProjectedIndex, int yProjectedIndex, int zProjectedIndex, int withDownIndex, int withUpIndex, int withNorthIndex, int withSouthIndex, int withWestIndex, int withEastIndex, int dx, int dy, int dz) {
		this.index = index;

		this.oppositeIndex = oppositeIndex;
		this.xProjectedIndex = xProjectedIndex;
		this.yProjectedIndex = yProjectedIndex;
		this.zProjectedIndex = zProjectedIndex;
		this.isAligned = isAlignedX() || isAlignedY() || isAlignedZ();
		this.withDownIndex = withDownIndex;
		this.withUpIndex = withUpIndex;
		this.withNorthIndex = withNorthIndex;
		this.withSouthIndex = withSouthIndex;
		this.withWestIndex = withWestIndex;
		this.withEastIndex = withEastIndex;

		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
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
		ConnectionSide pz = projectZ();

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

	public ConnectionSide withDown() {
		return fromIndex(withDownIndex);
	}

	public ConnectionSide withUp() {
		return fromIndex(withUpIndex);
	}

	public ConnectionSide withNorth() {
		return fromIndex(withNorthIndex);
	}

	public ConnectionSide withSouth() {
		return fromIndex(withSouthIndex);
	}

	public ConnectionSide withWest() {
		return fromIndex(withWestIndex);
	}

	public ConnectionSide withEast() {
		return fromIndex(withEastIndex);
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

	public Direction getDirection() {
		return isAligned() ? Direction.from3DDataValue(index) : null;
	}

	public static ConnectionSide fromDirection(Direction dir) {
		return ALL[dir.get3DDataValue()];
	}

	public boolean is(Direction dir) {
		return index == dir.get3DDataValue();
	}

	public BlockPos offset(BlockPos pos) {
		return pos.offset(dx, dy, dz);
	}
}
