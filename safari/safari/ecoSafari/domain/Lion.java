package domain;
import java.awt.Color;


public class Lion extends Animal{

    public Lion(EcoSafari habitat, int row, int column){
        super(habitat, row, column);
    }

    public final Color getColor(){
        return Color.ORANGE;
    }

    public int steps(){
        return 1;
    }

    public boolean isFood(Entity entity){
        return entity instanceof Zebra;
    }

    public int foodEnergyPercent(){
        return 50;
    }

    public Animal newborn(int row, int column){
        return new Lion(getHabitat(), row, column);
    }
}