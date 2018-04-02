package project.network;

/**
 * Classe représentant une centrale hydraulique.
 * @author Jimenez
 * @see PowerPlant
 */
public class HydraulicPlant extends PowerPlant{
    public static final int DEFAULT_POWER = 5000;
    public static final int DEFAULT_DELAY = 0;
    /**
     * Constructeur.
     * @param s le nom de la centrale.
     */
    HydraulicPlant(String s) {
        super(s, DEFAULT_POWER, DEFAULT_DELAY);
    }
}
