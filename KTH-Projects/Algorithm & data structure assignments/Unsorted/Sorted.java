import java.util.Random;

public class Sorted {
    public static boolean search_unsorted(int[] array, int key) {
        for (int index = 0; index < array.length; index++) {
            if(array[index] == key) {
                return true;
            }
        }
        return false;
    }

    public static boolean binary_search(int[] array, int key) {
        int first = 0;
        int last = array.length-1;

        while(true){
            // jump to the middle
            int index = first + ((last-first)/2);
            //int index = (first + last) >>> 1;

            if(array[index] == key){
                return true;
            }

            if(array[index] < key && index < last) {
                // The index position holds something that is less than
                // what we're looking for, what is the first possible page?
                first = (index+1);
                continue;
            }

            if (array[index] > key && index > first) {
                // The index position holds something that is larger than
                // what we're looking for, what is the last possible page?
                last = (index-1);
                continue;
            }
            return false;
        }
    }

    private static int[] sorted(int n) {
        Random rnd = new Random();
        int[] array = new int[n];
        int nxt = 0;
        for(int i = 0; i < n; i++) {
            nxt += rnd.nextInt(10) + 1;
            array[i] = nxt;
        }
        return array;
    }

    private static int[] unsorted(int n) {
        int[] array = new int[n];
        return array;
    }

    public static void main(String[] args) {
        final int SIZE = 100;
        int [] unsortedArray = unsorted(SIZE);
        int [] sortedArray = sorted(SIZE);
        int [] sortedArray2 = sorted(SIZE);
        int key = 52;

        //double t0 = System.nanoTime();
        //search_unsorted(sortedArray, key);
        //double t1 = System.nanoTime();
        //double t = (t1-t0);

    }
}
