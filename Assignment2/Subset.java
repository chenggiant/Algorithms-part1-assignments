public class Subset {
    public static void main(String[] args)
    {
        RandomizedQueue<String> strs = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            strs.enqueue(StdIn.readString());
        }
        
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(strs.dequeue());
        }
    }
}