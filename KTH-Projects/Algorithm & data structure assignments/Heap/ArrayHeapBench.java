import java.security.SecureRandom;

class ArrauHeapBench {

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

    public static void arrayTest(ArrayHeap heap, Integer[] num){
        for(int i = 0; i < num.length; i++) {
            heap.add(num[i]);
        }

        for(int i = 0; i < num.length; i++) {
            heap.remove();
        }
    }

    public static void treeTest(TreeHeap heap, Integer[] num){
        for(int i = 0; i < num.length; i++) {
            heap.add(num[i], num[i]);
        }

        for(int i = 0; i < num.length; i++) {
            heap.remove();
        }
    }

    public static void listTest(ListHeap heap, Integer[] num){
        for(int i = 0; i < num.length; i++) {
            heap.add(num[i], num[i]);
        }
        for(int i = 0; i < num.length; i++) {
            heap.remove();
        }
    }

    public static void arrayTest2(ArrayHeap heap, Integer[] num){
        for(int i = 0; i < num.length; i++) {
            heap.add(num[i]);
        }
        for(int i = 0; i < num.length; i++) {
            heap.remove();
        }
    }

    public static void pushTest(ArrayHeap heap, Integer pushy) {
        /*for(int i = 0; i < pushy.length; i++){
            System.out.printf("%d%8d\n", i, heap.push(pushy[i]));
        }*/
        heap.incramentValue(pushy);
    }

    public static void addTest(ArrayHeap heap, Integer pushy) {
        heap.remove();
        heap.add(pushy);
    }

    public static void main(String[] arg) {

	System.out.printf("# Adding values to a heap tree\n");
	System.out.printf("#%7s%8s%8s\n", "n", "v1", "v2");

	ArrayHeap a = new ArrayHeap(50);
    TreeHeap b = new TreeHeap();
    ListHeap c = new ListHeap();
    Integer[] array = unsorted(1023);
    Integer[] increm = unsortedincr(20);

    System.out.printf("%8d", 1023);

    int k = 1000;

    
    for(int j = 25; j <= 6400; j*=2) {
        double min = Double.POSITIVE_INFINITY;

        double t0 = System.nanoTime();
        //arrayTest(a, array);
        treeTest(b, array);
        //listTest(c, array);
        //addTest(a, j);
        //pushTest(a, j);
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
