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
         Alienelephant Orozco = new Alienelephant(this, 2, 2);
         Alienelephant Davila = new Alienelephant(this, 7, 7);
         Robot Juan = new Robot(this, 24,24);
         Robot David = new Robot(this, 4, 4);
         predatorPreyZone();
    }
    
    /**
     * Fills a rectangular zone of the EcoSafari with land.
     * Cells that fall outside the EcoSafari are ignored.
     */
    public void landZone(int row, int column, int height, int width){
        for (int r = row; r < row + height; r++){
            for (int c = column; c < column + width; c++){
                if (isInside(r, c)){
                    new Land(this, r, c);
                }
            }
        }
    }


    public void predatorPreyZone(){
        int row = 13;
        int column = 0;
        int height = 9;
        int width = 8;
        landZone(row, column, height, width);
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if ((3 * i + 5 * j) % 7 == 0){
                    new Grass(this, row + i, column + j);
                }
            }
        }
        int[][] zebras = {{1,1}, {3,5}, {5,2}, {7,6}};
        int[][] lions = {{2,3}, {6,4}};
        for (int[] z : zebras){
            new Zebra(this, row + z[0], column + z[1]);
        }
        for (int[] l : lions){
            new Lion(this, row + l[0], column + l[1]);
        }
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
     * @return 
     */
    public boolean isInside(int row, int column){
        return ((0<=row) && (row<SIZE) && (0<=column) && (column<SIZE));
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
     */
    public void set(Entity e, int row, int column){
        if (isInside(row,column)){ 
            cells[row][column]=e;
        }
    }

    
    /**
     * Finds the position of a specified entity
     */
    public int[]  find(Entity e){
       int[] position=null;
       for (int row=0 ; row<SIZE && position== null; row++){
           for (int column=0 ; column<SIZE && position==null ;column++){
               if (cells[row][column]==e){
                   position=new int [] {row,column};
               }
           }
       }
       return position;
    }
    
    public Elephant findElephant(){
        for (int row=0; row < SIZE; row++){
            for (int column=0; column < SIZE; column++){
                if (cells[row][column] instanceof Elephant){
                    return(Elephant) cells[row][column];
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
        for (int row=0; row < SIZE; row++){
            for (int column=0; column < SIZE; column++){
                if (cells[row][column] !=null){
                    cells[row][column].tic();
                }
            }
        }
        // el mismo codigo anterior pero con tac
        for (int row=0; row < SIZE; row++){
            for (int column=0; column < SIZE; column++){
                if (cells[row][column] !=null){
                    cells[row][column].tac();
                }
            }
        }        
    }

}