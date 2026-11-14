package Test;

import domain.*;
import presentation.*;
import javax.swing.*;
import java.io.File;

/**
 * Prueba de aceptacion de Abrir: iniciar la aplicacion, crear un nuevo estado
 * y abrir el archivo safariOne.dat.
 */
public class openAcceptance
{
    public static void main(String[] args) throws Exception{
        File file = new File("safariOne.dat");

        // se guarda safariOne.dat
        EcoSafari saved = new EcoSafari();
        new Elephant(saved, 5, 5);
        new Alienelephant(saved, 2, 2);
        new Bush(saved, 18, 18);
        new Storm(saved, 15, 15);
        saved.ticTac();
        saved.ticTac();
        saved.saveAs(file);

        // se inicia de nuevo ecosafari
        EcoSafariGUI gui = new EcoSafariGUI();
        gui.settheEcoSafari(new EcoSafari());
        gui.setTitle("nuevo ecosafari");
        gui.setVisible(true);
        JOptionPane.showMessageDialog(gui, " Aceptar para abrir safariOne.dat");

        // abre safariOne.dat
        gui.settheEcoSafari(gui.gettheEcoSafari().open(file));
        gui.setTitle("safariOne.dat ABIERTO");
    }
}