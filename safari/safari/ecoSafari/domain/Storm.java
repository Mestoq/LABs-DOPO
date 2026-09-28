package domain;
import java.awt.Color;

/**
 * Represents a basic Storm in the EcoSafari.
 * Its center moves circularly (wrapping around the grid) in a
 * diagonal northeast direction, one cell per tic. It destroys
 * whatever it finds in its new center, and marks the affected
 * area (diameter three, i.e. its eight neighboring cells) gray
 * wherever those cells are empty, leaving occupied cells untouched.
 */
//Include the documentation
public class Storm implements Entity{
    private final EcoSafari habitat;
    private boolean hasActed;

    public Storm(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity) this, row, column);
        hasActed = false;
    }

    public EcoSafari getHabitat(){
        return habitat;
    }

    public Color getColor(){
        return Color.BLACK;
    }

    public void tic(){
        if (!hasActed){
            int[] pos = getHabitat().find(this);
            int size = getHabitat().getSize();
            int r = pos[0];
            int c = pos[1];
            int nr = (r - 1 + size) % size; 
            int nc = (c + 1) % size;       

            Entity victima = getHabitat().get(nr, nc);
            if (victima != null && victima != this){
                victima.disappear();
            }

            getHabitat().set(null, r, c);
            getHabitat().set(this, nr, nc);

            afectedArea(nr, nc, size);
        }
        hasActed = true;
    }

    public void tac(){
        hasActed = false;
    }

    /**
     * Marks the eight neighboring cells of the given center gray. A
     * cell is marked when it is empty, or when it already holds one
     * of this Storm's own StormMark instances (safe to refresh, since
     * both the old and the new mark are gray); real entities in the
     * area are left untouched.
     */
    private void afectedArea(int centroFila, int centroColumna, int size){
        int[][] vecinos = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
        for (int[] d : vecinos){
            int fr = (centroFila + d[0] + size) % size;
            int fc = (centroColumna + d[1] + size) % size;
            Entity ocupante = getHabitat().get(fr, fc);
            if (ocupante == null || ocupante instanceof StormMark){
                new StormMark(fr, fc);
            }
        }
    }

    /**
     * Temporary gray mark left behind by this Storm's affected area.
     * Inner (non-static) class: uses the enclosing Storm's habitat
     * directly, it doesn't need to keep its own reference to it.
     * Disappears on its next tic() only after having survived a full
     * tac() phase, so it always lasts exactly one complete round,
     * regardless of where in the sweep order it was created.
     */
    private class StormMark implements Entity{
        private boolean readyToVanish;

        private StormMark(int row, int column){
            getHabitat().set(this, row, column);
            readyToVanish = false;
        }

        public EcoSafari getHabitat(){
            return Storm.this.getHabitat();
        }

        public Color getColor(){
            return Color.GRAY;
        }

        public void tic(){
            if (readyToVanish){
                disappear();
            }
        }

        public void tac(){
            readyToVanish = true;
        }
    }
}