public class Complicated {
    public static void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j > 0 && array[j] < array[j-1]; j--) {
                swap(array, j, j-1);
            }
        }
    }

    public static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;      
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
