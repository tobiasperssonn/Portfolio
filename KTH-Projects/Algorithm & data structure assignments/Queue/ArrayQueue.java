public class ArrayQueue {
    private Integer[] array;
    private int n;
    private int i;
    private int k;
    private int amount;

    public ArrayQueue(int size) {
        array = new Integer[size];
        n = size;
        i = 0;
        k = 0;
        amount = 0;
    }

    public void add(Integer item) {
        if((k+1) % n == i) {
            resize(2*n);
        }
        
        array[k] = item;
        k = (k + 1) % n;
        amount++;
    }

    public Integer remove() {
        //Queue is empty
        if(i == k) {
            return null;
        }

        Integer removedValue = array[i];
        array[i] = null;
        i = (i + 1) % n;
        amount--;

        if(amount < n/4) {
            resize(n/2);
        }

        return removedValue;
    }

    private void resize(int size) {

        Integer[] newArray = new Integer[size];
        int counter = 0;

        for(int j = i; j < (n-1); j++){
            newArray[counter++] = array[j];
        }

        for(int j = 0; j < (k-1); j++){
            newArray[counter++] = array[j];
        }

        i = 0;
        k = n;
        n = size;
        array = newArray;
    }
}
