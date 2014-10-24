import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
   private Node first, last;
   private int size;
   
   private class Node
   {
       Item item;
       Node next;
       Node prev;
   }
   
   public Deque()
   {
       size = 0;
       first = null;
       last = null;
   }
   
   private class DequeIterator implements Iterator<Item>
   {
       private Node current = first;
       
       public boolean hasNext() { return current != null; }
       public void remove() { throw new java.lang.UnsupportedOperationException(); }
       public Item next()
       {
           if (!hasNext()) throw new java.util.NoSuchElementException();
           Item item = current.item;
           current = current.next;
           return item;
       }
   }
      
   public boolean isEmpty() { return size == 0; }
   
   public int size() { return size; }
   
   public void addFirst(Item item) 
   {
       if (item == null) throw new NullPointerException(); 
       Node oldfirst = first;
       first = new Node();
       first.item = item;
       first.next = oldfirst;
       first.prev = null;
       if (isEmpty()) last = first;
       else oldfirst.prev = first;
       size++;
   }
       
   public void addLast(Item item)
   {
       if (item == null) throw new java.lang.NullPointerException();
       Node oldlast = last;
       last = new Node();
       last.item = item;
       last.next = null;
       last.prev = oldlast;
       if (isEmpty()) first = last;
       else oldlast.next = last;
       size++;
   }
   
   public Item removeFirst()
   {
       if (isEmpty()) throw new java.util.NoSuchElementException();
       Item item = first.item;  
       first = first.next;
       size--;
       if (isEmpty()) last = first;
       else first.prev = null;
       return item;
   }
   
   public Item removeLast()
   {
       if (isEmpty()) throw new java.util.NoSuchElementException(); 
       Item item = last.item;
       last = last.prev;
       size--;
       if (isEmpty()) first = last;
       else last.next = null;
       return item;
   }
       
   public Iterator<Item> iterator() { return new DequeIterator(); }
   

//   public static void main(String[] args) { ; }
}