package presentation;


import javax.swing.*;

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
		
	}

	public void main(String[] argumentos){
		HungrySnakeGUI pantalla = new HungrySnakeGUI();
		pantalla.setVisible(true); 
	}
}