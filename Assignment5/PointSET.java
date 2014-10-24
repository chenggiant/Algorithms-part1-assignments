import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set;
    
    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return set.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        set.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        for (Point2D p:set) p.draw();
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> points = new Stack<Point2D>();
        for (Point2D p:set) {
            if (rect.contains(p)) points.push(p);
        }
        return points;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (set.isEmpty()) return null;
        Point2D pp = set.first();
        double minDistance = p.distanceSquaredTo(pp);
        for (Point2D point:set) {
            if (p.distanceSquaredTo(point) <= minDistance) {
                pp = point;
                minDistance = p.distanceSquaredTo(point);
            }
        }
        return pp;
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
                     
}