import util.Point3;

public class ClosestPair {
  private Point3[] px, pz;
  public Point3[] solve(Point3[] points) {
    int n = points.length;

    px = new Point3[n];
    pz = new Point3[n];
    System.arraycopy(points, 0, px, 0, n);
    System.arraycopy(points, 0, pz, 0, n);
    Arrays.sort(px, Comparator.comparingLong(o -> o.x));
    Arrays.sort(pz, Comparator.comparingLong(o -> o.z));

    for (int i = 0; i < n; i++) {
      px[i].idx = i;
    }

    Point3[] res = find(0, n - 1, pz);

    return getDis(res[0], res[1]);

  }

  private Point3[] find(int x1, int x2, Point3[] pz){
    switch (x2 - x1 + 1) {
      case 2:
        return new Point3[]{px[x1], px[x2]};
      case 3:
        double dis12 = getDis(px[x1], px[x1 + 1]);
        double dis23 = getDis(px[x1 + 1], px[x2]);
        double dis13 = getDis(px[x1], px[x2]);
        if (dis12 < dis23) {
          if (dis12 < dis13) {
            return new Point3[]{px[x1], px[x1 + 1]};
          } else {
            return new Point3[]{px[x1], px[x2]};
          }
        } else {
          if (dis23 < dis13) {
            return new Point3[]{px[x1 + 1], px[x2]};
          } else {
            return new Point3[]{px[x1], px[x2]};
          }
        }
    }

    int mi = (x1 + x2) / 2;
    int idx1 = 0;
    int idx2 = 0;
    Point3[] qz = new Point3[mi - x1 + 1];
    Point3[] rz = new Point3[x2 - mi];

    for (Point3 p : pz) {
      if (p.idx <= mi) {
        qz[idx1++] = p;
      } else {
        rz[idx2++] = p;
      }
    }

    Point3[] left = find(x1, mi, qz);
    Point3[] right = find(mi + 1, x2, rz);

    double dis1 = getDis(left[0], left[1]);
    double dis3 = getDis(right[0], right[1]);
    double delta = Math.min(dis1, dis3);

    long x = px[mi].x;

    ArrayList<Point3> s1 = new ArrayList<>();   
    ArrayList<Long> segRef = new ArrayList<>(); 
    ArrayList<Integer> sl1 = new ArrayList<>(); 
    ArrayList<Integer> sl2 = new ArrayList<>();
    HashMap<Long, ArrayList<Point3>> s2 = new HashMap<>();
    construct(pz, x, mi, delta, s1, segRef, sl1, sl2, s2);

    Point3[] pairMin = new Point3[2];
    double disMin = delta;

    for (int i = 0; i < s1.size(); i++) {
      Point3 s = s1.get(i);
      long segIdx = segRef.get(i);
      int loc1 = sl1.get(i);
      int loc2 = sl2.get(i);

      ArrayList<Point3> seg;

      seg = s2.get(segIdx);
      if (!seg.isEmpty()){
        for (int j = Math.max(0, loc1 - 16); j < seg.size() && j <= loc1 + 16; j++) {
          Point3 st = seg.get(j);
          double tmp = getDis(s, st);
          if (tmp < disMin) {
            disMin = tmp;
            pairMin[0] = s;
            pairMin[1] = st;
          }
        }
      }

      seg = s2.get(segIdx + 1);
      if (!seg.isEmpty()) {
        for (int j = Math.max(0, loc2 - 16); j < seg.size() && j <= loc2 + 16; j++) {
          Point3 st = seg.get(j);
          double tmp = getDis(s, st);
          if (tmp < disMin) {
            disMin = tmp;
            pairMin[0] = s;
            pairMin[1] = st;
          }
        }
      }
    }

    if (pairMin[0] != null) {
      return pairMin;
    } else if (dis1 < dis3) {
      return left;
    } else {
      return right;
    }
  }

  private static void construct(Point3[] pz, long x, int idx, double delta,
                                ArrayList<Point3> s1,
                                ArrayList<Long> segRef,
                                ArrayList<Integer> sl1,
                                ArrayList<Integer> sl2,
                                HashMap<Long, ArrayList<Point3>> s2) {

    long yMin = Long.MAX_VALUE;
    for (Point3 p : pz) {
      if (yMin > p.y) {
        yMin = p.y;
      }
    }

    for (Point3 p : pz) {
      if (Math.abs(x - p.x) > delta) {
        continue;
      }

      long no = (long)((p.y - yMin) / delta);
      if (p.idx <= idx) { 
        s1.add(p);

        if (no == 0) {
          no = -1;
        } else {
          no = (no - 1) / 2;
        }
        segRef.add(no);

        ArrayList<Point3> arr;
        arr = s2.computeIfAbsent(no, k -> new ArrayList<>());
        sl1.add(Math.max(arr.size()-1, 0));
        arr = s2.computeIfAbsent(no+1, k -> new ArrayList<>());
        sl2.add(Math.max(arr.size()-1, 0));

      } else {       
        no = no / 2;
        s2.computeIfAbsent(no, k -> new ArrayList<>()).add(p);
      }
    }
  }
}