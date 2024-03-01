import java.io.BufferedReader;
import java.io.FileReader;

public class ZipHash {

    Integer[] keys;
    int max;

    public class Node {

        String name;
        Integer pop;

        private Node(String name, Integer pop) {
            this.name = name;
            this.pop = pop;
        }
    }

    public ZipHash(String file) {
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

        int[] data = new int[mod];
        int[] cols = new int[10];

        for (int i = 0; i < max; i++) {
            Integer index = keys[i] % mod;
            cols[data[index]]++;
            data[index]++;
        }

        System.out.print(mod);
        for (int i = 0; i < 10; i++) {
            System.out.print("\t" + cols[i]);
        }
        System.out.println();
    }

}
