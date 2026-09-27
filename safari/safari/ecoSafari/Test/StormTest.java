package Test;

import domain.EcoSafari;
import domain.Storm;
import domain.Bush;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StormTest {

    @Test
    public void AStormMovesABoxDiagonalNortheast(){
        EcoSafari safari = new EcoSafari();
        Storm thor = new Storm(safari, 12, 12);

        safari.ticTac();

        assertTrue(safari.get(11, 13) instanceof Storm,
            "La tormenta debe haberse movido al noreste (fila-1, columna+1)");
        assertEquals(Color.GRAY, safari.get(12, 12).getColor(),
            "La posición vieja ahora es un área afectada, debe quedar gris");
    }

    @Test
    public void AStormMovesInCircularPatternAsItLeavesTheEdge(){
        EcoSafari safari = new EcoSafari();
        Storm thor = new Storm(safari, 0, 24); 

        safari.ticTac();

        // fila 0-1 -> circular -> última fila (size-1); columna 24+1 -> circular -> 0
        assertTrue(safari.get(safari.getSize() - 1, 0) instanceof Storm,
            "Al salir del borde, la tormenta debe reaparecer por el lado opuesto");
    }

    @Test
    public void aStormDestroysWhatFindsInItsNewCenter(){
        EcoSafari safari = new EcoSafari();
        Storm thor = new Storm(safari, 12, 12);
        Bush acacia = new Bush(safari, 11, 13);

        safari.ticTac();

        Object inTheCenter = safari.get(11, 13);
        assertTrue(inTheCenter instanceof Storm,
            "La tormenta debe ocupar tener nuevo centro");
        assertNotEquals(acacia, inTheCenter, "El arbusto debe haber desaparecer");
    }

    @Test
    public void aStormLeavesGrayColoringInAfectedAreas(){
        EcoSafari safari = new EcoSafari();
        Storm thor = new Storm(safari, 12, 12);

        safari.ticTac();


        assertNotNull(safari.get(10, 13), "Debe haber color gris en el área afectada");
        assertEquals(Color.GRAY, safari.get(10, 13).getColor());
    }

    @Test
    public void theGrayZoneShouldChangeAfterTheNextTic(){
        EcoSafari safari = new EcoSafari();
        Storm thor = new Storm(safari, 12, 12);

        safari.ticTac(); 
        safari.ticTac(); 

        assertNull(safari.get(10, 12), "La marca gris debe durar solo un ciclo");
    }
}
