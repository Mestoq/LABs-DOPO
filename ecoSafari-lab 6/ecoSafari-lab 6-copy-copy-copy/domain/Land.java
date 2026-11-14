package domain;
import java.awt.Color;
import java.util.Random;

public class Land implements Entity{
    public static final double GRASS_PROBABILITY = 0.10;
    private static final Random RANDOM = new Random();

    private final EcoSafari habitat;
    private final double grassProbability;
    private boolean hasActed;

    /**
     * Creates land that grows grass with the default probability (10%)
     */
    public Land(EcoSafari habitat, int row, int column){
        this(habitat, row, column, GRASS_PROBABILITY);
    }


    public Land(EcoSafari habitat, int row, int column, double grassProbability){
        this.habitat = habitat;
        this.grassProbability = grassProbability;
        habitat.set((Entity) this, row, column);
        hasActed = false;
    }

    public EcoSafari getHabitat(){
        return habitat;
    }

    public Color getColor(){
        return new Color(210, 180, 140);
    }

    public void tic(){
        if (!hasActed && RANDOM.nextDouble() < grassProbability){
            int[] position = getHabitat().find(this);
            if (position != null){
                new Grass(getHabitat(), position[0], position[1]);
            }
        }
        hasActed = true;
    }

    public void tac(){
        hasActed = false;
    }


    static boolean replace(Entity entity){
        EcoSafari habitat = entity.getHabitat();
        int[] position = habitat.find(entity);
        boolean ok = false;
        if (position != null){
            Land land = new Land(habitat, position[0], position[1]);
            land.hasActed = true;
            ok = true;
        }
        return ok;
    }
}