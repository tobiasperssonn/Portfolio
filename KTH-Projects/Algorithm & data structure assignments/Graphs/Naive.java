public class Naive {

    private static Integer shortest(City from, City to, Integer max) {
        if (max < 0)
            return null;

        if (from == to)
            return 0;

        Integer shrt = null;

        for (int i = 0; i < from.neighbors.length; i++) {
            if (from.neighbors[i] != null) {
                Connection conn = from.neighbors[i];
                Integer tempDist = shortest(conn.destination, to, max-conn.distance);

                if (tempDist != null) {
                    tempDist += conn.distance;
                    if (shrt == null || tempDist < shrt) {
                        shrt = tempDist;
                    }   
                }
            }
        }
        return shrt;
    }        

    public static void main(String[] args) {

        Map map = new Map("C:/Programmering/AlgoData/Graphs/trains.csv");

        //String from = args[0];
        String from = "Göteborg";
        //String to = args[1];
        String to = "Umeå";
        //Integer max = Integer.valueOf(args[2]);
        Integer max = 706;

        long t0 = System.nanoTime();
        Integer dist = shortest(map.lookup(from), map.lookup(to), max);
        long time = (System.nanoTime() - t0)/1_000_000;

        System.out.println("shortest: " + dist + " min (" + time + " ms)");
    }
}
    