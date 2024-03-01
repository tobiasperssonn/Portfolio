public class TreeBinary {
 // Binary search!!!!!!!!
    public static boolean search(Integer[] array, int key) {
        int first = 0;
        int last = array.length-1;
        Integer testo = 0;

        while(true){
            // jump to the middle
            Integer index = first + ((last-first)/2);
            testo++;
            //int index = (first + last) >>> 1;

            if(array[index] == key){
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
            return false;
        }
    }
}
