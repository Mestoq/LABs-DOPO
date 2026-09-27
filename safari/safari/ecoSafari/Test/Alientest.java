package Test;
import domain.EcoSafari;
import domain.Alienelephant;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class Alientest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Alientest
{
    /**
     * Default constructor for test class Alientest
     */
    public Alientest(){}
        //testeamos que se mueva correctamente
        @Test 
        public void sedebemover2enuntic(){
            EcoSafari safari = new EcoSafari();
            Alienelephant alien = new Alienelephant(safari, 2, 2);
            safari.ticTac();
            int[] nuevaPos = safari.find(alien);
            assertNotNull(nuevaPos);
            assertEquals(4, nuevaPos[0]);
            assertEquals(3, nuevaPos[1]);
        }
        
        @Test 
        public void sedebereducirenergiaen20(){
            EcoSafari safari = new EcoSafari();
            Alienelephant alien = new Alienelephant(safari, 2, 2);
            int energiaInicial= alien.getEnergy();
            safari.ticTac();
            assertEquals(energiaInicial - 20, alien.getEnergy());
        }
        
        @Test
        public void desaparicioncorrecta(){
            EcoSafari safari = new EcoSafari();
            Alienelephant alien = new Alienelephant(safari, 2, 2);
            alien.changeEnergy(-80);
            assertEquals(20, alien.getEnergy());
            safari.ticTac();
            assertEquals(0, alien.getEnergy());
            assertNull(safari.find(alien));
        }
        

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}