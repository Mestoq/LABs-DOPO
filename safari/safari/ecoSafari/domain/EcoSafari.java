package domain;


public class EcoSafari{
 
    private static final int SIZE=25;
    private Entity[][] cells;
    
    /**
     * Constructs a new EcoSafari
     */
    public EcoSafari() {
        cells=new Entity[SIZE][SIZE];
        someEntities();
    }

    /**
     * Pupulates the EcoSafari with some entities
     */
    public void someEntities(){  
         Elephant dumbo = new Elephant(this, 5, 5);
         Elephant babar = new Elephant(this, 10, 10);
         Bush mopane = new Bush(this, 18, 18);
         Bush acacia = new Bush(this, 20, 20);
         Storm thor = new Storm(this, 15, 15);
         Storm tempest = new Storm(this, 1, 23);
         Alienelephant Orozco = new Alienelephant(this, 2, 2);
         Alienelephant Davila = new Alienelephant(this, 7, 7);
         Robot Juan = new Robot(this, 24,24);
         Robot David = new Robot(this, 4, 4);
    }
    
    /**
     * Returns the size of the EcoSafari 
     * @return 
     */
    public int  getSize(){
        return SIZE;
    }

    /**
     * Determines whether a position is inside the EcoSafari
     * @param r the row
     * @param c the column
     * @return 
     */
    public boolean isInside(int r, int c){
        return ((0<=r) && (r<SIZE) && (0<=c) && (c<SIZE));
    }
    
    /**
     * Returns the entity located at a specified position
     * @param r the row
     * @param c the column
     * @return 
     */
    public Entity get(int r,int c){
        return (isInside(r,c)? cells[r][c]: null);
    }

    /**
     * Places an entity at a specified position
     * @param r the row
     * @param c the column
     */
    public void set(Entity e, int r, int c){
        if (isInside(r,c)){ 
            cells[r][c]=e;
        }
    }

    
    /**
     * Finds the position of a specified entity
     * @param e the entity
     * @return an array {row, column} if the entity is found. null otherwise
     */
    public int[]  find(Entity e){
       int[] position=null;
       for (int r=0 ; r<SIZE && position== null; r++){
           for (int c=0 ; c<SIZE && position==null ;c++){
               if (cells[r][c]==e){
                   position=new int [] {r,c};
               }
           }
       }
       return position;
    }
    
    public Elephant findElephant(){
        for (int fila=0; fila < SIZE; fila++){
            for (int columna=0; columna < SIZE; columna++){
                if (cells[fila][columna] instanceof Elephant){
                    return(Elephant) cells[fila][columna];
                }
            }
        } 
        return null;
    }
 
    /**
     * Advances the simulation by one time step
     */
    //First, all entities execute their tic() action
    //Then, all entities execute their tac() actions
    public void ticTac(){  
        //primero hacemos el tic, nos guiamos en la logica del find que ya estaba definido cambiando solo lo que haga el tic y tac 
        for (int fila=0; fila < SIZE; fila++){
            for (int columna=0; columna < SIZE; columna++){
                if (cells[fila][columna] !=null){
                    cells[fila][columna].tic();
                }
            }
        }
        // el mismo codigo anterior pero con tac
        for (int fila=0; fila < SIZE; fila++){
            for (int columna=0; columna < SIZE; columna++){
                if (cells[fila][columna] !=null){
                    cells[fila][columna].tac();
                }
            }
        }        
    }

}
