public class Merge {
    private static void sort(int[] org, int[] aux, int lo, int hi) {
        if (lo != hi) {
            int mid = (lo + hi)/2;
            // sort the items from lo to mid
            sort(org, aux, lo, mid);
            // sort the items from mid+1 to hi
            sort(org, aux, mid+1, hi);
            // merge the two sections using the additional array
            merge(org, aux, lo, mid, hi);
        }
    }

    public static void sort(int[] org) {
        if (org.length == 0)
            return;

        int[] aux = new int[org.length];
        
        sort(org, aux, 0, org.length -1);

    }

    private static void merge(int[] org, int[] aux, int lo, int mid, int hi) {
        // copy all items from lo to hi from org to aux
        for (int i = lo; i <= hi; i++) {
            aux[i] = org[i];
        }

        // let's do the merging
        int i = lo; // the index in the first part
        int j = mid+1; // the index in the second part

        // for all indices from lo to hi
        for ( int k = lo; k <= hi; k++) {
            // if i is greater than mid then
            // move the j'th item to the org array, update j
            if(i > mid) {
                org[k] = aux[j];
                j++;
            }
            // else if j is greate than hi then
            // move the i'th item to the org array, update i
            else if(j > hi) {
                org[k] = aux[i];
                i++; 
            }
            // else if the i'th item is smaller than the j¨ath item,
            // move the i'th item to the org array, update i
            else if(aux[i] < aux[j]) {
                org[k] = aux[i];
                i++; 
            }
            // else
            // move the j'th item to the org array, update j
            else {
                org[k] = aux[j];
                j++;
            }
        }
    }

    /*
    public static void main(String[] args) {
        int[] arr = {2,52,1,4,124,5,45,6,754,2,3,412,532,55,532432,432,13,2313,421,32};

        sort(arr);

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
    */
        
}
