import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] s;
    
    public RandomizedQueue()
    {
        size = 0;
        s = (Item[]) new Object[1];
    }
    
    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int i = size;
        private int[] order;
        
        public RandomizedQueueIterator()
        {
            order = new int[i];
            for (int j = 0; j < i; ++j) {
                order[j] = j;
            }
            StdRandom.shuffle(order);
        }
        
        public boolean hasNext() { return i > 0; }
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
        public Item next() 
        { 
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return s[order[--i]]; 
        }
    }
    
    public boolean isEmpty() { return size == 0; }
    
    public int size() { return size; }
    
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = s[i];
        s = copy;
    }
    
    public void enqueue(Item item)
    {
        if (item == null) throw new NullPointerException();
        s[size++] = item;
        if (size == s.length) resize(2 * s.length);
    }
    
    public Item dequeue()
    {
        if (size == 0) throw new java.util.NoSuchElementException();
        int r = StdRandom.uniform(size);
        Item item = s[r];
        s[r] = s[size-1];
        s[--size] = null;
        if (size > 0 && size == s.length/4) resize(s.length/2);
        return item;
    }
    
    public Item sample()
    {
        if (size == 0) throw new java.util.NoSuchElementException();
        int r = StdRandom.uniform(size);
        return s[r];
    }
        
    public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

//    public static void main(String[] args) { ; }
}