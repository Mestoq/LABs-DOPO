package presentation;


import javax.swing.*;
import java.awt.event.*;

public class HungrySnakeGUI extends JFrame{

	public HungrySnakeGUI(){
		setTitle("HungrySnake");
		prepareView();
		prepareController();
	}
	
	private void prepareView(){
		java.awt.Dimension pantalla =java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(pantalla.width / 2, pantalla.height / 2);
		setLocationRelativeTo(null);
	}

	private void prepareController(){
        addWindowListener(new WindowAdapter() { //Se usa WindowAdapter para ahorrarnos escribir los otros 6 metodos de la interfaz del listener
            @Override
            public void windowClosing(WindowEvent e) {
                // Mostramos un panel con un  Sí y No
                int respuesta = JOptionPane.showConfirmDialog(
                        HungrySnakeGUI.this, 
                        "¿Seguro que quiere salir de la aplicación?", 
                        "Confirmar salida", 
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );


                if(respuesta == JOptionPane.YES_OPTION){
                    dispose(); 
                    System.exit(0); 
                }else{
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
				}
            }
        });
    }

	public void main(String[] argumentos){
		HungrySnakeGUI pantalla = new HungrySnakeGUI();
		pantalla.setVisible(true); 
		pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}
}
