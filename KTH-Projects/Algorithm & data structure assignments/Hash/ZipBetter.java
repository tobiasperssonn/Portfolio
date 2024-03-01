import java.io.BufferedReader;
import java.io.FileReader;

public class ZipBetter {

    Integer[] keys;
    Integer[] data;
    int max;

    public class Node {

        String name;
        Integer pop;
        Node next;

        private Node(String name, Integer pop, Node next) {
            this.name = name;
            this.pop = pop;
            this.next = next;
        }
    }

    public ZipBetter(String file) {
        keys = new Integer[10000];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                Integer code = Integer.valueOf(row[0].replaceAll("\\s", ""));
                keys[i++] = code;
            }
            max = i-1;

        } catch (Exception e) {
            System.out.println(" file " + file + " not found");
        }
    }

    public void collisions(int mod) {

        data = new Integer[mod];

        for (int i = 0; i <= max; i++) {
            Integer index = keys[i] % mod;

            while(data[index] != null) {    //Itterate the index if the position is occupied
                index = (index + 1) % mod;
            }

            data[index] = keys[i];

            if(i == mod-1) {     //Double the array size it's full
                resize(data, mod*2);
                mod = mod*2;
            }
        }
    }

    public void resize(Integer[] array, int newMod) {
        Integer[] temp = new Integer[newMod];

        for(int i = 0; i < array.length; i++) {
            if(array[i] != null) {
                Integer index = array[i] % newMod;

                while(temp[index] != null) {    //Itterate the index if the position is occupied
                    index = (index + 1) % newMod;
                }

                temp[index] = array[i];
            }
        }

        data = temp;
    }

    public boolean lookup(Integer zip) {
        int mod = data.length;
        Integer index = zip % mod;

        while(data[index] != null) {
            if(data[index].equals(zip)) {
                return true;
            }
            index = (index + 1) % mod;
        }

        return false;
    }

    public int lookupCount(Integer zip) {
        int mod = data.length;
        Integer index = zip % mod;
        int counter = 0;

        while(data[index] != null) {
            counter++;
            if(data[index].equals(zip)) {
                return counter;
            }
            index = (index + 1) % mod;
        }

        return counter;
    }

}
