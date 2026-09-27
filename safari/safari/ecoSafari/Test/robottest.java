package Test;

import domain.*;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class robottest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class robottest
{
    /**
     * Default constructor for test class robottest
     */
    public robottest(){}
    //el robot se crea correctamente en safari
    @Test
    public void contruccion(){
            EcoSafari safari = new EcoSafari();
            Robot robot = new Robot(safari, 4, 4);
            assertEquals(100, robot.getEnergy());
            assertEquals(robot, safari.get(4, 4));
            assertEquals(Color.RED, robot.getColor());
            assertEquals(Entity.SQUARE, robot.shape());
    }
    //se consume energia
    @Test
    public void consumorobot(){
            EcoSafari safari = new EcoSafari();
            Robot robot = new Robot(safari, 24, 24);
            robot.tic();
            assertEquals(95, robot.getEnergy());
    }
    //el robot se mueve donde el elefante
    @Test
    public void robotsemueve(){
            EcoSafari safari = new EcoSafari();
            Robot robot = new Robot(safari, 10, 10);
            robot.tic();
            assertEquals(robot, safari.get(9, 9));
    }
}