public class Subset {
    public static void main(String[] args) {
        int s = Integer.parseInt(args[0]);
        RandomizedQueue<String> strings = new RandomizedQueue<>();
        while (!StdIn.isEmpty())
            strings.enqueue(StdIn.readString());
        for (int i = 0; i < s; i++)
            System.out.println(strings.dequeue());
    }
}