import java.io.BufferedReader;
import java.io.FileReader;

public class ZipKey {

    Node[] data;
    int max;

    public class Node {

        String name;
        Integer pop;

        private Node(String name, Integer pop) {
            this.name = name;
            this.pop = pop;
        }
    }

    public ZipKey(String file) {
        data = new Node[100000];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                Integer code = Integer.valueOf(row[0].replaceAll("\\s", ""));
                data[code] = new Node(row[1], Integer.valueOf(row[2]));
                i++;
            }
            max = i-1;

        } catch (Exception e) {
            System.out.println(" file " + file + " not found");
        }
    }

    public boolean lookup(String zip) {
        int zipCode = Integer.valueOf(zip.replaceAll("\\s", ""));

        if(zipCode < 99999 && data[zipCode] != null)
                return true;
                
        return false;
    }

}