package domain;
import java.awt.Color;
/**
 * Write a description of class Robot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
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
                int filas=0;
                int columnas=0;
                //compara posiciones en x para moverse
                if (elephantposition[0]>robotposition[0]){
                    filas=1;
                }
                else if (elephantposition[0]<robotposition[0]){
                    filas =-1;
                }
                //compara pero en vez de x en y
                if (elephantposition[1]>robotposition[1]){
                    columnas=1;
                }
                else if (elephantposition[1]<robotposition[1]){
                    columnas =-1;
                }
                //actualizamos la posicion
                int nuevafila = robotposition[0] + filas;
                int nuevacolumna = robotposition[1] + columnas;
                
                //condicion de que si alcanza el elefante lo desaparece y ocupa la casilla
                if (habitat.get(nuevafila, nuevacolumna)==elephant){
                    elephant.disappear();
                    move(filas, columnas);
                    changeEnergy(30);
                }
                //si no se sigue moviendo hacia el elefante
                else if (habitat.get(nuevafila, nuevacolumna)==null){
                    move(filas, columnas);
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