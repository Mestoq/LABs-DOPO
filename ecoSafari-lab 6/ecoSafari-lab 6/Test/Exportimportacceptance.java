package Test;

import domain.*;
import presentation.*;
import java.io.File;
/**
 * Write a description of class Exportimportacceptance here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Exportimportacceptance
{
    // instance variables - replace the example below with your own
    private static final int TICS=3;

    public static void main(String[] args) throws Exception{
        File file = new File("safari0Other.txt");
        // ise inicia la aplicacion y exporto
        EcoSafariGUI first = new EcoSafariGUI();
        first.gettheEcoSafari().import_(new File("safariInicial.txt"));
        for(int i = 0; i<TICS; i++){
            first.gettheEcoSafari().ticTac();
        }
        first.gettheEcoSafari().exportAs(file);
        first.setTitle("original(exportada)");
        first.setLocation(20, 40);
        first.setVisible(true);
        
        //sale y entra
        EcoSafariGUI second = new EcoSafariGUI();
        int before = count(second.gettheEcoSafari());
        //importo el txt
        second.gettheEcoSafari().import_(file);
        second.setTitle("nuevo con importado");
        second.setLocation(20 + first.getWidth() + 10, 40);
        second.setVisible(true);
        System.out.println("antes de importar" + before);
        System.out.println("despues de importar" + count(second.gettheEcoSafari()));
    }
    
    private static int count(EcoSafari safari){
        int n=0;
        for(int r=0; r<safari.getSize(); r++){
            for(int c=0; c<safari.getSize(); c++){
                if(safari.get(r, c) !=null){
                    n++;
                }
            }
        }
        return n;
    }
}