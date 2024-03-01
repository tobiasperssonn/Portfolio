
public class Simple {

    public static void sort(int[] array) {
        for (int i = 0; i < array.length -1; i++) {
            int candidate = array[i];
            int min_idx = i;
            for (int j = i; j < array.length ; j++) {
                if (array[j] < array[min_idx]) {
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

    /* 
    public static void main(String[] args) {
        int[] arr = {23,13,42,13,21,45,234,53,3,13,4,14,2,131,5325};

        sort(arr);

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
    */

}