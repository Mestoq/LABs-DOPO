package Test;

import domain.*;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SaveTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SaveTest
{
    private File file;
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void deleteFile()
    {
        if(file !=null){
            file.delete();
        }
    }
    private File tempFile() throws IOException{
        file = File.createTempFile("safari", ".dat");
        return file;
    }
    //crea el archivo con el ecosafari
    @Test
    public void shouldcreateTheFileWhenSaving() throws Exception{
        EcoSafari safari = new EcoSafari();
        File f = tempFile();
        f.delete();
        assertFalse(f.exists());
        safari.saveAs(f);
        assertTrue(f.exists(), "archivo creado correctamente");
        assertTrue(f.length()>0, "archivo no debe estar vacio");
    }
    //comprueba que se guarde todo el ecosafari tal y como se estaba
    @Test
    public void shouldsaveTheState() throws Exception{
        EcoSafari safari = new EcoSafari();
        safari.ticTac();
        safari.ticTac();
        File f = tempFile();
        safari.saveAs(f);
        assertTrue(f.length()>0);
    }
    //salta la excepcion cuando el archivo guardado es null
    @Test
    public void shouldfailwithnullfile() throws Exception{
        EcoSafari safari = new EcoSafari();
        assertThrows(EcoSafariException.class, () -> safari.saveAs(null));
    }
    
    //guardar no debe afectar el table actual
    @Test
    public void shoulddoesntchange() throws Exception{
        EcoSafari safari = new EcoSafari();
        Entity before = safari.get(5,5);
        safari.saveAs(tempFile());
        assertSame(before, safari.get(5,5));
    }
}