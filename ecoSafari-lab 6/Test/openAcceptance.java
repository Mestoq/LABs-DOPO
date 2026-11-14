package Test;

import domain.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Write a description of class openAcceptance here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class openAcceptance
{
    // prueba de aceptacion de iniciar el safari avanzar dos tic.tac, gaurdar como safariOne.dat y crear un nuevo safari y abrir el archivo
    private final File file = new File("safariOne.dat");

    @AfterEach
    public void deleteFile()
    {
        if(file !=null){
            file.delete();
        }
    }

    @Test
    public void shouldrecoverthesameentities() throws Exception{
        EcoSafari safari = new EcoSafari();
        safari.ticTac();
        safari.ticTac();
        safari.saveAs(file);
        assertTrue(file.exists());
        System.out.println("safariOne.dat ocupa " + file.length() +"bytes");
        EcoSafari fresh = new EcoSafari();
        EcoSafari opened = fresh.open(file);
        for (int r = 0; r< safari.getSize(); r++){
            for (int c=0; c<safari.getSize(); c++){
                Entity a = safari.get(r,c);
                Entity b = opened.get(r,c);
                assertEquals(a== null ? null : a.getClass(), b==null ? null : b.getClass());
            }
        }
    }
}