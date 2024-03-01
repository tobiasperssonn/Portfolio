public class City {

    String name;
    Connection[] neighbors;
    
    public City(String name) {
        this.name = name;
        this.neighbors = new Connection[587];
    }

    public void connect(City nxt, int dst){
        for(int i = 0; i < neighbors.length; i++) {
            if(neighbors[i] == null) {
                neighbors[i] = new Connection(nxt, dst);
                break;
            }
        }
    }
}
