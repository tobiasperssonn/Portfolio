import java.io.BufferedReader;
import java.io.FileReader;

public class Zip {

    Node[] data;
    int max;

    public class Node {

        String code;
        String name;
        Integer pop;

        private Node(String code, String name, Integer pop) {
            this.code = code;
            this.name = name;
            this.pop = pop;
        }
    }

    public Zip(String file) {
        data = new Node[10000];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data[i++] = new Node(row[0], row[1], Integer.valueOf(row[2]));
            }
            max = i-1;

        } catch (Exception e) {
            System.out.println(" file " + file + " not found");
        }
    }

    public boolean linear(String zip) {
        String code;
        String zipCode = zip.replaceAll("\\s+", "");
        for(int i = 0; i <= max; i++) {
            code = data[i].code.replaceAll("\\s+", "");
            if(code.equals(zipCode))
                return true;
        }
        return false;
    }

    public boolean binary(String zip) {
        int low = 0;
        int high = max;
        int mid;
        String code;
        String zipCode = zip.replaceAll("\\s+", "");

        while(low <= high) {
            mid = low + ((high-low)/2);
            code = data[mid].code.replaceAll("\\s+", "");

            if(code.equals(zipCode))
                return true;

            if(code.compareTo(zipCode) < 0)
                low = mid + 1;

            else
                high = mid - 1;
        }

        return false;
    }

}