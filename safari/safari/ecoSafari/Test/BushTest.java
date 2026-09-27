package Test;

import domain.EcoSafari;
import domain.Bush;
import domain.Elephant;
import domain.Entity;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BushTest {

    // El elefante se coloca en la esquina (24,24): move(1,1) queda fuera de la
    // cuadrícula, así que el elefante NO se mueve y permanece fijo al sur durante todos los test

    @Test
    public void AYoungBushDoesNotDisappearBeforeTwoTicsAlthoughThereIsAnElephantNeighbor(){
        EcoSafari safari = new EcoSafari();
        Bush mopane = new Bush(safari, 23, 24);
        Elephant tantor = new Elephant(safari, 24, 24); 

        safari.ticTac(); 

        assertTrue(safari.get(23, 24) instanceof Bush,"Antes de los 2 tics el elfante no deberia poder comerse el arbusto");
    }

    @Test
    public void aBushDisappearsIfItRemainsNeighborToAnElephantAfterTheGracePeriod(){
        EcoSafari safari = new EcoSafari();
        Bush mopane = new Bush(safari, 23, 24);
        Elephant tantor = new Elephant(safari, 24, 24); 

        safari.ticTac(); 
        safari.ticTac(); 
        safari.ticTac(); 

        assertNull(safari.get(23, 24),
            "Ya crecio lo suficiente para ser consumido");
    }

    @Test
    public void   theBushGetsYellowWhenHeIsOld(){
        EcoSafari safari = new EcoSafari();
        Bush acacia = new Bush(safari, 18, 18);

        assertEquals(Color.GREEN, acacia.getColor(), "Aun deberia estar joven");

        safari.ticTac(); 
        safari.ticTac(); 
        safari.ticTac();
        safari.ticTac(); 

        assertEquals(Color.YELLOW, acacia.getColor(), "Ahora si esta viejito");
    }
}
