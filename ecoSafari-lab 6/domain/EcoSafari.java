package domain;
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
public class EcoSafari implements Serializable{
 
    private static final int SIZE=25;
    private Entity[][] cells;
    //adicionamos este atributo que son los tipos que pueden ser importados o exportados
    private static final List<String> TYPES =Arrays.asList("Elephant", "Alienelephant","Bush", "Storm", "Robot", "Zebra", "Lion", "Grass", "Land");
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
    
    public EcoSafari open00(File file) throws EcoSafariException{
        throw new EcoSafariException("opcion open en contruccion. Archivo " + file.getName());
    }
    
    public void saveAs00(File file) throws EcoSafariException{
        throw new EcoSafariException("opcion save en contruccion. Archivo " + file.getName());
    }
    
    /** guarda el estado actual del ecosafari con todas su entidades usando serializable,  se crea un .dat y lanza excepcion si el archivo no se pudo guardar*/
    public void saveAs(File file) throws EcoSafariException{
        if(file==null){
            throw new EcoSafariException(EcoSafariException.SAVE_ERROR+"archivo no posible de guardar");
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(this);
        } catch (IOException e){
            throw new EcoSafariException(EcoSafariException.SAVE_ERROR+file.getName());
        }
    }
    /** lee el estado serializable del ecosafari de un file,  se lee un .dat con ecosafari, retorna el ecosafari guardado y lanza excepcion si el archivo no tiene un ecosafari guardado*/
    public EcoSafari open(File file) throws EcoSafariException{
        if(file==null){
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR+"archivo no posible de abrir");
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            return (EcoSafari) in.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e){
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR+file.getName());
        }
    }
    /**
     * importa un archivo tipo file y en este caso lanzala excepcion ya que esta en contruccion, en la parte de presentacion se maneja lo del menu desplegable y todo 
     */
    public static File import00(File file) throws EcoSafariException{
        throw new EcoSafariException("opcion import as en contruccion. Archivo " + file.getName());
    }
    //
    public void exportAs00(File file) throws EcoSafariException{
        throw new EcoSafariException("opcion export as en contruccion. Archivo " + file.getName());
    }
    /**
     * exporta el estado actual de ecosafari a un txt, en este solo se guarda el tipo de entidad y la posicion
     */
    
    public void exportAs(File file) throws EcoSafariException{
        if(file==null){
            throw new EcoSafariException(EcoSafariException.EXPORT_ERROR+"archivo no posible de exportar");
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))){
            for (int row = 0; row < SIZE; row++){
                for (int column = 0; column < SIZE; column++){
                    Entity e = cells[row][column];
                    if (e != null && TYPES.contains(e.getClass().getSimpleName())){
                        out.write(e.getClass().getSimpleName() + " " + row + ", "+ column);
                        out.newLine();
                    }
                }
            }            
        } catch (IOException e){
            throw new EcoSafariException(EcoSafariException.EXPORT_ERROR+file.getName());
        }
    }
    /**
     * es basicamente cada linea del txt de forma que aca guardamos el tipo que como se ve en el atributo de ecosafari
     * es el elemento que puede ser elefante, arbusto, etc y el row y column hace referencia la posicion en la cuadricula
     */
    private static class Item{
        private final String type;
        private final int row;
        private final int column;
        
        private Item(String type, int row, int column){
            this.type = type;
            this.row = row;
            this.column = column;
        }
    }
    /**
     * es basicamente nuestro traductor de lineas entonces cuando lee una linea en el txt lo que hace es que la pasa a un item 
     * 
     */
    private Item parse(String line){
        String[] parts = line.split("\\s+", 2); // separo la linea 
        if (parts.length !=2 || !TYPES.contains(parts[0])){
            return null;
        }
        String[] position = parts[1].split(",");
        if(position.length !=2){
            return null;
        }
        try{
            int row = Integer.parseInt(position[0].trim());
            int column =  Integer.parseInt(position[1].trim());
            return (isInside(row, column) ? new Item(parts[0], row, column) : null);
        }catch (NumberFormatException e){
            return null;
        }
    }
    
    /**
     *  crea la entidad de un item
     * 
     */
    private void create(Item item){
        if(item.type.equals("Elephant")){
            new Elephant(this, item.row, item.column);
        }
        else if(item.type.equals("Alienelephant")){
            new Alienelephant(this, item.row, item.column);
        }
        else if(item.type.equals("Bush")){
            new Bush(this, item.row, item.column);
        }
        else if(item.type.equals("Storm")){
            new Storm(this, item.row, item.column);
        }
        else if(item.type.equals("Robot")){
            new Robot(this, item.row, item.column);
        }
        else if(item.type.equals("Zebra")){
            new Zebra(this, item.row, item.column);
        }
        else if(item.type.equals("Lion")){
            new Lion(this, item.row, item.column);
        }
        else if(item.type.equals("Grass")){
            new Grass(this, item.row, item.column);
        }
        else if(item.type.equals("Land")){
            new Land(this, item.row, item.column);
        }
    }
    /**
     * importa items de un txt, de forma que los adiciona a ecosafari, usamos formato fila y columna, si haay un error en una linea no se adiciona nada 
     */
    public void import_(File file) throws EcoSafariException{
        if(file==null){
            throw new EcoSafariException(EcoSafariException.IMPORT_ERROR+"archivo no posible de importar");
        }
        List<Item> items = new ArrayList<Item>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = in.readLine()) !=null){
                line = line.trim();
                if (!line.isEmpty()){
                    Item item = parse(line);
                    if (item == null){
                        throw new EcoSafariException(EcoSafariException.IMPORT_ERROR+file.getName());
                    }
                    items.add(item);
                }
            }            
        } catch (IOException e){
            throw new EcoSafariException(EcoSafariException.IMPORT_ERROR+file.getName());
        }
        for(Item item : items){
            create(item);
        }
    }
    
    
}