import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Map {

    private City[] cities;
    private final int mod = 541;    

    public Map(String file) {
        cities = new City[mod];

        try (BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                City cityA = lookup(row[0]);
                City cityB = lookup(row[1]);
                int dst = Integer.valueOf(row[2]);
                cityA.connect(cityB, dst);
                cityB.connect(cityA, dst);
                //System.out.println("STAD: " + row[0]);
                //System.out.println(cityA.neighbors[0].destination.name);
                //System.out.println("============================================");
            }
        } catch (Exception e) {
            System.out.println(" file " + file + " not found or corrupt");
        }
    }

    private Integer hash(String name) {
        int hash = 0;
        for (int i = 0; i < name.length(); i++) {
            hash = (hash*31 % mod) + name.charAt(i);
        }
        return hash % mod;
    }

    public City lookup(String name) {
        int cityIndex = hash(name);
        City city = cities[cityIndex];

        while (city != null && !city.name.equals(name)) {
            cityIndex = (cityIndex + 1) % mod;
            city = cities[cityIndex];
        }

        if (city == null) {
            city = new City(name);
            cities[cityIndex] = city;
        }

        return city;
    }
    
}
