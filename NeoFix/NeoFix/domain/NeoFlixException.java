package domain;


/**
 * Write a description of class NeoFlixException here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NeoFlixException extends Exception{
    public static final String VALUE_UNKNOWN="no se conoce el valor";
    public static final String DATA_ERROR="error en los datos ";
    public static final String CONTENT_EMPTY="contenido vacio ";
    public static final String TO_BE_IMPLEMENTED ="pendiente de implementar";
    public static final String SERIES_ALREADY_EXIST="la serie ya existe";
    public static final String NOT_VALID_FORMAT="formato numerico no valido";
    public static final String INCONSISTENT_DATA="datos inconsistentes";
    public static final String NOT_NULL_VALUES="el nombre no puede estar vacio";
    public static final String EPISODE_NOT_FOUND="el episodio no existe";
    
    public NeoFlixException(String message){
        super(message);
    }
}