package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class HungrySnakeGUI extends JFrame {

	// ---------- Constantes ----------
	private static final int BOARD_SIZE = 15;

	// ---------- Atributos de la vista ----------
	private JPanel infoPanel;
	private JLabel scoreLabel;
	private JLabel lengthLabel;
	private JLabel timeLabel;

	private JPanel boardPanel;
	private JPanel[][] cells;

	private JPanel statusPanel;
	private JLabel statusLabel;


	private Color boardColor = new Color(53, 53, 53);
	private Color snakeColor = new Color(60, 170, 70);
	private Color foodColor = new Color(200, 40, 40);

	public HungrySnakeGUI() {
		setTitle("HungrySnake");
		prepareView();
		prepareController();
	}


	private void prepareView() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(pantalla.width / 2, pantalla.height / 2);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(5, 5));

		JMenuBar snakeMenu = prepareViewMenu();
		prepareControllerMenu(snakeMenu);

		prepareViewInfo();
		prepareViewBoard();
		prepareViewStatus();

		refresh();
	}

	private void prepareViewInfo() {
		infoPanel = new JPanel(new GridLayout(1, 3, 10, 0));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		scoreLabel = new JLabel("Puntaje: 0", SwingConstants.CENTER);
		lengthLabel = new JLabel("Longitud: 3", SwingConstants.CENTER);
		timeLabel = new JLabel("Tiempo: 00:00", SwingConstants.CENTER);

		infoPanel.add(scoreLabel);
		infoPanel.add(lengthLabel);
		infoPanel.add(timeLabel);

		add(infoPanel, BorderLayout.NORTH);
	}

	private void prepareViewBoard() {
		boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE, 1, 1));
		boardPanel.setBackground(Color.DARK_GRAY);
		boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));

		cells = new JPanel[BOARD_SIZE][BOARD_SIZE];
		for (int fila = 0; fila < BOARD_SIZE; fila++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				cells[fila][col] = new JPanel();
				boardPanel.add(cells[fila][col]);
			}
		}

		add(boardPanel, BorderLayout.CENTER);
	}

	private void prepareViewStatus() {
		statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusPanel.setBorder(BorderFactory.createEtchedBorder());
		statusLabel = new JLabel("Listo. Seleccione Menu > Nuevo para empezar.");
		statusPanel.add(statusLabel);

		add(statusPanel, BorderLayout.SOUTH);
	}

	/**
	 * Actualiza la vista del tablero con el tablero inicial por omisión,
	 * usando los colores configurados actualmente.
	 */
	private void refresh() {
		for (int fila = 0; fila < BOARD_SIZE; fila++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				cells[fila][col].setBackground(boardColor);
			}
		}

		int centro = BOARD_SIZE / 2;

		// Serpiente inicial: cuerpo con snakeColor, cabeza un tono más oscuro
		cells[centro][centro - 2].setBackground(snakeColor);
		cells[centro][centro - 1].setBackground(snakeColor);
		cells[centro][centro].setBackground(snakeColor.darker());

		// Comida inicial
		cells[centro][centro + 4].setBackground(foodColor);

		boardPanel.revalidate();
		boardPanel.repaint();
	}


	private void prepareController() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

	/**
	 * Abre el selector de color y devuelve el color elegido,
	 * o el color actual si el usuario cancela.
	 */
	private Color chooseColor(String titulo, Color actual) {
		Color elegido = JColorChooser.showDialog(this, titulo, actual);
		return (elegido != null) ? elegido : actual;
	}

	private void changeSnakeColorAction() {
		Color nuevo = chooseColor("Color de la serpiente", snakeColor);
		if (!nuevo.equals(snakeColor)) {
			snakeColor = nuevo;
			refresh();
			statusLabel.setText("Color de la serpiente actualizado.");
		} else {
			statusLabel.setText("Color de la serpiente sin cambios.");
		}
	}

	private void changeFoodColorAction() {
		Color nuevo = chooseColor("Color de la comida", foodColor);
		if (!nuevo.equals(foodColor)) {
			foodColor = nuevo;
			refresh();
			statusLabel.setText("Color de la comida actualizado.");
		} else {
			statusLabel.setText("Color de la comida sin cambios.");
		}
	}

	private void changeBoardColorAction() {
		Color nuevo = chooseColor("Color del tablero", boardColor);
		if (!nuevo.equals(boardColor)) {
			boardColor = nuevo;
			refresh();
			statusLabel.setText("Color del tablero actualizado.");
		} else {
			statusLabel.setText("Color del tablero sin cambios.");
		}
	}


	private void prepareControllerMenu(JMenuBar snakeMenu) {
		JMenu archivo = snakeMenu.getMenu(0);

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

		JMenu colores = snakeMenu.getMenu(1);

		colores.getItem(0).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeSnakeColorAction();
			}
		});

		colores.getItem(1).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeFoodColorAction();
			}
		});

		colores.getItem(2).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeBoardColorAction();
			}
		});
	}

	private JMenuBar prepareViewMenu() {
		JMenuBar snakeMenu = new JMenuBar();

		JMenu archivo = new JMenu("Menu");
		archivo.add(new JMenuItem("Nuevo"));
		archivo.add(new JMenuItem("Abrir"));
		archivo.add(new JMenuItem("Guardar"));
		archivo.add(new JMenuItem("Salir"));

		JMenu colores = new JMenu("Colores");
		colores.add(new JMenuItem("Serpiente..."));
		colores.add(new JMenuItem("Comida..."));
		colores.add(new JMenuItem("Tablero..."));

		snakeMenu.add(archivo);
		snakeMenu.add(colores);
		setJMenuBar(snakeMenu);

		return snakeMenu;
	}

	public static void main(String[] argumentos) {
		HungrySnakeGUI pantalla = new HungrySnakeGUI();
		pantalla.setVisible(true);
	}
}