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
   
    
    public EcoSafariGUI() {
        theEcoSafari=new EcoSafari();
        SIZE=theEcoSafari.getSize();
        prepareElements();
        prepareActions();
        prepareView();
    }


    private void prepareView() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(pantalla.width / 2, pantalla.height / 2);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(5, 5));

		JMenuBar safariMenu = prepareViewMenu();
		prepareControllerMenu(safariMenu);

    }
    private void prepareElements() {
        setTitle("EcoSafari");
        photo=new PhotoEcoSafari(this);
        ticTacButton=new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo,BorderLayout.NORTH);
        add(ticTacButton,BorderLayout.SOUTH);
        setSize(new Dimension(SIDE*SIZE+15,SIDE*SIZE+72)); 
        setResizable(false);
        photo.repaint();
    }

    private void prepareActions(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        ticTacButton.addActionListener(e-> ticTacButtonAction());
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
    private void openAction() {
		JFileChooser fileChooser = new JFileChooser();
		int seleccion = fileChooser.showOpenDialog(this);

		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File archivoSeleccionado = fileChooser.getSelectedFile();
			JOptionPane.showMessageDialog(
					this,
					"La funcion de abrir está en construcción.\nArchivo seleccionado: " + archivoSeleccionado.getName(),
					"En construcción",
					JOptionPane.INFORMATION_MESSAGE
			);
		}
	}

    private void actionSave() {
		JFileChooser fileChooser = new JFileChooser();
		int seleccion = fileChooser.showSaveDialog(this);

		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File archivoSeleccionado = fileChooser.getSelectedFile();
			JOptionPane.showMessageDialog(
					this,
					"La funcionalidad de GUARDAR está en construcción.\nArchivo seleccionado: " + archivoSeleccionado.getName(),
					"En construcción",
					JOptionPane.INFORMATION_MESSAGE
			);
		}
    }
    private void prepareControllerMenu(JMenuBar safariMenu) {
		JMenu archivo = safariMenu.getMenu(0);
		
		archivo.getItem(0).addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
        		newGame();
    		}
		});

		archivo.getItem(1).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openAction();
			}
		});

		archivo.getItem(2).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionSave();
			}
		});

		archivo.getItem(3).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmExit();
			}
		});
    }
    private JMenuBar prepareViewMenu() {
		JMenuBar safariMenu = new JMenuBar();

		JMenu archivo = new JMenu("Menu");
		archivo.add(new JMenuItem("Nuevo"));
		archivo.add(new JMenuItem("Abrir"));
		archivo.add(new JMenuItem("Salvar como"));
		archivo.add(new JMenuItem("Importar"));
		archivo.add(new JMenuItem("Exportar como"));
		archivo.add(new JMenuItem("Salir"));

		safariMenu.add(archivo);
		setJMenuBar(safariMenu);

		return safariMenu;
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