public class Duplicate {
    public static int linear(int[] array1, int[] array2) {
        int doublets = 0;
        for(int i = 0; i < array1.length; i++){
            if(Linear.search(array2, array1[i])){
                doublets++;
            }
        }
        return doublets;
    }

    public static int binary(int[] array1, int[] array2) {
        int doublets = 0;
        for(int i = 0; i < array1.length; i++){
            if(Binary.search(array2, array1[i])){
                doublets++;
            }
        }
        return doublets;
    }

    public static int finalAlgo(int[] array1, int[] array2) {
        int doublets = 0;
        int i = 0;
        int j = 0;
        
        while(true) {
            if(i == array1.length | j == array2.length) {
                break;
            }

            if(array1[i] == array2[j]) {
                doublets++;
                i++;
                j++;
                continue;
            }

            if(array1[i] < array2[j]) {
                i++;
            }
            else {
                j++;
            }
        }

        return doublets;
    }
}
