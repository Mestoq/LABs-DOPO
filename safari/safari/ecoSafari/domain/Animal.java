package domain;
import java.util.Random;

public abstract class Animal extends Organism implements Entity{
    private static final Random RANDOM = new Random();
    private static final int[][] MOORE =
        {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};

    private final EcoSafari habitat;
    private boolean hasActed;

    public Animal(EcoSafari habitat, int row, int column){
        this.habitat = habitat;
        habitat.set((Entity) this, row, column);
        hasActed = false;
    }

    public EcoSafari getHabitat(){
        return habitat;
    }

    public int shape(){
        return Entity.ROUND;
    }

    public abstract int steps();
    public abstract boolean isFood(Entity entity);
    public abstract int foodEnergyPercent();
    public abstract Animal newborn(int row, int column);

    public void tic(){
        if (!hasActed){
            hasActed = true;
            boolean moved = false;
            for (int i = 0; i < steps(); i++){
                if (step()){
                    moved = true;
                }
            }
            if (moved){
                changeEnergy(-((getEnergy() + 9) / 10)); 
            }
            if (getEnergy() > 0){
                eat();
                reproduce();
            } else {
                disappear();
            }
        }
    }

    public void tac(){
        hasActed = false;
    }


    public boolean disappearDead(){
        return Land.replace(this);
    }


    private boolean step(){
        int[] position = getHabitat().find(this);
        int r = position[0];
        int c = position[1];
        int[][] options = new int[MOORE.length][];
        int count = 0;
        for (int[] d : MOORE){
            if (getHabitat().get(r + d[0], c + d[1]) instanceof Land){
                options[count++] = new int[] {r + d[0], c + d[1]};
            }
        }
        boolean moved = (count > 0);
        if (moved){
            int[] target = options[RANDOM.nextInt(count)];
            Entity land = getHabitat().get(target[0], target[1]);
            getHabitat().set(land, r, c);
            getHabitat().set(this, target[0], target[1]);
        }
        return moved;
    }


    private void eat(){
        int[] position = getHabitat().find(this);
        for (int[] d : MOORE){
            Entity neighbor = getHabitat().get(position[0] + d[0], position[1] + d[1]);
            if (neighbor != null && isFood(neighbor)){
                neighbor.disappear();
                changeEnergy((getEnergy() * foodEnergyPercent() + 99) / 100);
                return;
            }
        }
    }


    private void reproduce(){
        int[] position = getHabitat().find(this);
        for (int[] d : MOORE){
            int r = position[0] + d[0];
            int c = position[1] + d[1];
            if (getHabitat().get(r, c) instanceof Land && hasMateNextTo(r, c)){
                Animal baby = newborn(r, c);
                baby.hasActed = true; // just born: rests during this round
                return;
            }
        }
    }

    private boolean hasMateNextTo(int row, int column){
        for (int[] d : MOORE){
            Entity neighbor = getHabitat().get(row + d[0], column + d[1]);
            if (neighbor != null && neighbor != this && neighbor.getClass() == getClass()){
                return true;
            }
        }
        return false;
    }
}