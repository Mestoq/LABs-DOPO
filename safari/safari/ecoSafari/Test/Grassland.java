package Test;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.EcoSafari;
import domain.Elephant;

/**
 * The test class Grassland.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Grassland
{
    /**
     * Default constructor for test class Grassland
     */
    public Grassland()
    {
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
    
    @Test
    public void AnElephantJustCreatedShouldStartOnTheDesignatedArea() {
        EcoSafari safari = new EcoSafari();
        Elephant dumbo = new Elephant(safari, 5, 5);
        assertEquals(dumbo, safari.get(5, 5));
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