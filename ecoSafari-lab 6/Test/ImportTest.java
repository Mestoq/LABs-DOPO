package Test;

import domain.*;
import java.io.*;
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
public class ImportTest
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
    private File textFile (String content) throws IOException{
        file = File.createTempFile("safari", ".txt");
        try (FileWriter out = new FileWriter(file)){
            out.write(content);
        }
        return file;
    }
    //prueba de que importe un txt
    @Test
    public void shouldimporttextfile() throws Exception{
        EcoSafari safari = new EcoSafari();
        safari.import_(textFile("Elephant 10, 20\nBush 15, 15\nStorm 5, 5\n"));
        assertTrue(safari.get(10, 20) instanceof Elephant);
        assertTrue(safari.get(15, 15)instanceof Bush);
        assertTrue(safari.get(5, 5)instanceof Storm);
    }
    // mira que importa todo tipo de item
    @Test
    public void ShouldImportEveryTypeOfItem() throws Exception{
        EcoSafari safari = new EcoSafari();
        safari.import_(textFile(
            "Elephant 0, 0\nAlienelephant 0, 1\nBush 0, 2\nStorm 0, 3\nRobot 0, 4\n"
          + "Zebra 0, 5\nLion 0, 6\nGrass 0, 7\nLand 0, 8\n"));
        assertEquals(Elephant.class, safari.get(0, 0).getClass());
        assertEquals(Alienelephant.class, safari.get(0, 1).getClass());
        assertEquals(Bush.class, safari.get(0, 2).getClass());
        assertEquals(Storm.class, safari.get(0, 3).getClass());
        assertEquals(Robot.class, safari.get(0, 4).getClass());
        assertEquals(Zebra.class, safari.get(0, 5).getClass());
        assertEquals(Lion.class, safari.get(0, 6).getClass());
        assertEquals(Grass.class, safari.get(0, 7).getClass());
        assertEquals(Land.class, safari.get(0, 8).getClass());
    }

    //salta la excepcion cuando se importa un tipo no validop
    @Test
    public void shouldfailwithunknowntype() throws Exception{
        EcoSafari safari = new EcoSafari();
        File f = textFile("Dragon 1, 1\n");
        assertThrows(EcoSafariException.class, () -> safari.import_(f));
    }
    
    //salta la excepcion cuando no se puede importar
    @Test
    public void shouldfailwhenthefilecantberead() throws Exception{
        EcoSafari safari = new EcoSafari();
        EcoSafariException ex = assertThrows(EcoSafariException.class, () -> safari.import_(new File("no_existe.txt")));
        assertTrue(ex.getMessage().contains("no_existe.txt"));
    }
}