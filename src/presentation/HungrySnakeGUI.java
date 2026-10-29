package presentation;

import javax.swing.*;
import java.awt.event.*;

public class HungrySnakeGUI extends JFrame {

	public HungrySnakeGUI() {
		setTitle("HungrySnake");
		prepareView();
		prepareController();
	}

	private void prepareView() {
		java.awt.Dimension pantalla = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(pantalla.width / 2, pantalla.height / 2);
		setLocationRelativeTo(null);


		JMenuBar sankeMenu = PrepareViewMenu();
		prepareControllerMenu(sankeMenu);
	}

	private void prepareController() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmExit();
			}
		});
	}

	private void confirmExit() {
		int respuesta = JOptionPane.showConfirmDialog(
				HungrySnakeGUI.this,
				"¿Seguro que quiere salir de la aplicación?",
				"Confirmar salida",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
		);

		if (respuesta == JOptionPane.YES_OPTION) {
			dispose();
			System.exit(0);
		} else {
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}


	private void prepareControllerMenu(JMenuBar sankeMenu) {

		JMenu archivo = sankeMenu.getMenu(0);

		// Extraemos el cuarto elemento (Salir) en la posición 3
		JMenuItem exit = archivo.getItem(3);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmExit();
			}
		});
	}

	private JMenuBar PrepareViewMenu() {

		JMenuBar snakeMenu = new JMenuBar();
		JMenu archivo = new JMenu("Menu");

		JMenuItem newMatch = new JMenuItem("Nuevo");
		JMenuItem open = new JMenuItem("Abrir");
		JMenuItem save = new JMenuItem("Guardar");
		JMenuItem exit = new JMenuItem("Salir");

		archivo.add(newMatch);
		archivo.add(open);
		archivo.add(save);
		archivo.add(exit);
		snakeMenu.add(archivo);
		setJMenuBar(snakeMenu);

		return snakeMenu;
	}

	public static void main(String[] argumentos) {
		HungrySnakeGUI pantalla = new HungrySnakeGUI();
		pantalla.setVisible(true);
	}
}
