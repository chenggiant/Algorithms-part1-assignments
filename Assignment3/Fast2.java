import java.util.*;

public class Fast2 {
    public static void main(String[] args) {
        // Rescale the coordinate system.
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        // Read points from the input file.
        In in = new In(args[0]);
        int pointsCount = in.readInt();
        Point[] points = new Point[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        // Go each point p.
        for (int p = 0; p < pointsCount; p++) {
            // Sort the points according to the slopes they makes with p.
            Arrays.sort(points, points[p].SLOPE_ORDER);
            // Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p
            ArrayList<Point> collinearPoints = new ArrayList<Point>(pointsCount);
            for (int q = 0; q < pointsCount - 1; q++) {
                if (p == q) {
                    continue;
                }
                if (collinearPoints.isEmpty()) {
                    collinearPoints.add(points[q]);
                } else if (points[p].slopeTo(points[q - 1]) == points[p].slopeTo(points[q])) {
                    collinearPoints.add(points[q]);
                } else if (collinearPoints.size() > 2) {
                    // Draw collinear points.
                    collinearPoints.add(points[p]);
                    Collections.sort(collinearPoints);
                    // Display collinear points.
                    for (int i = 0; i < 3; i++) {
                        StdOut.print(collinearPoints.get(i));
                        StdOut.print(" -> ");
                    }
                    StdOut.println(Collections.max(collinearPoints));
                    Collections.min(collinearPoints).drawTo(Collections.max(collinearPoints));
                    break;
                } else {
                    collinearPoints.clear();
                    collinearPoints.add(points[q]);
                }
            }
        }
    }
}