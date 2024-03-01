public class DynamicPaths {

    City[] path;
    int sp;

    public DynamicPaths() {
        path = new City[54];
        sp = 0;
    }

    private Integer shortest(City from, City to, Integer max) {

        if (from == to)
            return 0;

        Integer shrt = null;

        for (int i = 0; i < sp; i++) {
            if (path[i] == from)
                return null;
        }

        path[sp++] = from;   

        for (int i = 0; i < from.neighbors.length; i++) {
            if (from.neighbors[i] != null) {
                Connection conn = from.neighbors[i];
                if (max != null && conn.distance > max) {
                    continue;
                }
                Integer tempDist = shortest(conn.destination, to, max);

                if (tempDist != null) {
                    tempDist += conn.distance;
                    if (shrt == null || tempDist < shrt) {
                        shrt = tempDist;
                        max = shrt;
                    }
                }
            }
        }
        path[sp--] = null;
        return shrt;
    }        

    public static void main(String[] args) {

        Map map = new Map("C:/Programmering/AlgoData/Graphs/trains.csv");
        DynamicPaths path = new DynamicPaths();

        //String from = args[0];
        String from = "Malmö";
        //String to = args[1];
        String to = "Kiruna";

        System.out.println("Input 1: " + map.lookup(from).name);
        System.out.println("Input 2: " + map.lookup(to).name);

        long t0 = System.nanoTime();
        Integer dist = path.shortest(map.lookup(from), map.lookup(to), null);
        long time = (System.nanoTime() - t0)/1_000_000;

        System.out.println("shortest: " + dist + " min (" + time + " ms)");
    }
}
