package domain;
import java.awt.Color;

public class Zebra extends Animal{

    public Zebra(EcoSafari habitat, int row, int column){
        super(habitat, row, column);
    }

    public final Color getColor(){
        return Color.MAGENTA;
    }

    public int steps(){
        return 2;
    }

    public boolean isFood(Entity entity){
        return entity instanceof Grass;
    }

    public int foodEnergyPercent(){
        return 25;
    }

    public Animal newborn(int row, int column){
        return new Zebra(getHabitat(), row, column);
    }
}