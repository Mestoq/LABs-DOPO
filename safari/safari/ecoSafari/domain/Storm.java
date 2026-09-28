package domain;
import java.awt.Color;

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

            Entity victim = getHabitat().get(nr, nc);
            if (victim != null && victim!= this){
                victim.disappear();
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
     * the cells suroinding the center are given center gray color
     * the cell is marked when it is empty,real entities in the
     * area will left untouched.
     */
    private void afectedArea(int centerRow, int centerColumn, int size){
        int[][] vecinos = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};
        for (int[] d : vecinos){
            int fr = (centerRow + d[0] + size) % size;
            int fc = (centerColumn + d[1] + size) % size;
            Entity ocupied = getHabitat().get(fr, fc);
            if (ocupied == null || ocupied instanceof StormMark){
                new StormMark(fr, fc);
            }
        }
    }

    /**
     * Gray mark left behind by this Storm's affected area.
     * Disappears on its next tic() only after having survived a full
     * tac()
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