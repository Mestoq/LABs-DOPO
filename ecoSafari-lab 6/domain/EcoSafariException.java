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
    public static final String SAVE_ERROR   = "no se pudo guardar: ";
    public static final String OPEN_ERROR   = "no se pudo abrir: ";
    public static final String EXPORT_ERROR = "no se pudo exportar el archivo: ";
    public static final String IMPORT_ERROR = "no se pudo importar el archivo: ";

    // Casos detalladas con varios manejos de casos para la parte III.A
    public static final String FILE_NULL           = "no se indicó ningún archivo";
    public static final String FILE_NOT_FOUND      = "el archivo no existe -> ";
    public static final String IS_DIRECTORY        = "la ruta es una carpeta, no un archivo -> ";
    public static final String BAD_EXTENSION       = "extensión incorrecta, se esperaba .dat -> ";
    public static final String NO_PARENT           = "la carpeta destino no existe -> ";
    public static final String NO_WRITE_PERMISSION = "no hay permiso de escritura -> ";
    public static final String NO_READ_PERMISSION  = "no hay permiso de lectura -> ";
    public static final String EMPTY_FILE          = "el archivo está vacío -> ";
    public static final String CORRUPT_FILE        = "el archivo está dañado o no es de EcoSafari -> ";
    public static final String NOT_A_SAFARI        = "el archivo no contiene un EcoSafari -> ";
    public static final String INCOMPATIBLE        = "versión de clases incompatible -> ";
    public static final String NOT_SERIALIZABLE    = "hay un elemento que no se puede guardar -> ";


    // Casos detalladas con varios manejos de casos para la parte III.B
    public static final String BAD_EXTENSION_TXT = "extensión incorrecta, se esperaba .txt -> ";
    public static final String BAD_FORMAT        = "formato de línea incorrecto, se esperaba 'Tipo fila, columna' -> ";
    public static final String UNKNOWN_TYPE      = "tipo de elemento desconocido -> ";
    public static final String BAD_NUMBER        = "la posición no es numérica -> ";
    public static final String OUT_OF_BOARD      = "la posición está fuera del tablero -> ";

    //Bono

    public static final String CANNOT_CREATE = "no se pudo crear el elemento -> ";


    public EcoSafariException(String message){
        super(message);
    }
}