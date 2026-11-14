package domain;
import java.lang.*;

/**
 * Write a description of class EcoSafariException here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EcoSafariException extends Exception{
    //ponemos los errores simples
    public static final String SAVE_ERROR="no se pudo guardar";
    public static final String OPEN_ERROR="no se pudo abrir";
    public static final String EXPORT_ERROR="no se pudo exportar el archivo";
    public static final String IMPORT_ERROR="no se pudo importar el archivo";
    public EcoSafariException(String message){
        super(message);
    }
}