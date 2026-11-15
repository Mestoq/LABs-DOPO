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
    public void saveAs01(File file) throws EcoSafariException{
        if (file == null){
            throw new EcoSafariException(EcoSafariException.SAVE_ERROR + "archivo no posible de guardar");
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(this);
        } catch (IOException e){
            throw new EcoSafariException(EcoSafariException.SAVE_ERROR + file.getName());
        }
    }
    /** lee el estado serializable del ecosafari de un file,  se lee un .dat con ecosafari, retorna el ecosafari guardado y lanza excepcion si el archivo no tiene un ecosafari guardado*/
    public EcoSafari open01(File file) throws EcoSafariException {
        if (file == null) {
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR + "archivo no posible de abrir");
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (EcoSafari) in.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR + file.getName());
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
    
    public void exportAs01(File file) throws EcoSafariException{
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
    public void import01(File file) throws EcoSafariException{
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
    /**
     * Con mejor manejo de excepciones y con un nuevo llamado
     */
    public void saveAs(File file) throws EcoSafariException{
        checkSaveTarget(file);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
            out.writeObject(this);
        } catch (NotSerializableException e){
            throw new EcoSafariException(EcoSafariException.SAVE_ERROR
                    + EcoSafariException.NOT_SERIALIZABLE + file.getName());
        } catch (IOException e){
            throw new EcoSafariException(EcoSafariException.SAVE_ERROR + file.getName());
        }

    }
    /**
     * Nuevo metodo que nace para manejar el file y si algo sucede durante el guardado
     * a diferencia de lo que fue open aqui solo lanza y propaga, ya save lo atiende
     */
    private void checkSaveTarget(File file) throws EcoSafariException{
        String p = EcoSafariException.SAVE_ERROR;
        if (file == null){
            throw new EcoSafariException(p + EcoSafariException.FILE_NULL);
        }
        if (file.isDirectory()){
            throw new EcoSafariException(p + EcoSafariException.IS_DIRECTORY + file.getName());
        }
        if (!file.getName().toLowerCase().endsWith(".dat")){
            throw new EcoSafariException(p + EcoSafariException.BAD_EXTENSION + file.getName());
        }
        File parent = file.getAbsoluteFile().getParentFile();
        if (parent == null || !parent.exists()){
            throw new EcoSafariException(p + EcoSafariException.NO_PARENT + file.getName());
        }
        if ((file.exists() && !file.canWrite()) || (!file.exists() && !parent.canWrite())){
            throw new EcoSafariException(p + EcoSafariException.NO_WRITE_PERMISSION + file.getName());
        }
    }
    /**
     * Con mejor manejo de excepciones
     */
    public EcoSafari open(File file) throws EcoSafariException{
        checkOpenSource(file);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            return (EcoSafari) in.readObject();
        } catch (StreamCorruptedException | EOFException e){
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR
                    + EcoSafariException.CORRUPT_FILE + file.getName());
        } catch (InvalidClassException | ClassNotFoundException e){
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR
                    + EcoSafariException.INCOMPATIBLE + file.getName());
        } catch (ClassCastException e){
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR
                    + EcoSafariException.NOT_A_SAFARI + file.getName());
        } catch (IOException e){
            throw new EcoSafariException(EcoSafariException.OPEN_ERROR + file.getName());
        }
    }
    /**
     * Nuevo metodo que nace para manejar el file a abrir y si algo sucede durante la lectura o la apertura del file
     * a diferencia de lo que fue open aqui como antes solo lanza y propaga, ya open lo atiende
     */
    private void checkOpenSource(File file) throws EcoSafariException{
        String p = EcoSafariException.OPEN_ERROR;
        if (file == null){
            throw new EcoSafariException(p + EcoSafariException.FILE_NULL);
        }
        if (!file.exists()){
            throw new EcoSafariException(p + EcoSafariException.FILE_NOT_FOUND + file.getName());
        }
        if (file.isDirectory()){
            throw new EcoSafariException(p + EcoSafariException.IS_DIRECTORY + file.getName());
        }
        if (!file.getName().toLowerCase().endsWith(".dat")){
            throw new EcoSafariException(p + EcoSafariException.BAD_EXTENSION + file.getName());
        }
        if (!file.canRead()){
            throw new EcoSafariException(p + EcoSafariException.NO_READ_PERMISSION + file.getName());
        }
        if (file.length() == 0){
            throw new EcoSafariException(p + EcoSafariException.EMPTY_FILE + file.getName());
        }
    }



    /** exportara el estado a un .txt con errores detallados y tira exception
      * con la causa detallada del error
      * */
    public void exportAs(File file) throws EcoSafariException{
        checkExportTarget(file);
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))){
            for (int row = 0; row < SIZE; row++){
                for (int column = 0; column < SIZE; column++){
                    Entity e = cells[row][column];
                    if (e != null && TYPES.contains(e.getClass().getSimpleName())){
                        out.write(e.getClass().getSimpleName() + " " + row + ", " + column);
                        out.newLine();
                    }
                }
            }
        } catch (IOException e){
            throw new EcoSafariException(EcoSafariException.EXPORT_ERROR + file.getName());
        }
    }

    private void checkExportTarget(File file) throws EcoSafariException{
        String p = EcoSafariException.EXPORT_ERROR;
        if (file == null){
            throw new EcoSafariException(p + EcoSafariException.FILE_NULL);
        }
        if (file.isDirectory()){
            throw new EcoSafariException(p + EcoSafariException.IS_DIRECTORY + file.getName());
        }
        if (!file.getName().toLowerCase().endsWith(".txt")){
            throw new EcoSafariException(p + EcoSafariException.BAD_EXTENSION_TXT + file.getName());
        }
        File parent = file.getAbsoluteFile().getParentFile();
        if (parent == null || !parent.exists()){
            throw new EcoSafariException(p + EcoSafariException.NO_PARENT + file.getName());
        }
        if ((file.exists() && !file.canWrite()) || (!file.exists() && !parent.canWrite())){
            throw new EcoSafariException(p + EcoSafariException.NO_WRITE_PERMISSION + file.getName());
        }
    }
    /** importara elementos desde un .txt con errores detallados
     *  ademas es atomica,osea, si alguna línea es inválida no se agrega ningún elemento atiende excepciones
     */
    public void import_(File file) throws EcoSafariException{
        checkImportSource(file);
        List<Item> items = new ArrayList<Item>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = in.readLine()) != null){
                line = line.trim();
                if (!line.isEmpty()){
                    items.add(parse01(line, file.getName()));
                }
            }
        } catch (IOException e){
            throw new EcoSafariException(EcoSafariException.IMPORT_ERROR + file.getName());
        }
        if (items.isEmpty()){
            throw new EcoSafariException(EcoSafariException.IMPORT_ERROR
                    + EcoSafariException.EMPTY_FILE + file.getName());
        }
        for (Item item : items){
            create(item);
        }
    }

    private void checkImportSource(File file) throws EcoSafariException{
        String p = EcoSafariException.IMPORT_ERROR;
        if (file == null){
            throw new EcoSafariException(p + EcoSafariException.FILE_NULL);
        }
        if (!file.exists()){
            throw new EcoSafariException(p + EcoSafariException.FILE_NOT_FOUND + file.getName());
        }
        if (file.isDirectory()){
            throw new EcoSafariException(p + EcoSafariException.IS_DIRECTORY + file.getName());
        }
        if (!file.getName().toLowerCase().endsWith(".txt")){
            throw new EcoSafariException(p + EcoSafariException.BAD_EXTENSION_TXT + file.getName());
        }
        if (!file.canRead()){
            throw new EcoSafariException(p + EcoSafariException.NO_READ_PERMISSION + file.getName());
        }
    }

    /**
     * Traduce una línea "Tipo fila, columna" a un Item o lanza la causa exacta del fallo.
     */
    private Item parse01(String line, String fileName) throws EcoSafariException{
        String p = EcoSafariException.IMPORT_ERROR;
        String[] parts = line.split("\\s+", 2);
        if (parts.length != 2){
            throw new EcoSafariException(p + EcoSafariException.BAD_FORMAT + fileName);
        }
        if (!TYPES.contains(parts[0])){
            throw new EcoSafariException(p + EcoSafariException.UNKNOWN_TYPE + fileName);
        }
        String[] position = parts[1].split(",");
        if (position.length != 2){
            throw new EcoSafariException(p + EcoSafariException.BAD_FORMAT + fileName);
        }
        int row, column;
        try {
            row = Integer.parseInt(position[0].trim());
            column = Integer.parseInt(position[1].trim());
        } catch (NumberFormatException e){
            throw new EcoSafariException(p + EcoSafariException.BAD_NUMBER + fileName);
        }
        if (!isInside(row, column)){
            throw new EcoSafariException(p + EcoSafariException.OUT_OF_BOARD + fileName);
        }
        return new Item(parts[0], row, column);
    }
    
}