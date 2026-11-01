package test;

import domain.HungrySnake;
import org.junit.Test;
import static org.junit.Assert.*;


public class HungrySnakeTest {
	private int contar(char[][] tablero, char simbolo) {
		int total = 0;
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				if (tablero[i][j] == simbolo) {
					total++;
				}
			}
		}
		return total;
	}

	// Devuelve {fila, columna} de la primera casilla con ese simbolo
	private int[] buscar(char[][] tablero, char simbolo) {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				if (tablero[i][j] == simbolo) {
					return new int[]{i, j};
				}
			}
		}
		return null;
	}

	//generar el juego 

	@Test
	public void dadoTableroPorOmision_cuandoSeCrea_entoncesEs25x25() {
		HungrySnake juego = new HungrySnake(25, 25, 1, 2);
		char[][] tablero = juego.board();
		assertEquals(25, tablero.length);
		assertEquals(25, tablero[0].length);
	}

	@Test
	public void dadoUnJuegoNuevo_cuandoSePideElTamano_entoncesLaSerpienteMideUno() {
		HungrySnake juego = new HungrySnake(25, 25, 1, 2);
		assertEquals(1, juego.size());
		assertEquals(1, contar(juego.board(), 'H'));
		assertEquals(0, contar(juego.board(), 'S'));
	}

	@Test
	public void dadoUnaFrutaYDosPorCientoDeObstaculos_cuandoSeCrea_entoncesHayUnaFrutaYDoceObstaculos() {
		HungrySnake juego = new HungrySnake(25, 25, 1, 2);
		char[][] tablero = juego.board();
		assertEquals(1, contar(tablero, 'f'));
		assertEquals(12, contar(tablero, 'o'));   // 625 * 2 / 100
	}

	@Test
	public void dadoUnJuegoNuevo_cuandoSeUbicaLaSerpiente_entoncesEmpiezaEnElCentro() {
		HungrySnake juego = new HungrySnake(25, 25, 1, 2);
		int[] cabeza = buscar(juego.board(), 'H');
		assertEquals(12, cabeza[0]);
		assertEquals(12, cabeza[1]);
	}

	@Test
	public void dadoVariasFrutas_cuandoSeCrea_entoncesHayEsaCantidadDeFrutas() {
		HungrySnake juego = new HungrySnake(10, 8, 5, 0);
		assertEquals(5, contar(juego.board(), 'f'));
		assertEquals(0, contar(juego.board(), 'o'));
	}

	@Test(expected = IllegalArgumentException.class)
	public void dadoUnTableroMuyPequeno_cuandoSeCrea_entoncesLanzaExcepcion() {
		new HungrySnake(2, 5, 1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dadoCeroFrutas_cuandoSeCrea_entoncesLanzaExcepcion() {
		new HungrySnake(5, 5, 0, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dadoUnPorcentajeMayorACien_cuandoSeCrea_entoncesLanzaExcepcion() {
		new HungrySnake(5, 5, 1, 101);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dadoMasElementosQueCasillas_cuandoSeCrea_entoncesLanzaExcepcion() {
		new HungrySnake(3, 3, 9, 0);
	}

	//Mover

	@Test
	public void dadoUnaCasillaLibre_cuandoSeMueveConW_entoncesLaCabezaSubeUnaFila() {
		HungrySnake juego = new HungrySnake(7, 7, 1, 0);
		int[] antes = buscar(juego.board(), 'H');
		assertTrue(juego.move('W'));
		int[] despues = buscar(juego.board(), 'H');
		assertEquals(antes[0] - 1, despues[0]);
		assertEquals(antes[1], despues[1]);
	}

	@Test
	public void dadoUnaCasillaLibre_cuandoSeMueveConS_entoncesLaCabezaBajaUnaFila() {
		HungrySnake juego = new HungrySnake(7, 7, 1, 0);
		int[] antes = buscar(juego.board(), 'H');
		assertTrue(juego.move('S'));
		int[] despues = buscar(juego.board(), 'H');
		assertEquals(antes[0] + 1, despues[0]);
		assertEquals(antes[1], despues[1]);
	}

	@Test
	public void dadoUnaCasillaLibre_cuandoSeMueveConA_entoncesLaCabezaVaALaIzquierda() {
		HungrySnake juego = new HungrySnake(7, 7, 1, 0);
		int[] antes = buscar(juego.board(), 'H');
		assertTrue(juego.move('A'));
		int[] despues = buscar(juego.board(), 'H');
		assertEquals(antes[0], despues[0]);
		assertEquals(antes[1] - 1, despues[1]);
	}

	@Test
	public void dadoUnaCasillaLibre_cuandoSeMueveConD_entoncesLaCabezaVaALaDerecha() {
		HungrySnake juego = new HungrySnake(7, 7, 1, 0);
		int[] antes = buscar(juego.board(), 'H');
		assertTrue(juego.move('D'));
		int[] despues = buscar(juego.board(), 'H');
		assertEquals(antes[0], despues[0]);
		assertEquals(antes[1] + 1, despues[1]);
	}

	@Test
	public void dadaUnaDireccionEnMinuscula_cuandoSeMueve_entoncesTambienFunciona() {
		HungrySnake juego = new HungrySnake(7, 7, 1, 0);
		assertTrue(juego.move('w'));
	}

	@Test(expected = IllegalArgumentException.class)
	public void dadaUnaDireccionInvalida_cuandoSeMueve_entoncesLanzaExcepcion() {
		HungrySnake juego = new HungrySnake(7, 7, 1, 0);
		juego.move('X');
	}

	//Perder

	@Test
	public void dadoElBordeSuperior_cuandoLaSerpienteSaleDelTablero_entoncesPierde() {
		HungrySnake juego = new HungrySnake(5, 5, 1, 0);   // empieza en la fila 2
		assertTrue(juego.move('W'));    // fila 1
		assertTrue(juego.move('W'));    // fila 0
		assertFalse(juego.move('W'));   // fuera del tablero
	}

	@Test
	public void dadoUnObstaculoJuntoALaCabeza_cuandoSeMueveHaciaEl_entoncesPierde() {
		char[] direcciones = {'W', 'S', 'A', 'D'};
		int[][] cambios = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
		boolean probado = false;

		// Se crean juegos hasta que quede un obstaculo pegado a la cabeza
		for (int intento = 0; intento < 1000 && !probado; intento++) {
			HungrySnake juego = new HungrySnake(3, 3, 1, 50);
			char[][] tablero = juego.board();
			int[] cabeza = buscar(tablero, 'H');
			for (int k = 0; k < 4 && !probado; k++) {
				if (tablero[cabeza[0] + cambios[k][0]][cabeza[1] + cambios[k][1]] == 'o') {
					assertFalse(juego.move(direcciones[k]));
					probado = true;
				}
			}
		}
		assertTrue("No se logro armar un tablero con obstaculo junto a la cabeza", probado);
	}

	@Test
	public void dadaUnaSerpienteLarga_cuandoChocaConSuCuerpo_entoncesPierde() {
		// 3x3 con 8 frutas: cada movimiento come y la serpiente crece
		HungrySnake juego = new HungrySnake(3, 3, 8, 0);
		String recorrido = "WDSSAAWW";
		for (int k = 0; k < recorrido.length(); k++) {
			assertTrue(juego.move(recorrido.charAt(k)));
		}
		assertEquals(9, juego.size());
		assertFalse(juego.move('D'));   // la casilla de la derecha es su cuerpo
	}

	//Reglas especiales del movimiento

	@Test
	public void dadaUnaSerpienteDeDos_cuandoSeDevuelveSobreSuCuello_entoncesSeIgnoraElMovimiento() {
		HungrySnake juego = new HungrySnake(3, 3, 8, 0);
		juego.move('W');
		juego.move('D');
		int[] antes = buscar(juego.board(), 'H');
		assertTrue(juego.move('A'));    // hacia atras: se ignora
		int[] despues = buscar(juego.board(), 'H');
		assertEquals(3, juego.size());
		assertEquals(antes[0], despues[0]);
		assertEquals(antes[1], despues[1]);
	}

	@Test
	public void dadaLaColaDeLaSerpiente_cuandoLaCabezaEntraAEsaCasillaSinComer_entoncesNoPierde() {
		HungrySnake juego = new HungrySnake(3, 3, 8, 0);
		String recorrido = "WDSSA";    // la serpiente mide 6 y su cola esta en el centro
		for (int k = 0; k < recorrido.length(); k++) {
			juego.move(recorrido.charAt(k));
		}
		assertEquals(6, juego.size());
		assertTrue(juego.move('W'));    // entra al centro: la cola ya se libero
		assertEquals(6, juego.size());
	}

	//comer

	@Test
	public void dadaUnaFruta_cuandoLaSerpienteLaCome_entoncesCreceYApareceOtraFruta() {
		HungrySnake juego = new HungrySnake(7, 7, 1, 0);
		char[][] tablero = juego.board();
		int[] cabeza = buscar(tablero, 'H');
		int[] fruta = buscar(tablero, 'f');

		// Primero las filas y luego las columnas (no hay obstaculos)
		while (cabeza[0] != fruta[0]) {
			if (fruta[0] > cabeza[0]) {
				juego.move('S');
				cabeza[0]++;
			} else {
				juego.move('W');
				cabeza[0]--;
			}
		}
		while (cabeza[1] != fruta[1]) {
			if (fruta[1] > cabeza[1]) {
				juego.move('D');
				cabeza[1]++;
			} else {
				juego.move('A');
				cabeza[1]--;
			}
		}

		assertEquals(2, juego.size());
		assertEquals(1, contar(juego.board(), 'f'));
		assertEquals(1, contar(juego.board(), 'S'));
	}

	//tablero

	@Test
	public void dadoUnTableroDevuelto_cuandoSeModifica_entoncesElJuegoNoCambia() {
		HungrySnake juego = new HungrySnake(7, 7, 1, 0);
		char[][] tablero = juego.board();
		int[] cabeza = buscar(tablero, 'H');
		tablero[cabeza[0]][cabeza[1]] = 'o';
		assertEquals(1, contar(juego.board(), 'H'));
		assertEquals(0, contar(juego.board(), 'o'));
	}
}