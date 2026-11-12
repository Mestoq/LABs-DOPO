package Test;

import domain.*;
import presentation.*;
/**
 * Write a description of class aceptacionrobot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
// se ve graficamente como el robot va a suvir para acercarse al elefante
public class robotAcceptance
{
    public static void main(String[] args){
        EcoSafariGUI gui = new EcoSafariGUI();
        EcoSafari safari = gui.gettheEcoSafari();
        Robot robot = new Robot(safari, 10, 5);
        gui.setVisible(true);
        safari.ticTac();
    }
}