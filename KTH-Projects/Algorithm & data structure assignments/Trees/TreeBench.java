import java.security.SecureRandom;

public class TreeBench {
    private static Integer[] unsorted(int n) {
        SecureRandom rnd = new SecureRandom();
        Integer[] array = new Integer[n];

        for(int i = 0; i < n; i++) {
            array[i] = rnd.nextInt();
        }

        return array;
    }

    private static Integer[] sorted(int n) {
        Integer[] array = new Integer[n];

        for(int i = 0; i < n; i++) {
            array[i] = i;
        }

        return array;
    }

    public static void benchTime(int k, Integer[] keys, Integer[] lookKeys) {
        double minCons = Double.POSITIVE_INFINITY;
        double minLook = Double.POSITIVE_INFINITY;
        double minBinary = Double.POSITIVE_INFINITY;

        Integer value = 4;

        for(int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            BinaryTree binaryTree = newTree(keys);
            double t1 = System.nanoTime();
            double t = (t1-t0);
            if(t < minCons) {
                minCons = t;
            }

            double t2 = System.nanoTime();
            binaryTree.lookup(value);
            double t3 = System.nanoTime();
            double t4 = (t3-t2);
            if(t4 < minLook) {
                minLook = t4;
            }

            double t5 = System.nanoTime();
            TreeBinary.search(keys, 4);
            double t6 = System.nanoTime();
            double t7 = (t6-t5);
            if(t7 < minBinary) {
                minBinary = t7;
            }

        }

        System.out.printf("%8.1f%8.1f%8.1f\n" ,minCons/1000,minLook/1000,minBinary/1000);
    }

    /*public static void benchLookup(BinaryTree binaryTree, Integer[] keys) {
        for(Integer key : keys) {
            binaryTree.lookup(key);
        }
    }*/

    public static BinaryTree newTree(Integer[] keys) {
        BinaryTree binaryTree = new BinaryTree();

        for(Integer key : keys) {
            binaryTree.add(key, key);;
        }

        return binaryTree;
    }

    public static void main(String[] args) {
        int k = 5;
        int maxSize = 3200;

        System.out.printf("%7s%8s%8s%8s\n","#N","Cons","Look","Binary");

        for(int size = 25; size <= maxSize; size*=2) {
            System.out.printf("%8d", size);

            Integer[] keys = unsorted(size);
            Integer[] lookKeys = unsorted(size);
            benchTime(k, keys, lookKeys);
        }
    }
}