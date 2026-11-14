package Test;

import domain.*;
import presentation.*;
import java.io.*;
import javax.swing.*;
public class ImportAcceptance
{
    // instance variables - replace the example below with your own
    private static final int TICS=3;

    public static void main(String[] args) throws Exception{
        // se excribe el txt
        File file = new File("safari0Other.txt");
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))){
            String[] lines ={
                "Elephant 10, 20",
                "Bush 15, 15",
                "Storm 5, 5",
                "Zebra 3, 3",
                "Lion 8, 20",
                "Grass 20, 2",
                "Land 21, 2"
            };
            for (String line : lines){
                out.write(line);
                out.newLine();
            }
        }
        //inicia nuevamente e importa safari0Other.txt
        EcoSafariGUI gui = new EcoSafariGUI();
        gui.gettheEcoSafari().import_(file);
        gui.setTitle("despues de importado");
        gui.setLocation(40, 40);
        gui.setVisible(true);
        
        //muestra el contenido
        showFile(file, 40 + gui.getWidth() +10, 40);
    }
    //abre la ventana con el contenido del archivo en texto
    private static void showFile(File file, int x, int y) throws IOException{
        StringBuilder text = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = in.readLine()) != null){
                text.append(line).append('\n');
            }
        }
        JTextArea area = new JTextArea(text.toString(), 28, 24);
        area.setEditable(false);
        area.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 13));
        JFrame window = new JFrame(file.getName() + " (archivo importado)");
        window.add(new JScrollPane(area));
        window.pack();
        window.setLocation(x, y);
        window.setVisible(true);
    }
}