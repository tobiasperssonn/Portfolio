
public class Reverse {

    public static void sort(int[] array) {
        for (int i = 0; i < array.length -1; i++) {
            int candidate = array[i];
            int min_idx = i;
            for (int j = i; j < array.length ; j++) {
                if (array[j] > array[min_idx]) {
                    min_idx = j;
                    candidate = array[min_idx];
                }
            }
            if (min_idx != i) {
                array[min_idx] = array[i];
                array[i] = candidate;
            }
        }
    }

}