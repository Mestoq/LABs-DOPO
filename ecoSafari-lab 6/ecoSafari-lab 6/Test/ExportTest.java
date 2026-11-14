package Test;

import domain.*;
import java.io.*;
import java.util.*;
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
public class ExportTest
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
        file = File.createTempFile("safari", ".txt");
        return file;
    }
    private List<String> readLines(File f) throws IOException{
        List <String> lines = new ArrayList<String>();
        try (BufferedReader in = new BufferedReader(new FileReader(f))){
            String line;
            while((line = in.readLine()) != null){
                lines.add(line);
            }
        }
        return lines;
    }
    //mira que cree el txt
    @Test
    public void shouldcreatetextfile() throws Exception{
        EcoSafari safari = new EcoSafari();
        new Elephant(safari, 5, 5);
        File f = tempFile();
        f.delete();
        safari.exportAs(f);
        assertTrue(f.exists());
        assertTrue(f.length()>0);
    }
    // mira que se escribe correctamente cada item
    @Test
    public void shouldwriteonelinewithtypeandposition() throws Exception{
        EcoSafari safari = new EcoSafari();
        new Elephant(safari,5 ,5);
        new Elephant(safari,10 ,10);
        new Bush(safari,18 ,18);
        new Storm(safari,15 ,15);
        new Alienelephant(safari,2 ,2);
        new Robot(safari,24 ,24);
        File f = tempFile();
        safari.exportAs(f);
        List<String> lines = readLines(f);
        assertTrue(lines.contains("Elephant 5, 5"));
        assertTrue(lines.contains("Elephant 10, 10"));
        assertTrue(lines.contains("Bush 18, 18"));
        assertTrue(lines.contains("Storm 15, 15"));
        assertTrue(lines.contains("Alienelephant 2, 2"));
        assertTrue(lines.contains("Robot 24, 24"));
        
    }

    //salta la excepcion cuando el archivo abierto es null
    @Test
    public void shouldfailwithnull() throws Exception{
        EcoSafari safari = new EcoSafari();
        assertThrows(EcoSafariException.class, () -> safari.exportAs(null));
    }
    
    //salta la excepcion cuando no se puede exportar
    @Test
    public void shouldfailwhenthefilecantbewritten() throws Exception{
        EcoSafari safari = new EcoSafari();
        File invalid = new File("carpetaquenoexiste/safari.txt");
        EcoSafariException ex = assertThrows(EcoSafariException.class, () -> safari.exportAs(invalid));
        assertTrue(ex.getMessage().contains("safari.txt"));
    }
}