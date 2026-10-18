package test;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import presentation.NeoFlixGUI;

/**
 * Pruebas de ACEPTACION (Parte II.B). Manejan la GUI real de NeoFlix:
 * escriben en los campos, pulsan los botones y leen lo que se ve en pantalla.

 */
public class NeoFlixAcceptanceTest{

    private static final String INITIAL_LIST =
        "6 elementos\n" +
        ">The Hidden Village\n" +
        ">The First Mission\n" +
        ">The Rival\n" +
        ">The Tournament\n" +
        ">The Final Battle\n" +
        ">The Neo Ninja: 2020\n" +
        "\tThe Hidden Village\n" +
        "\tThe First Mission\n" +
        "\tThe Rival\n" +
        "\tThe Tournament\n" +
        "\tThe Final Battle\n";

    private NeoFlixGUI gui;

    @Before
    public void setUp() throws Exception{
        assumeFalse("Sin entorno grafico", GraphicsEnvironment.isHeadless());
        Constructor<NeoFlixGUI> c = NeoFlixGUI.class.getDeclaredConstructor();
        c.setAccessible(true);
        gui = c.newInstance();               
    }

    @After
    public void tearDown(){
        if (gui != null) gui.dispose();
    }

 

    private Object field(String name) throws Exception{
        Field f = NeoFlixGUI.class.getDeclaredField(name);
        f.setAccessible(true);
        return f.get(gui);
    }

    private void type(String fieldName, String text) throws Exception{
        Object o = field(fieldName);
        if (o instanceof JTextField) ((JTextField) o).setText(text);
        else ((JTextArea) o).setText(text);
    }

    private void click(String buttonName) throws Exception{
        ((JButton) field(buttonName)).doClick();
    }


    private void addEpisode(String title, String attempts, String completed, String votes, String sumVotes) throws Exception{
        type("title", title);
        type("atttemps", attempts);          //
        type("completed", completed);
        type("votes", votes);
        type("sumVotes", sumVotes);
        click("buttonAdd");
        click("buttonRestartAdd");
    }

    /** Llena el formulario como una serie y pulsa Adicionar. */
    private void addSeries(String title, String year, String episodes) throws Exception{
        type("title", title);
        type("year", year);
        type("episodes", episodes);
        click("buttonAdd");
        click("buttonRestartAdd");
    }

    /** Pulsa Listar y devuelve lo que se ve en pantalla. */
    private String list() throws Exception{
        click("buttonList");
        return ((JTextArea) field("textDetails")).getText();
    }


    @Test
    public void AC1_shouldListTheInitialContents() throws Exception{
        assertEquals(INITIAL_LIST, list());
    }

    @Test
    public void AC2_shouldAddANewEpisode() throws Exception{
        addEpisode("Pilot", "10", "5", "2", "15");
        String screen = list();
        assertTrue(screen.startsWith("7 elementos\n"));
        assertTrue(screen.endsWith(">Pilot\n"));
    }

    @Test
    public void AC3_shouldAddANewSeries() throws Exception{
        addSeries("The Second Ninja", "2024", "The Rival\nThe Tournament");
        String screen = list();
        assertTrue(screen.startsWith("7 elementos\n"));
        assertTrue(screen.endsWith(">The Second Ninja: 2024\n\tThe Rival\n\tThe Tournament\n"));
    }

    @Test
    public void AC4_shouldShowDuplicatesWithoutWarning() throws Exception{
        addEpisode("The Hidden Village", "199", "120", "50", "430");
        addSeries("The Neo Ninja", "2020", "The Hidden Village\nThe First Mission");
        String screen = list();
        assertTrue(screen.startsWith("8 elementos\n"));
        assertEquals(2, screen.split(">The Neo Ninja", -1).length - 1);
    }
    

}