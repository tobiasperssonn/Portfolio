public class LinkedBench {
    public static void main(String[] args) {
        System.out.printf("#%7s%8s%8s\n","N","A", "B");

        
        int fixedSize = 10000;
        int k = 10000;

        LinkedList a, b;
        
        for(int size = 25; size < fixedSize; size*=2){

            System.out.printf("%8d", size);

            a = new LinkedList(size);
            b = new LinkedList(fixedSize);
            benchTime(a, b, k, size);

            a = new LinkedList(size);
            b = new LinkedList(fixedSize);
            benchTime(b, a, k, size);

            System.out.println();
        }
    }

    public static void benchTime(LinkedList a, LinkedList b, int k, int size){
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

    public static void print(LinkedList list){
        for(LinkedList.Cell curr = list.first; curr != null; curr = curr.tail){
            System.out.println("addres is "  + curr);
            System.out.println("value is " + curr.head);
        }
    }
}
