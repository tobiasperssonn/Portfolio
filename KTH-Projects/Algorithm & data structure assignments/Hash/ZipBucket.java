import java.io.BufferedReader;
import java.io.FileReader;

public class ZipBucket {

    Integer[] keys;
    Node[] data;
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

    public ZipBucket(String file) {
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

        //Linked list with all the buckets
        data = new Node[mod];

        for (int i = 0; i <= max; i++) {
            Integer index = keys[i] % mod;

            if (data[index] == null) {  //Skapar en ny nod ifall ingen kollision sker
                data[index] = new Node(null, keys[i], null);
            } else {    //Länkar samman vår nya nod med de andra i samma bucket
                Node Coll = new Node(null, keys[i], data[index]);
                data[index] = Coll;
            }

        }
    }

    public boolean lookup(Integer zip) {
        int mod = data.length;
        Integer index = zip % mod;

        if(data[index] == null) {   //Inget element med denna hash
            return false;
        } else {
            Node curr = data[index];
            while(curr != null) {   //Search through each element in the bucket
                if(curr.pop.equals(zip))
                    return true;

                curr = curr.next;
            }
            return false;
        }
    }

    public int lookupCount(Integer zip) {
        //Returning 0 equals no element found
        int mod = data.length;
        Integer index = zip % mod;
        int counter = 0;

        if(data[index] == null) {   //Inget element med denna hash
            return counter;
        } else {
            Node curr = data[index];
            while(curr != null) {   //Search through each element in the bucket
                counter++;
                if(curr.pop.equals(zip))
                    return counter;

                curr = curr.next;
            }
            return counter;
        }
    }

}
