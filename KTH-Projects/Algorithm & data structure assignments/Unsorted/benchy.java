import java.util.Random;


class benchy {

    private static void linear(int[] array, int[] indx) {
	for (int i = 0; i < indx.length ; i++) {
	    Linear.search(array, indx[i]);
	}
    }
    

    private static void binary(int[] array, int[] indx) {
	for (int i = 0; i < indx.length ; i++) {
	    Binary.search(array, indx[i]);
	}
    }

       
    private static int[] sorted(int n) {
	Random rnd = new Random();	
	int[] array = new int[n];
	int nxt = rnd.nextInt(10);

	for (int i = 0; i < n ; i++) {
	    array[i] = nxt;
	    nxt += rnd.nextInt(10) + 1 ;
	}	
	return array;
    }


    private static int[] keys(int loop, int n) {
	Random rnd = new Random();	
	int[] indx = new int[loop];
	for (int i = 0; i < loop ; i++) {
	    indx[i] = rnd.nextInt(n*5);
	}	
	return indx;
    }

    private static double linear_t (int[] array1, int[] array2, int k, int loop) {
        double min = Double.POSITIVE_INFINITY;

	    for (int i = 0; i < k; i++) {
		double t0 = System.nanoTime();
        Duplicate.linear(array1, array2);
		double t1 = System.nanoTime();
		double t = (t1 - t0);
		if (t < min)
		    min = t;
	    }
        return min;
    }

    
    public static void main(String[] arg) {

	int[] sizes = {100,200,400,800,1600,3200,6400};

	System.out.printf("# searching through duplicates off two arrays of length n, time in ns\n");
	System.out.printf("#%12s%12s%12s%12s\n", "n", "linear", "binary", "pointer");
	for ( int n : sizes) {

	    int loop = 10000;
        int k = 10000;
	    
	    int[] array1 = sorted(n);
	    int[] array2 = sorted(n);

	    System.out.printf("%12d", n);
	    
	    double min = Double.POSITIVE_INFINITY;

	    for (int i = 0; i < k; i++) {
		double t0 = System.nanoTime();
        Duplicate.linear(array1, array2);
		double t1 = System.nanoTime();
		double t = (t1 - t0);
		if (t < min)
		    min = t;
	    }

	    System.out.printf("%12.0f", (min));	    

	    min = Double.POSITIVE_INFINITY;
	    
	    for (int i = 0; i < k; i++) {
		double t0 = System.nanoTime();
		Duplicate.binary(array1, array2);
		double t1 = System.nanoTime();
		double t = (t1 - t0);
		if (t < min)
		    min = t;
	    }

	    System.out.printf("%12.0f" , (min));
        
        min = Double.POSITIVE_INFINITY;
	    
	    for (int i = 0; i < k; i++) {
		double t0 = System.nanoTime();
		Duplicate.finalAlgo(array1, array2);
		double t1 = System.nanoTime();
		double t = (t1 - t0);
		if (t < min)
		    min = t;
	    }

	    System.out.printf("%12.0f\n" , (min));
	}
    }
}
