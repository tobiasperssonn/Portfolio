public class Linear {
    public static boolean search(int[] array, int key) {
        for (int index = 0; index < array.length; index++) {
            if(array[index] == key) {
                return true;
            }
            if((index+1) < array.length && array[index + 1] > key) {
                return false;
            }
        }
        return false;
    }

    public static boolean search_sorted(int[] array, int key) {
        for (int index = 0; index < array.length; index++) {
            if(array[index] == key) {
                return true;
            }
            if((index+1) < array.length && array[index + 1] > key) {
                return false; 
            }
        }
        return false;
    }

    /*public static boolean search_sorted(int[] array, int key) {
        int index = 0;

        while(index < array.length) {
            if(array[index] == key) {
                return true;
            }
            if (array[index+1] > key) {
                return false;
            }
            index++;
        }
        return false;
    }*/
}
