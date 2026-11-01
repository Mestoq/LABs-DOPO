package presentation;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

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


		JMenuBar snakeMenu = PrepareViewMenu();
		prepareControllerMenu(snakeMenu);
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

	private void openAction() {
		JFileChooser fileChooser = new JFileChooser();
		int seleccion = fileChooser.showOpenDialog(this);

		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File archivoSeleccionado = fileChooser.getSelectedFile();
			JOptionPane.showMessageDialog(
					this,
					"La funcionalidad de ABRIR está en construcción.\nArchivo seleccionado: " + archivoSeleccionado.getName(),
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
		JMenuItem open = archivo.getItem(1);
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openAction();
			}
		});

		JMenuItem save = archivo.getItem(2);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionSave();
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