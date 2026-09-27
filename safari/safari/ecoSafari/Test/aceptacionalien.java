package Test;

import domain.*;
import presentation.*;

/**
 * Write a description of class aceptacionalien here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class aceptacionalien
{
    public static void main(String[] args){
        EcoSafariGUI gui = new EcoSafariGUI();
        EcoSafari safari = gui.gettheEcoSafari();
        Alienelephant alien = new Alienelephant(safari, 2 , 2);
        alien.changeEnergy(-80);
        gui.setVisible(true);
        safari.ticTac();
    }
}