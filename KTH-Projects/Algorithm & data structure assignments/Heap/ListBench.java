class Listbench {

    public static void ListTest(Integer size, ListHeap list){
        for(Integer i = 0; i <= size; i++) {
            list.add(i,i);
        }

        for(Integer i = 0; i <= size; i++) {
            //list.remove();
        }
    }
    
    public static void ListTestAlt(Integer size, ListHeapAlt list){
        for(Integer i = 0; i <= size; i++) {
            list.add(i,i);
        }

        for(Integer i = 0; i <= size; i++) {
            list.remove();
        }
    }


    
    public static void main(String[] arg) {

	Integer[] sizes = {100,200,400,800,1600,3200};

	System.out.printf("# Adding and removing values from two queues\n");
	System.out.printf("#%7s%8s%8s\n", "n", "v1", "v2");
	for ( int n : sizes) {
        ListHeap a = new ListHeap();
        ListHeapAlt b = new ListHeapAlt();

	    System.out.printf("%8d", n);

	    int k = 10000;
	    
	    double min = Double.POSITIVE_INFINITY;

	    for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            ListTest(n, a);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
	    }

	    System.out.printf("%8.1f", (min/1000));	    

	    min = Double.POSITIVE_INFINITY;
	    
	    for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            ListTestAlt(n, b);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
	    }

	    System.out.printf("%8.1f\n" , (min/1000));	    	    
	}
    }
}
