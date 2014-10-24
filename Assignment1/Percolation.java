public class Percolation {
    private boolean[][] opened;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private int len;
    
    public Percolation(int N)
    {
        if (N <= 0) {
            throw new IllegalArgumentException("Given N <= 0");
        }
        opened = new boolean[N][N];
        uf     = new WeightedQuickUnionUF(N*N + 2);
        uf2    = new WeightedQuickUnionUF(N*N + 1); 
        top    = N*N;
        bottom = N*N + 1;
        len    = N;
    }
    
    public void open(int i, int j)
    {
        if (checkIndex(i, j)) {
            opened[i-1][j-1] = true;
            if (i == 1) {
                uf.union(j-1, top); 
                uf2.union(j-1, top);
            }
            if (i == len) uf.union((i-1)*len+j-1, bottom);
            if (i > 1   && isOpen(i-1, j)) {
                uf.union((i-1)*len+j-1, (i-2)*len+j-1);
                uf2.union((i-1)*len+j-1, (i-2)*len+j-1);
            }
            if (i < len && isOpen(i+1, j)) {
                uf.union((i-1)*len+j-1, i*len+j-1);
                uf2.union((i-1)*len+j-1, i*len+j-1);
            }
            if (j > 1   && isOpen(i, j-1)) {
                uf.union((i-1)*len+j-1, (i-1)*len+j-2);
                uf2.union((i-1)*len+j-1, (i-1)*len+j-2);
            }
            if (j < len && isOpen(i, j+1)) {
                uf.union((i-1)*len+j-1, (i-1)*len+j);
                uf2.union((i-1)*len+j-1, (i-1)*len+j);
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public boolean isOpen(int i, int j)
    {
        if (checkIndex(i, j)) {
            return opened[i-1][j-1];
        }
        throw new IndexOutOfBoundsException();
    }
    
    public boolean isFull(int i, int j)
    {
        if (checkIndex(i, j)) {
            return uf2.connected((i-1)*len+j-1, top);
        }
        throw new IndexOutOfBoundsException();
    }
    
    public boolean percolates()
    {
        return uf.connected(top, bottom);
    }
    
    private boolean checkIndex(int i, int j)
    {
        if (i < 1 || i > len || j < 1 || j > len) return false;
        return true;
    }
}