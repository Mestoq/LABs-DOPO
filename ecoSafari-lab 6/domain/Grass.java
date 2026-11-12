package domain;
import java.awt.Color;


public class Grass implements Entity{
    private final EcoSafari habitat;

    public Grass(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity) this, row, column);
    }

    public EcoSafari getHabitat(){
        return habitat;
    }

    public Color getColor(){
        return new Color(34, 139, 34);
    }

    public void tic(){
    }

    /**
     * When grass disappears (eaten), it is replaced by land.
     */
    public boolean disappear(){
        return Land.replace(this);
    }
}