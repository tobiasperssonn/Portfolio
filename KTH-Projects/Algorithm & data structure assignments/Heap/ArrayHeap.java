public class ArrayHeap {
    private Integer[] heap;
    private int k;

    public ArrayHeap(int startSize) {
        heap = new Integer[startSize];
        k = 0;
    }

    public void add(Integer value) {
        if(k == heap.length) {
            resize(heap.length*2);
        }

        //Add the new item
        heap[k] = value;
        //Check the item against the parent
        bubble(k);
        k++;
    }

    private void bubble(int index) {
        //Cast the answer to an int so that we only need one formula
        int parent = (int)(index - 1) / 2;

        while(index > 0 && heap[index] < heap[parent]) {
            Integer temp = heap[index];
            heap[index] = heap[parent];
            heap[parent] = temp;

            index = parent;
            parent = (int)(index - 1) / 2;
        }
    }

    public Integer remove() {
        if(k == 0) {
            return null;
        }

        Integer root = heap[0];
        heap[0] = heap[k - 1];
        k--;

        sink(0);

        if(k < heap.length/4) {
            resize(heap.length/2);
        }

        return root;
    }

    private void sink(int index) {
        int leftChild = 2*index+1;
        int rightChild = 2*index+2;
        int min = index;

        //Find out if one of the children has a smaller value
        if(leftChild < k && heap[leftChild] < heap[min]) {
            min = leftChild;
        }

        if(rightChild < k && heap[rightChild] < heap[min]) {
            min = rightChild;
        }

        //Swap the values
        if(min != index) {
            Integer temp = heap[index];
            heap[index] = heap[min];
            heap[min] = temp;

            //Continue to go through the tree via recursion
            sink(min);
        }
    }

    public void incramentValue(int incr) {
        heap[0] += incr;

        sink(0);
    }

    private void resize(int size) {

        Integer[] newArray = new Integer[size];

        for(int i = 0; i < k; i++){
            newArray[i] = heap[i];
        }

        heap = newArray;
    }

    public static void main(String[] args) {
        ArrayHeap heapA = new ArrayHeap(2);

        heapA.add(20);
        heapA.add(2);
        heapA.add(4);
        heapA.add(2);
        System.out.println(heapA.remove());
        heapA.incramentValue(20);
    }
}
