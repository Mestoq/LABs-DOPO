package presentation;

import domain.HungrySnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class HungrySnakeGUI extends JFrame {

	// ---------- Constantes ----------
	private static final int BOARD_SIZE = 15;
	private static final int FRUTAS = 1;
	private static final int PORCENTAJE_OBSTACULOS = 2;
	private static final int DELAY_MS = 150; //esto es para cada cuanto avnaza la snake

	// modelo y estado 
	private HungrySnake game;
	private char direction; //WASD para moverse si es ' ' todavia no se mueve
	private boolean gameOver;
	private boolean wasRunning;
	private int ticks;
	private Timer timer;

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
	private Color obstacleColor = Color.LIGHT_GRAY;
	

	public HungrySnakeGUI() {
		setTitle("HungrySnake");
		game= new HungrySnake(BOARD_SIZE, BOARD_SIZE, FRUTAS, PORCENTAJE_OBSTACULOS);
		direction = ' ';
		gameOver = false;
		ticks = 0;
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
		char[][] tablero = game.board();
		for (int fila = 0; fila < BOARD_SIZE; fila++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				char elemento = tablero[fila][col];
				if (elemento == 'o'){
					cells[fila][col].setBackground(obstacleColor);
				}
				else if (elemento == 'f'){
					cells[fila][col].setBackground(foodColor);
				}
				else if (elemento == 'S'){
					cells[fila][col].setBackground(snakeColor);
				}
				else if (elemento == 'H'){
					cells[fila][col].setBackground(snakeColor.darker());
				}
				else{	
					cells[fila][col].setBackground(boardColor);
				}
			}
		}

		int segundos = ticks * DELAY_MS / 1000;
		scoreLabel.setText("Puntaje: " + (game.size() -1));
		lengthLabel.setText("Longitud: " + game.size());
		timeLabel.setText(String.format("Tiempo: %02d:%02d", segundos / 60, segundos% 60));
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
		// se mueve con WASD
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int tecla = e.getKeyCode();
				if (tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_W) {
					changeDirection('W');
				} else if (tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_S) {
					changeDirection('S');
				} else if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_A) {
					changeDirection('A');
				} else if (tecla == KeyEvent.VK_RIGHT || tecla == KeyEvent.VK_D) {
					changeDirection('D');
				}
			}
		});

		// Reloj: en cada tick la serpiente avanza una casilla
		timer = new Timer(DELAY_MS, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				step();
			}
		});
	}

	// Guarda la direccion pedida; la primera vez tambien arranca el reloj
	private void changeDirection(char newDirection) {
		if (gameOver) {
			return;
		}
		direction = newDirection;
		if (!timer.isRunning()) {
			timer.start();
			statusLabel.setText("Jugando...");
		}
	}

	// Un paso del juego: el modelo mueve y la vista se actualiza
	private void step() {
		boolean sigueVivo = game.move(direction);
		ticks++;
		refresh();
		if (!sigueVivo) {
			gameOver = true;
			timer.stop();
			statusLabel.setText("Fin del juego. Menu > Nuevo para jugar otra vez.");
			JOptionPane.showMessageDialog(
					this,
					"¡Perdiste! Tamaño final de la serpiente: " + game.size(),
					"Fin del juego",
					JOptionPane.INFORMATION_MESSAGE
			);
		}
	}

	private void newGame() {
		timer.stop();
		game = new HungrySnake(BOARD_SIZE, BOARD_SIZE, FRUTAS, PORCENTAJE_OBSTACULOS);
		direction = ' ';
		gameOver = false;
		ticks = 0;
		refresh();
		statusLabel.setText("Listo. Use las flechas o W, A, S, D para empezar a jugar.");
		requestFocusInWindow();
	}

	// Mientras hay un dialogo abierto el juego se detiene
	private void pauseGame() {
		wasRunning = timer.isRunning();
		timer.stop();
	}

	private void resumeGame() {
		if (wasRunning) {
			timer.start();
		}
		wasRunning = false;
		requestFocusInWindow();
	}

	private void confirmExit() {
		pauseGame();
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
		resumeGame();
	}

	private void openAction() {
		pauseGame();
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
		resumeGame();
	}

	private void actionSave() {
		pauseGame();
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
		resumeGame();
	}

	/**
	 * Abre el selector de color y devuelve el color elegido,
	 * o el color actual si el usuario cancela.
	 */
	private Color chooseColor(String titulo, Color actual) {
		pauseGame();
		Color elegido = JColorChooser.showDialog(this, titulo, actual);
		resumeGame();
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

	private void changeObstacleColorAction() {
		Color nuevo = chooseColor("Color de los obstáculos", obstacleColor);
		if (!nuevo.equals(obstacleColor)) {
			obstacleColor = nuevo;
			refresh();
			statusLabel.setText("Color de los obstáculos actualizado.");
		} else {
			statusLabel.setText("Color de los obstáculos sin cambios.");
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
				changeObstacleColorAction();
			}
		});

		colores.getItem(3).addActionListener(new ActionListener() {
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
		colores.add(new JMenuItem("Obstaculos..."));
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