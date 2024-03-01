import java.util.Random;
import java.security.SecureRandom;


class SortBench {
    private static int[] unsorted(int n) {
        Random rnd = new SecureRandom();	
        int[] array = new int[n];

        for (int i = 0; i < n ; i++) {
            array[i] = rnd.nextInt();
        }
        
        //Reverse.sort(array);

        return array;
    }

    
    public static void main(String[] arg) {

        int[] sizes = {100,200,400,800,1600,3200};

        System.out.printf("# Sorting an array of length n, time in ms\n");
        System.out.printf("#%7s%8s%8s%8s%8s\n", "n", "Sel", "In" , "Mer", "MerOpt");
        for ( int n : sizes) {
            
            int[] array = unsorted(n);

            System.out.printf("%8d", n);

            int k = 1000;
            int prefix = 1000;
            
            double min = Double.POSITIVE_INFINITY;

            for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            Simple.sort(array);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
            }

            System.out.printf("%8.1f", min/prefix);	   
            
            array = unsorted(n);

            min = Double.POSITIVE_INFINITY;
            
            for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            Complicated.sort(array);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
            }

            System.out.printf("%8.1f" , min/prefix);

            array = unsorted(n);
            
            min = Double.POSITIVE_INFINITY;
            
            for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            Merge.sort(array);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
            }

            System.out.printf("%8.1f" , min/prefix);

            array = unsorted(n);
            
            min = Double.POSITIVE_INFINITY;
            
            for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            MergeOpt.sort(array);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
            }

            System.out.printf("%8.1f\n" , min/prefix);
        }
    
    }
}
