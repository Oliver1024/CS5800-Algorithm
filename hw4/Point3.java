public class Point3 {
	public int idx;
	public long x, y, z;

	public Point3(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static double getDis(Point3 p1, Point3 p2) {
		long tmp1 = p1.x - p2.x;
		long tmp2 = p1.y - p2.y;
		long tmp3 = p1.z - p2.z;
		return Math.sqrt(tmp1 * tmp1 + tmp2 * tmp2 + tmp3 * tmp3);
	}

}