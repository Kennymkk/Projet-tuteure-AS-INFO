package network;

/**
 *
 * @author Jimenez
 */
public class Group extends Node{
    // membres et méthodes statiques
    private int consumption;
    private String name;
    private SubStation station;
    
    private Group(){
        super();
    }
    
    Group(int power, String s){
        this();
        consumption = power;
        name = s;
    }
    
    @Override
    boolean isConnected(){
        return station != null;
    }
    
    // getters/setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    void setStation(SubStation station) {
        this.station = station;
    }

    public SubStation getStation() {
        return station;
    }
}
