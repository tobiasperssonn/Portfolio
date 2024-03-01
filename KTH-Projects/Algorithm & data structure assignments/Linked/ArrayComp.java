import java.security.SecureRandom;
import java.util.Random;

public class ArrayComp {
    public static void main(String[] args) {
        System.out.printf("#%7s%8s%8s\n","N","Linked", "Array");

        int fixedSize = 10000;
        int k = 100;

        LinkedList a, b;
        int[] arrayA, arrayB;
        
        
        for(int size = 25; size < fixedSize; size*=2){

            System.out.printf("%8d", size+size);

            a = new LinkedList(size);
            b = new LinkedList(size);
            linkedTime(a, b, k, size);

            arrayA = unsorted(size);
            arrayB = unsorted(size);
            arrayTime(arrayA, arrayB, k, size);

            System.out.println();
        }
        

        /*
        for(int size = 25; size < fixedSize; size*=2){

            System.out.printf("%8d", size);

            arrayA = unsorted(3000);
            arrayB = unsorted(size);
            arrayTime(arrayA, arrayB, k, size);

            System.out.println();
        }
        */
    }

    public static void arrayTime(int[] a, int[] b, int k, int size){
        double min = Double.POSITIVE_INFINITY;

        for(int j = 0; j < k; j++){
            double t0 = System.nanoTime();
            appendArray(a, b);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min) {
                min = t;
            }
        }     
        System.out.printf("%8.1f" , min/1000);
    }

    public static void linkedTime(LinkedList a, LinkedList b, int k, int size){
        double min = Double.POSITIVE_INFINITY;

        for(int j = 0; j < k; j++){
            double t0 = System.nanoTime();
            a.append(b);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min) {
                min = t;
            }
        }     
        System.out.printf("%8.1f" , min/1000);
    }

    public static void appendArray(int[] a, int[] b){
        int appendLen = (a.length+b.length);
        int[] newArray = new int[appendLen];

        for(int i = 0; i < a.length; i++){
            newArray[i] = a[i];
        }

        for(int i = 0; i < b.length; i++){
            newArray[i+a.length] = b[i];
        }
    }

    private static int[] unsorted(int n) {
        Random rnd = new SecureRandom();	
        int[] array = new int[n];

        for (int i = 0; i < n ; i++) {
            array[i] = rnd.nextInt();
        }

        return array;
    }
}
