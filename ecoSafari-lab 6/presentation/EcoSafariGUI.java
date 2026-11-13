package presentation;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EcoSafariGUI extends JFrame{  
    public static final int SIDE=20;

    public final int SIZE;
    private JButton ticTacButton;
    private JPanel  controlPanel;
    private PhotoEcoSafari photo;
    private EcoSafari theEcoSafari;
    private JMenuItem itemNew;
    private JMenuItem itemOpen;
    private JMenuItem itemSaveAs;
    private JMenuItem itemImport;
    private JMenuItem itemExportAs;
    private JMenuItem itemExit;
   
    
    public EcoSafariGUI() {
        theEcoSafari=new EcoSafari();
        SIZE=theEcoSafari.getSize();
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        setTitle("EcoSafari");
        photo=new PhotoEcoSafari(this);
        ticTacButton=new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo,BorderLayout.NORTH);
        add(ticTacButton,BorderLayout.SOUTH);
        prepareViewMenu();
        setSize(new Dimension(SIDE*SIZE+15,SIDE*SIZE+72+25));
        setResizable(false);
        photo.repaint();
    }

    /**
     * Con este creamos la barra de menú con el menú Archivo
     * con los separators como requisito
     */
    private void prepareViewMenu() {
        JMenuBar safariMenu = new JMenuBar();
        JMenu archivo = new JMenu("Archivo");

        itemNew      = new JMenuItem("Nuevo");
        itemOpen     = new JMenuItem("Abrir");
        itemSaveAs   = new JMenuItem("Salvar como");
        itemImport   = new JMenuItem("Importar");
        itemExportAs = new JMenuItem("Exportar como");
        itemExit     = new JMenuItem("Salir");

        archivo.add(itemNew);
        archivo.addSeparator();
        archivo.add(itemOpen);
        archivo.addSeparator();
        archivo.add(itemSaveAs);
        archivo.addSeparator();
        archivo.add(itemImport);
        archivo.addSeparator();
        archivo.add(itemExportAs);
        archivo.addSeparator();
        archivo.add(itemExit);
        safariMenu.add(archivo);
        setJMenuBar(safariMenu);
    }

    private void prepareActions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        ticTacButton.addActionListener(e-> ticTacButtonAction());
        prepareControllerMenu();
    }

    private void prepareControllerMenu() {
        itemNew.addActionListener(e -> optionNew());
        itemOpen.addActionListener(e -> optionOpen());
        itemSaveAs.addActionListener(e -> optionSaveAs());
        itemImport.addActionListener(e -> optionImport());
        itemExportAs.addActionListener(e -> optionExportAs());
        itemExit.addActionListener(e -> optionExit());
    }

    /** Nuevo: crea un safari nuevo y lo redibuja. */
    private void optionNew() {
        theEcoSafari = new EcoSafari();
        photo.repaint();
    }

    /** Salir: termina la aplicación (sin confirmación). */
    private void optionExit() {
        System.exit(0);
    }

    /** Abrir: elige un archivo y delega en el modelo. */
    private void optionOpen() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theEcoSafari.open(chooser.getSelectedFile());
                photo.repaint();
            } catch (EcoSafariException ex) {
                showMessage(ex.getMessage());
            }
        }
    }

    /** Salvar como: elige destino y delega en el modelo. */
    private void optionSaveAs() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theEcoSafari.saveAs(chooser.getSelectedFile());
            } catch (EcoSafariException ex) {
                showMessage(ex.getMessage());
            }
        }
    }

    /** Importar: elige un archivo de texto y delega en el modelo. */
    private void optionImport() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theEcoSafari.importAs(chooser.getSelectedFile());
                photo.repaint();
            } catch (EcoSafariException ex) {
                showMessage(ex.getMessage());
            }
        }
    }

    /** Exportar como: elige destino y delega en el modelo. */
    private void optionExportAs() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                theEcoSafari.exportAs(chooser.getSelectedFile());
            } catch (EcoSafariException ex) {
                showMessage(ex.getMessage());
            }
        }
    }

    /** Único punto de salida de mensajes al usuario. */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "EcoSafari", JOptionPane.INFORMATION_MESSAGE);
    }

    private void ticTacButtonAction() {
        theEcoSafari.ticTac();
        photo.repaint();
    }

    public EcoSafari gettheEcoSafari(){
        return theEcoSafari;
    }
    
    public static void main(String[] args) {
        EcoSafariGUI cg=new EcoSafariGUI();
        cg.setVisible(true);
    }  


    class PhotoEcoSafari extends JPanel{
        private EcoSafariGUI gui;
    
        public PhotoEcoSafari(EcoSafariGUI gui) {
            this.gui=gui;
            setBackground(Color.white);
            setPreferredSize(new Dimension(gui.SIDE*gui.SIZE+10, gui.SIDE*gui.SIZE+10));         
        }
    
    
        public void paintComponent(Graphics g){
            EcoSafari theEcoSafari=gui.gettheEcoSafari();
            super.paintComponent(g);
             
            for (int c=0;c<=theEcoSafari.getSize();c++){
                g.drawLine(c*gui.SIDE,0,c*gui.SIDE,theEcoSafari.getSize()*gui.SIDE);
            }
            for (int f=0;f<=theEcoSafari.getSize();f++){
                g.drawLine(0,f*gui.SIDE,theEcoSafari.getSize()*gui.SIDE,f*gui.SIDE);
            }       
            for (int f=0;f<theEcoSafari.getSize();f++){
                for(int c=0;c<theEcoSafari.getSize();c++){
                    if (theEcoSafari.get(f,c)!=null){
                        g.setColor(theEcoSafari.get(f,c).getColor());
                        if (theEcoSafari.get(f,c).shape()==Entity.SQUARE){                  
                            g.fillRoundRect(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2,2,2);   
                        }else {
                            g.fillOval(gui.SIDE*c+1,gui.SIDE*f+1,gui.SIDE-2,gui.SIDE-2);
                        }
                        if (theEcoSafari.get(f,c).isOrganism()){
                            g.setColor(Color.red);
                            if (((Organism)theEcoSafari.get(f,c)).getEnergy()>=50){
                                g.drawString("+",gui.SIDE*c+6,gui.SIDE*f+15);
                            } else {
                                g.drawString("~",gui.SIDE*c+6,gui.SIDE*f+17);
                            }
                        }    
                    }
                }
            }
        }
    }
}