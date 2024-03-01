public class Binary {

    public static boolean search(int[] array, int key) {
        int first = 0;
        int last = array.length-1;
        int testo = 0;

        while(true){
            // jump to the middle
            int index = first + ((last-first)/2);
            testo++;
            //int index = (first + last) >>> 1;

            if(array[index] == key){
                System.out.println(testo);
                return true;
            }

            if(array[index] < key && index < last) {
                // The index position holds something that is less than
                // what we're looking for, what is the first possible page?
                first = (index+1);
                continue;
            }

            if (array[index] > key && index > first) {
                // The index position holds something that is larger than
                // what we're looking for, what is the last possible page?
                last = (index-1);
                continue;
            }
            System.out.println(testo);
            return false;
        }
    }

    public static void main(String[] args) {
        int[] test = new int[64000000];
        double t1 = System.nanoTime();
        search(test, -4);
        double t2 = System.nanoTime();
        double t3 = (t2-t1);
        System.out.println(t3);
    }
}
