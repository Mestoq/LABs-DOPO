package Test;

import domain.*;
import presentation.*;
import javax.swing.*;
import java.io.*;

/**
 * Prueba de aceptacion 3: iniciar la aplicacion y exportar como safari0ne.txt.
 * Muestra la aplicacion y, a su lado, el contenido del archivo exportado.
 */
public class ExportAcceptance
{
    public static void main(String[] args) throws Exception{
        // 1. se inicia la aplicacion
        EcoSafariGUI gui = new EcoSafariGUI();
        gui.gettheEcoSafari().import_(new File("safariInicial.txt"));
        gui.setTitle("EcoSafari - estado exportado");
        gui.setLocation(40, 40);
        gui.setVisible(true);

        // 2. Archivo > Exportar como > safari0ne.txt
        File file = new File("safari0ne.txt");
        gui.gettheEcoSafari().exportAs(file);

        // 3. se muestra el contenido del archivo
        showFile(file, "safari0ne.txt", 40 + gui.getWidth() + 10, 40);
        System.out.println("Archivo: " + file.getAbsolutePath());
        System.out.println("Existe: " + file.exists() + " - Tamano: " + file.length() + " bytes");
    }

    /** Abre una ventana con el contenido del archivo de texto. */
    private static void showFile(File file, String title, int x, int y) throws IOException{
        StringBuilder text = new StringBuilder();
        int lines = 0;
        try (BufferedReader in = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = in.readLine()) != null){
                text.append(line).append('\n');
                lines++;
            }
        }
        JTextArea area = new JTextArea(text.toString(), 28, 24);
        area.setEditable(false);
        area.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 13));
        JFrame window = new JFrame(title + " (" + lines + " lineas)");
        window.add(new JScrollPane(area));
        window.pack();
        window.setLocation(x, y);
        window.setVisible(true);
    }
}