import java.util.*;

public class Fast {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        Point[] ySortPoints = new Point[N];
        Point[] result = new Point[N];

    
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        for (int j = 0; j < N; j++) {
            int x = in.readInt();
            int y = in.readInt();
            Point point = new Point(x, y);
            points[j] = point;
            ySortPoints[j] = point;
            point.draw();
        }
        
        Arrays.sort(ySortPoints);
        Arrays.sort(points);
        
        HashSet<String> set = new HashSet<String>();
        
        for (int i = 0; i < N; i++) {
            Arrays.sort(points, ySortPoints[i].SLOPE_ORDER);
            int count = 1;
            for (int j = 1; j < N; j++) {
                if (ySortPoints[i].slopeTo(points[j-1]) == ySortPoints[i].slopeTo(points[j])) {
                    result[count++] = points[j];
                } else {
                    if (count >= 3) {
                        result[count++] = ySortPoints[i];
                        Arrays.sort(result, 0, count);
                        if (!set.contains(result[0].toString() + result[count-1].toString())) {
                            for (int k = 0; k < count-1; k++) {
                                StdOut.print(result[k].toString() + " -> ");
                            }
                            StdOut.print(result[count-1].toString() + "\n");
                            set.add(result[0].toString() + result[count-1].toString());
                            result[0].drawTo(result[count-1]);
                            result = new Point[N];
                        }
                    }
                    result[0] = points[j];
                    count = 1;
                }
                // This part need to be fixed. Too ugly to process the end case.
                if (count >= 3 && j == N-1) {
                    result[count++] = ySortPoints[i];
                    Arrays.sort(result, 0, count);
                    if (!set.contains(result[0].toString() + result[count-1].toString())) {
                        for (int k = 0; k < count-1; k++) {
                            StdOut.print(result[k].toString() + " -> ");
                        }
                        StdOut.print(result[count-1].toString() + "\n");
                        set.add(result[0].toString() + result[count-1].toString());
                        result[0].drawTo(result[count-1]);
                        result = new Point[N];
                    }
                }
            }
        }
    }
}