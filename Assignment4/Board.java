public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    
    private final int N;
    private final int[][] data;
    
    public Board(int[][] blocks) {
        this(blocks, 0);
    }
    
    private Board(int[][] blocks, int moves) {
        this.N = blocks.length;
        this.data = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.data[i][j] = blocks[i][j];
            }
        }
    }
        
    // board dimension N
    public int dimension() {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {
        int num, hammingValue = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                num = i * N + j + 1;
                if (i == N-1 && j == N-1) break;
                if (data[i][j] != num) hammingValue++;
                num++;
            }
        }
        return hammingValue;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanValue = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (data[i][j] == 0) continue;
                int iTmp = (data[i][j]-1) / N;
                int jTmp = (data[i][j]-1) % N;
                manhattanValue += (Math.abs(iTmp - i) + Math.abs(jTmp - j));
            }
        }    
        return manhattanValue;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        int num = 1;
        if (data[N-1][N-1] != 0) return false;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (data[i][j] == 0) break;
                if (data[i][j] != num) return false;
                num++;
            }
        }
        return true;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {      
        Board bb = new Board(data);
        
        if (bb.data[0][0] == 0) {
            exch(bb.data, 1, 0, 1, 1);
        } else if (bb.data[0][1] == 0) {
            exch(bb.data, 1, 0, 1, 1);
        } else {
            exch(bb.data, 0, 0, 0, 1);
        }
        return bb;
    }
    
    // does this board equal y?
    public boolean equals(Object Y) {
        if (Y == null) return false;
        if (Y == this) return true;
        
        if (Y.getClass() != this.getClass()) return false;
        
        Board that = (Board) Y; 
        
        if (that.dimension() != this.dimension()) return false;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.data[i][j] != (that.data)[i][j]) return false;
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        
        int row = 0, col = 0;
        int[][] dataTmp = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dataTmp[i][j] = data[i][j];
                if (data[i][j] == 0) {
                    row = i; 
                    col = j;
                }
            }
        }
        
       // shift with left
        if (col > 0) {
            exch(dataTmp, row, col, row, col-1);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row, col-1);
        }
        
       // shift with right
        if (col < N-1) {
            exch(dataTmp, row, col, row, col+1);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row, col+1);
        }
        
        // shift with up
        if (row > 0) {
            exch(dataTmp, row, col, row-1, col);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row-1, col);
        }
        
        // shift with down
        if (row < N-1) {
            exch(dataTmp, row, col, row+1, col);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row+1, col);
        }
        return stack;
    }
    
    private void exch(int[][] matrix, int i, int j, int p, int q) {
        int tmp = matrix[i][j];  
        matrix[i][j] = matrix[p][q]; 
        matrix[p][q] = tmp;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder str = new StringBuilder(dimension() + "\n");
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                str.append(data[i][j]);
                str.append(" ");
            }      
            str.append("\n");
        }     
        return str.toString();
    }
    
//    public static void main(String[] args) {
//        int[][] input = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
//        int[][] input2 = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
//        int[][] input3 = new int[][]{{0, 5, 7}, {1, 8, 4}, {3, 2, 6}};
//
//        Board testBoard = new Board(input);
//        Board testBoard2 = new Board(input2);
//        Board testBoard3 = new Board(input3);
//
//        StdOut.println(testBoard3.manhattan());
//        
//        Iterable<Board> result = testBoard.neighbors();
//        
//        for (Board b : result) {
//            StdOut.println(b.toString());
//        }
//    }
}