import java.security.SecureRandom;

class TreeHeapBench {

    private static Integer[] unsorted(int n) {
        SecureRandom rnd = new SecureRandom();	
        Integer[] array = new Integer[n];

        for (int i = 0; i < n ; i++) {
            array[i] = rnd.nextInt(10000)+1;
        }

        return array;
    }

    private static Integer[] unsortedincr(int n) {
        SecureRandom rnd = new SecureRandom();	
        Integer[] array = new Integer[n];

        for (int i = 0; i < n ; i++) {
            array[i] = rnd.nextInt(100)+10;
        }

        return array;
    }

    public static void TreeTest(TreeHeap heap, Integer[] num){
        for(int i = 0; i < num.length; i++) {
            heap.add(num[i], num[i]);
        }

        /* 
        for(int j = 10; j <= 100; j+=10){
            System.out.printf("%d%8d\n", j, heap.push(j));
        }*/
    }

    public static Integer pushTest(TreeHeap heap, Integer pushy) {
        /*for(int i = 0; i < pushy.length; i++){
            System.out.printf("%d%8d\n", i, heap.push(pushy[i]));
        }*/
        return heap.push(pushy);
    }

    public static void addTest(TreeHeap heap, Integer pushy) {
        Integer removed = heap.remove();
        heap.add(removed, pushy);
    }

    public static void pushTest2(TreeHeap heap, Integer pushy) {
        heap.push(pushy);
    }

    public static void main(String[] arg) {

	System.out.printf("# Adding values to a heap tree\n");
	System.out.printf("#%7s%8s%8s\n", "n", "v1", "v2");

	TreeHeap a = new TreeHeap();
    Integer[] array = unsorted(1023);
    Integer[] increm = unsortedincr(20);

    System.out.printf("%8d", 1023);

    int k = 1000;

    for(int j = 25; j <= 6400; j*=2) {
        double min = Double.POSITIVE_INFINITY;

        TreeTest(a, array);
        double t0 = System.nanoTime();
        //addTest(a, j);
        pushTest2(a, j);
        double t1 = System.nanoTime();
        double t = (t1 - t0);
        if (t < min)
            min = t;

        System.out.printf("%d%8.1f\n", j, (min/1000));
    }
	    
    //double min = Double.POSITIVE_INFINITY;

    /* 
    for (int i = 0; i < k; i++) {
        TreeTest(a, array);
        //System.out.printf("%8d\n", a.push(10));
        double t0 = System.nanoTime();
        addTest(a, increm);
        double t1 = System.nanoTime();
        double t = (t1 - t0);
        if (t < min)
            min = t;
    }

	System.out.printf("%8.1f", (min/1000));	*/    

    /* 
    for(int l = 0; l < increm.length; l++) {
        TreeTest(a, array);
        Integer res = pushTest(a, increm[l]);
        System.out.printf("%d%8d\n", l, res);
    }*/

    }
}
