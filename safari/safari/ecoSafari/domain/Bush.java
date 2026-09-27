package domain;
import java.awt.Color;

/**
 * Represents a bush in the EcoSafari.
 * A bush is green while young and turns yellow after aging 4 tics.
 * Once it is at least 2 tics old, on every tic it checks its four
 * cardinal neighboring cells, in priority order North, South, East,
 * West, and if any of them holds an Elephant, the bush is eaten and
 * disappears.
 */
//Include the documentation
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
     * @return
     */
    private boolean closeElephant(){
        int[] pos = getHabitat().find(this);
        int r = pos[0];
        int c = pos[1];
        int[][] vecinos = {{-1,0}, {1,0}, {0,1}, {0,-1}}; // N, S, E, O
        for (int[] d : vecinos){
            Entity vecino = getHabitat().get(r + d[0], c + d[1]);
            if (vecino instanceof Elephant){
                return true;
            }
        }
        return false;
    }
}