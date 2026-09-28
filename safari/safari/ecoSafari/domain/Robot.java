package domain;
import java.awt.Color;

public class Robot extends Organism implements Entity
{
    private final EcoSafari habitat;
    private boolean hasActed;
    
    public Robot(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set(this, row, column);
    }
    public EcoSafari getHabitat(){
        return habitat;
    }
    public final Color getColor(){
        return Color.RED;
    }
    public final int shape(){
        return Entity.SQUARE;
    }
    
    public void tic(){
        if (!hasActed){
            Elephant elephant = habitat.findElephant();
            if (elephant !=null){
                int[] robotposition = habitat.find(this);
                int[] elephantposition = habitat.find(elephant);
                int rows=0;
                int columns=0;
                //compara posiciones en x para moverse
                if (elephantposition[0]>robotposition[0]){
                    rows=1;
                }
                else if (elephantposition[0]<robotposition[0]){
                    rows =-1;
                }
                //compara pero en vez de x en y
                if (elephantposition[1]>robotposition[1]){
                    columns=1;
                }
                else if (elephantposition[1]<robotposition[1]){
                    columns =-1;
                }
                //actualizamos la posicion
                int nuevafila = robotposition[0] + rows;
                int nuevacolumna = robotposition[1] + columns;
                
                //condicion de que si alcanza el elefante lo desaparece y ocupa la casilla
                if (habitat.get(nuevafila, nuevacolumna)==elephant){
                    elephant.disappear();
                    move(rows, columns);
                    changeEnergy(30);
                }
                //si no se sigue moviendo hacia el elefante
                else if (habitat.get(nuevafila, nuevacolumna)==null){
                    move(rows, columns);
                }
                changeEnergy(-5);
            }
        }
        hasActed=true;
    }
    public void tac(){
        hasActed=false;
    }
}