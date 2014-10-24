public class KdTree {
    private Node root;
    private int size;
    private RectHV board;
    
    private class Node {
        private Point2D key;
        private Point2D val;
        private Node left, right;
        private boolean isHorizontal = false;
        
        public Node(Point2D key, Point2D val, Node left, Node right, boolean isHorizontal) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
            this.isHorizontal = isHorizontal;
        }
    }
    
    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
        board = new RectHV(0, 0, 1.0, 1.0);
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // number of points in the set
    public int size() {
       return size;
    }
    
    // add the point to the set (if it is not already in the set)
    // refer to BST put function
    public void insert(Point2D p) {
        root = put(root, p, p, false);
    }
    
    private Node put(Node node, Point2D key, Point2D val, boolean isHorizontal) {
        if (node == null) {
            size++;
            return new Node(key, val, null, null, isHorizontal);
        }
        
        if (node.key.x() == key.x() && node.key.y() == key.y()) {
            node.val = val;
        } else if ((!node.isHorizontal && node.key.x() >= key.x()) || (node.isHorizontal && node.key.y() >= key.y())) {
            node.left = put(node.left, key, val, !isHorizontal);
        } else {
            node.right = put(node.right, key, val, !isHorizontal);
        }
        
        return node;
    }
    
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node node = root;
        while (node != null) {
            if (node.key.x() == p.x() && node.key.y() == p.y()) {
                return true;
            } else if ((!node.isHorizontal && node.key.x() >= p.x()) || (node.isHorizontal && node.key.y() >= p.y())) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }
    
    // draw all points to standard draw
    public void draw() {
        StdDraw.setScale(0, 1);
        draw(root, board);
    }
    
    private void draw(Node node, RectHV rect) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            new Point2D(node.key.x(), node.key.y()).draw();
            StdDraw.setPenRadius();
            
            if (!node.isHorizontal) {
                StdDraw.setPenColor(StdDraw.RED);
                new Point2D(node.key.x(), rect.ymin()).drawTo(new Point2D(node.key.x(), rect.ymax()));
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                new Point2D(rect.xmin(), node.key.y()).drawTo(new Point2D(rect.xmax(), node.key.y()));
            }
            draw(node.left, leftRect(rect, node));
            draw(node.right, rightRect(rect, node));
        }
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> points = new Stack<Point2D>();
        get(root, board, rect, points);
        return points;
    }
    
    private RectHV leftRect(RectHV rect, Node p) {
        if (p.isHorizontal) {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.key.y());
        }
        return new RectHV(rect.xmin(), rect.ymin(), p.key.x(), rect.ymax());
    }
    
    private RectHV rightRect(RectHV rect, Node p) {
        if (p.isHorizontal) {
            return new RectHV(rect.xmin(), p.key.y(), rect.xmax(), rect.ymax());
        }
        return new RectHV(p.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
    }    
    
    private void get(Node node, RectHV nodeRect, RectHV rect, Stack<Point2D> pointsIn) {
        if (node != null) {
            if (nodeRect.intersects(rect)) {
                if (rect.contains(node.val)) pointsIn.push(node.val);
                get(node.left, leftRect(nodeRect, node), rect, pointsIn);
                get(node.right, rightRect(nodeRect, node), rect, pointsIn);
            }   
        }
    }
    
    private Point2D nearestNeigh(Node node, RectHV rect, Point2D point, Point2D nearPoint) {
        Point2D nearestPoint = nearPoint;
        if (node != null) {      
            if (nearestPoint == null || nearestPoint.distanceSquaredTo(point) > rect.distanceSquaredTo(point)) {
                if (nearestPoint == null) {
                    nearestPoint = node.key;
                } else {
                    if (node.key.distanceSquaredTo(point) < nearestPoint.distanceSquaredTo(point)) {
                        nearestPoint = node.key;
                    }
                }
                
                if (!node.isHorizontal) {
                    RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.key.x(), rect.ymax());
                    RectHV rightRect = new RectHV(node.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
                    if (point.x() <= node.key.x()) {
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                    } else {
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                    }
                } else {
                    RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.key.y());
                    RectHV rightRect = new RectHV(rect.xmin(), node.key.y(), rect.xmax(), rect.ymax());
                    if (point.y() <= node.key.y()) {
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                    } else {
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                    }
                }
            }
        }
        return nearestPoint;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return nearestNeigh(root, board, p, null);
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
                     
}