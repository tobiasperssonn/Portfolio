public class Paths {

    City[] path;
    int sp;

    public Paths() {
        path = new City[54];
        sp = 0;
    }

    private Integer shortest(City from, City to) {
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
                Integer tempDist = shortest(conn.destination, to);

                if (tempDist != null) {
                    tempDist += conn.distance;
                    if (shrt == null || tempDist < shrt) {
                        shrt = tempDist;
                    }
                }
            }
        }
        path[sp--] = null;
        return shrt;
    }        

    public static void main(String[] args) {

        Map map = new Map("C:/Programmering/AlgoData/Graphs/trains.csv");
        Paths path = new Paths();

        //String from = args[0];
        String from = "Göteborg";
        //String to = args[1];
        String to = "Umeå";

        long t0 = System.nanoTime();
        Integer dist = path.shortest(map.lookup(from), map.lookup(to));
        long time = (System.nanoTime() - t0)/1_000_000;

        System.out.println("shortest: " + dist + " min (" + time + " ms)");
    }
}
