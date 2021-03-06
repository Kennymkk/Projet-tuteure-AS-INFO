package project.network.error;

import java.util.ArrayList;
import project.network.base.Line;
import project.network.base.PowerPlant;
import project.network.base.SubStation;

/**
 *
 * @author jimenezc
 */
public class NeedOptimizationError extends NetworkError{
    private SubStation station;
    public NeedOptimizationError(SubStation station){
        super();
        this.station = station;
    }
    
    public SubStation getStation(){
        return this.station;
    }
    /**
     * @see NetworkError#solve() 
     */
    @Override
    public void solve(){
        int powerNeeded = this.station.getPowerIn();
        ArrayList<PowerPlant> plants = new ArrayList<>();
        for (Line l : this.station.getLines()){
            PowerPlant plant = l.getIn();
            if (plant.getState() == PowerPlant.State.ON){
                plants.add(plant);
                this.station.giveBackPower(l, l.getActivePower());
            }
        }
        plants.sort(PowerPlant.powerComparator);
        for (PowerPlant p : plants){
            int powerAsked = (powerNeeded <= Math.abs(p.getActivePower()))?powerNeeded:Math.abs(p.getActivePower());
            powerNeeded -= p.grantToStation(station, powerAsked);
        }
        this.setSolved(true); // optimisme
    }
}
