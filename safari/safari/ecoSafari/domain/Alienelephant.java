package domain;
import java.awt.Color;


public class Alienelephant extends Elephant{
    private boolean hasActed;
    
    public Alienelephant(EcoSafari habitat,int row, int column){
        //nos apoyamos en el contructor que ya habia de elephant con super
        super(habitat, row, column);  
        this.hasActed=false;
    }
    @Override
    public Color getColor(){
        return Color.BLUE;
    }
    @Override
    public void tic(){
        if ((! hasActed) && (move(2, 1))) {
            changeEnergy(-20);
            if (getEnergy()==0){
                disappear();
            }
        }
        hasActed=true;
    }
    @Override
    public void tac(){
        hasActed=false;
    }    
}