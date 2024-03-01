import java.util.Random;


class QueueBench {

       
    private static Integer[] sorted(int n) {
        Random rnd = new Random();	
        Integer[] array = new Integer[n];
        Integer nxt = rnd.nextInt(10);

        for (int i = 0; i < n ; i++) {
            array[i] = nxt;
            nxt += rnd.nextInt(10) + 1 ;
        }	
        return array;
    }

    public static void queueTest(Integer size, Queue queue){
        for(Integer i = 0; i <= size; i++) {
            queue.add(i);
        }

    }

    public static void arrayQueueTest(Integer size, ArrayQueue queue){
        for(Integer i = 0; i <= size; i++) {
            queue.add(i);
        }

    }

    
    public static void main(String[] arg) {

	Integer[] sizes = {100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,3200};

	System.out.printf("# Adding and removing values from two queues\n");
	System.out.printf("#%7s%8s%8s\n", "n", "Linked", "Array");
	for ( int n : sizes) {
        Queue a = new Queue();
        ArrayQueue b = new ArrayQueue(50);

	    System.out.printf("%8d", n);

	    int k = 10000;
	    
	    double min = Double.POSITIVE_INFINITY;

	    for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            queueTest(n, a);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
	    }

	    System.out.printf("%8.1f", (min/1000));	    

	    min = Double.POSITIVE_INFINITY;
	    
	    for (int i = 0; i < k; i++) {
            double t0 = System.nanoTime();
            arrayQueueTest(n, b);
            double t1 = System.nanoTime();
            double t = (t1 - t0);
            if (t < min)
                min = t;
	    }

	    System.out.printf("%8.1f\n" , (min/1000));	    	    
	}
    }
}
