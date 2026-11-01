package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class HungrySnakeGUI extends JFrame {


	private static final int BOARD_SIZE = 15;
	private static final Color COLOR_EMPTY = new Color(53, 53, 53);
	private static final Color COLOR_SNAKE_HEAD = new Color(20, 110, 40);
	private static final Color COLOR_SNAKE_BODY = new Color(60, 170, 70);
	private static final Color COLOR_FOOD = new Color(200, 40, 40);


	private JPanel infoPanel;
	private JLabel scoreLabel;
	private JLabel lengthLabel;
	private JLabel timeLabel;

	// Zona central (tablero)
	private JPanel boardPanel;
	private JPanel[][] cells;

	// Zona inferior (estado)
	private JPanel statusPanel;
	private JLabel statusLabel;

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

	/** Zona superior con datos del puntaje, longitud de la serpiente y tiempo. */
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

	/** Zona central con el tablero de juego. */
	private void prepareViewBoard() {
		boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE, 1, 1));
		boardPanel.setBackground(Color.DARK_GRAY);
		boardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));

		cells = new JPanel[BOARD_SIZE][BOARD_SIZE];
		for (int fila = 0; fila < BOARD_SIZE; fila++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				cells[fila][col] = new JPanel();
				cells[fila][col].setBackground(COLOR_EMPTY);
				boardPanel.add(cells[fila][col]);
			}
		}

		add(boardPanel, BorderLayout.CENTER);
	}

	/**
	 *
	 * Zona inferior con los mensajes de estado.
	 */
	private void prepareViewStatus() {
		statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusPanel.setBorder(BorderFactory.createEtchedBorder());
		statusLabel = new JLabel("Listo. Seleccione Menu > Nuevo para empezar.");
		statusPanel.add(statusLabel);

		add(statusPanel, BorderLayout.SOUTH);
	}

	/**
	 * Actualiza la vista del tablero.
	 * Por ahora pinta el tablero inicial por omisión
	 * serpiente de 3 celdas en el centro y una comida.
	 * (Se  termina de implementar realmente en ciclos posteriores.)
	 */
	private void refresh() {
		// Limpiar
		for (int fila = 0; fila < BOARD_SIZE; fila++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				cells[fila][col].setBackground(COLOR_EMPTY);
			}
		}

		int centro = BOARD_SIZE / 2;

		// Serpiente inicial (cabeza a la derecha)
		cells[centro][centro - 2].setBackground(COLOR_SNAKE_BODY);
		cells[centro][centro - 1].setBackground(COLOR_SNAKE_BODY);
		cells[centro][centro].setBackground(COLOR_SNAKE_HEAD);

		// Comida inicial
		cells[centro][centro + 4].setBackground(COLOR_FOOD);

		boardPanel.revalidate();
		boardPanel.repaint();
	}

	// =====================================================
	//                    CONTROLADOR
	// =====================================================
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

	private void prepareControllerMenu(JMenuBar snakeMenu) {
		JMenu archivo = snakeMenu.getMenu(0);

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

		JMenuItem exit = archivo.getItem(3);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmExit();
			}
		});
	}

	private JMenuBar prepareViewMenu() {
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