package domain;
import java.awt.Color;


public class Bush extends Organism implements Entity{
    private final EcoSafari habitat;
    private boolean hasActed;
    private int age;

    public Bush(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity) this, row, column);
        hasActed = false;
        age = 0;
    }

    public EcoSafari getHabitat(){
        return habitat;
    }

    public final Color getColor(){
        return (age < 4 ? Color.GREEN : Color.YELLOW);
    }

    public void tic(){
        if (!hasActed){
            if (age >= 2 && closeElephant()){
                disappear();
            } else {
                age++;
            }
        }
        hasActed = true;
    }

    public void tac(){
        hasActed = false;
    }

    /**
     * Determines whether there is an Elephant in one of the four
     * cardinal neighboring cells (North, South, East, West).
     */
    private boolean closeElephant(){
        int[] pos = getHabitat().find(this);
        int r = pos[0];
        int c = pos[1];
        int[][] neighbors = {{-1,0}, {1,0}, {0,1}, {0,-1}}; // N, S, E, O
        for (int[] d : neighbors){
            Entity neighbor = getHabitat().get(r + d[0], c + d[1]);
            if (neighbor instanceof Elephant){
                return true;
            }
        }
        return false;
    }
}