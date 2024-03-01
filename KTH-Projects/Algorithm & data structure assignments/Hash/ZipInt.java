import java.io.BufferedReader;
import java.io.FileReader;

public class ZipInt {

    Node[] data;
    int max;

    public class Node {

        Integer code;
        String name;
        Integer pop;

        private Node(Integer code, String name, Integer pop) {
            this.code = code;
            this.name = name;
            this.pop = pop;
        }
    }

    public ZipInt(String file) {
        data = new Node[10000];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                Integer code = Integer.valueOf(row[0].replaceAll("\\s", ""));
                data[i++] = new Node(code, row[1], Integer.valueOf(row[2]));
            }
            max = i-1;

        } catch (Exception e) {
            System.out.println(" file " + file + " not found");
        }
    }

    public boolean linear(Integer zip) {
        for(int i = 0; i <= max; i++) {
            if(data[i].code.equals(zip))
                return true;
        }
        return false;
    }

    public boolean binary(Integer zip) {
        int low = 0;
        int high = max;
        int mid;
        int code;

        while(low <= high) {
            mid = low + ((high-low)/2);
            code = data[mid].code;

            if(code == zip)
                return true;

            if(code < zip)
                low = mid + 1;

            else
                high = mid - 1;
        }

        return false;
    }

}