import java.util.Comparator;

public class Solver {
    
    private MinPQ<Node> pq;
    private MinPQ<Node> pqTwin;
    private Node finalNode;
    private boolean solvable;
    
    private class Node {
        private Board board;
        private Node prevNode;
        
        public Node(Board board, Node prevNode) {
            this.board = board;
            this.prevNode = prevNode;
        }
    }
    
    public Solver(Board initial) {
        pq = new MinPQ<Node>(nodeComparator);
        pqTwin = new MinPQ<Node>(nodeComparator);
        
        solvable = false;
        
        Node node = new Node(initial, null);
        Node nodeTwin = new Node(initial.twin(), null);
        
        pq.insert(node);
        pqTwin.insert(nodeTwin);
        
        node = pq.delMin();
        nodeTwin = pqTwin.delMin();
        
        while (!node.board.isGoal() && !nodeTwin.board.isGoal()) {

            for (Board b : node.board.neighbors()) { 
                if (node.prevNode == null || !b.equals(node.prevNode.board)) {
                    Node neighbor = new Node(b, node);
                    pq.insert(neighbor);
                }
            }
            
            for (Board bTwin : nodeTwin.board.neighbors()) {
                if (nodeTwin.prevNode == null || !bTwin.equals(nodeTwin.prevNode.board)) {
                    Node neighbor = new Node(bTwin, nodeTwin);
                    pqTwin.insert(neighbor);
                }
            }
            
            node = pq.delMin();
            nodeTwin = pqTwin.delMin();
        }
        
        if (node.board.isGoal()) {
            solvable = true; 
            finalNode = node;
        }  
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (!solvable) return -1;
        Node current = finalNode;
        int moves = 0;
        
        while (current.prevNode != null) {
            moves++;
            current = current.prevNode;
        }
        return moves;
    }
    
    // to add number of moves to hamming or manhattan distance
    private static int numMoves(Node node) {
        int moves = 0;
        Node current = node;
        
        while (current.prevNode != null) {
            moves++;
            current = current.prevNode;
        }
        return moves;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!solvable) return null;
        
        Node current = finalNode;
        Stack<Board> boards = new Stack<Board>();
        boards.push(current.board);
        
        while (current.prevNode != null) {
            boards.push(current.prevNode.board);
            current = current.prevNode;
        }
        return boards;
    }
    
    private Comparator<Node> nodeComparator = new Comparator<Node>() {   
        @Override
        public int compare(Node a, Node b) {
            return a.board.manhattan() + numMoves(a) - b.board.manhattan() - numMoves(b);
        }
    };
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}