package domain;

import java.util.Random;

public class HungrySnake{
	private int filas;
	private int columnas;
	private char[][] elementos;  //con elementos nos referimos a frutas y obstaculos
	private int[] filasSnake;		//para este atributo y el de abajo la pos 0 es la cabezaa
	private int[] columnasSnake;
	private int length;
	private Random random;

	public HungrySnake(int f, int c, int num_frutas, int porcentaje_obstaculos){
		if (f<3 || c<3){
			throw new IllegalArgumentException("El tablero como minimo debe ser de 3x3");
		}
		if (num_frutas<1){
			throw new IllegalArgumentException("debe haber al menos una fruta");
		}
		if (porcentaje_obstaculos<0 || porcentaje_obstaculos > 100){
			throw new IllegalArgumentException("el porcentaje debe ser entre 0 y 100");
		}
		int obstaculos = f * c * porcentaje_obstaculos/100;
		if (obstaculos + num_frutas > f * c-1){
			throw new IllegalArgumentException("no hay espacios para tantas frutas y obstaculos en el tablero");
		}
		filas= f;
		columnas= c;
		random= new Random();
		elementos = new char[f][c];
		for (int i=0; i<f; i++){
			for(int j=0; j<c; j++){
				elementos[i][j]= ' ';
			}
		
		}
		//iniciaamos la serpiente en el centro
		filasSnake = new int[f * c];
		columnasSnake =new int[f*c];
		filasSnake[0]= f/2;
		columnasSnake[0]= c/2;
		length=1;
		
		//se generan los obstaculos y frutas en casillas desocupadas aleatoriamente
		int puesto=0;
		while (puesto < obstaculos + num_frutas){
			int i = random.nextInt(f);
			int j = random.nextInt(c);
			boolean juego_empezado = (i == filasSnake[0] && j == columnasSnake[0]);
			if (elementos[i][j] == ' ' && !juego_empezado){
				if (puesto<obstaculos){
					elementos[i][j]='o';
				}
				else {
					elementos[i][j]='f';
				}
				puesto++;
			}

		}	
	}
	public boolean move(char d){
		d= Character.toUpperCase(d);
		int d_fila = 0;
		int d_columna = 0;
		if (d== 'W'){
			d_fila =-1;
		}
		else if (d== 'S'){
			d_fila =1;
		}
		else if (d== 'A'){
			d_columna =-1;
		}
		else if (d== 'D'){
			d_columna =1;
		} 
		else{
			throw new IllegalArgumentException("Direccion invalida" +d);
		}
		int nueva_fila=	filasSnake[0] + d_fila;
		int nueva_columna= columnasSnake[0] + d_columna;
		
		// hacemos que ignore si quiere ir a la casilla anterior a la cabeza
		if(length >1 && nueva_fila == filasSnake[1] && nueva_columna== columnasSnake[1]){
			return true;
		}
		// cuando llegue al borde
		if(nueva_fila < 0 || nueva_fila >= filas || nueva_columna < 0 || nueva_columna >= columnas){
			return false;
		}
		//obstaculo
		if (elementos[nueva_fila][nueva_columna] == 'o'){
			return false;
		}
		boolean comer = (elementos[nueva_fila][nueva_columna]=='f');
		//choca con su propio cuerpo, la cola no cuenta como choque excepto si esta comiendo
		int partes = length-1;
		if (comer){
			partes=length;
		}
		for (int k = 0; k < partes; k++){
			if (filasSnake[k]== nueva_fila && columnasSnake[k] == nueva_columna){
				return false;
			}
		}
		// cuando come crece una unidad
		if (comer) {
			length = length + 1;
		}

		// cuando se mueve cada parte del cuerpo ocupa el lugar de la anterior parte 
		for (int k = length - 1; k > 0; k--){
			filasSnake[k] = filasSnake[k - 1];
			columnasSnake[k] = columnasSnake[k - 1];
		}
		filasSnake[0] = nueva_fila;
		columnasSnake[0] = nueva_columna;

		// si la fruta se come, desaparece la fruta y aparece otra en una casilla libre 
		if (comer){
			elementos[nueva_fila][nueva_columna] = ' ';
			boolean[][] ocupado = new boolean[filas][columnas];
			for (int k = 0; k < length; k++){
				ocupado[filasSnake[k]][columnasSnake[k]] = true;
			}
			int libre=0;
			for (int i = 0; i < filas; i++){
				for (int j = 0; j < columnas; j++){
					if (elementos[i][j] == ' ' && !ocupado[i][j]){
						libre++;
					}
				}
			}
			if (libre > 0){
				boolean puesto = false;
				while (!puesto){
					int i = random.nextInt(filas);
					int j = random.nextInt(columnas);
					if(elementos[i][j] == ' ' && !ocupado[i][j]){
						elementos[i][j] = 'f';
						puesto = true;
					}	
				}
			}
		}
		return true;
	}
	//retorna la longitud de la snake
	public int size(){
		return length;
	}


	// retorna el tablero, '' es vacio, 'o' es obstaculo, 'f' es fruta, 'H' es la cabeza y 'S' es el cuerpo de la snake
	
	public char[][] board(){
		char[][] resultado = new char[filas][columnas];
		for (int i = 0; i < filas; i++){
			for (int j = 0; j < columnas; j++){
				resultado[i][j] = elementos[i][j];
			}
		}
		for (int k = 1; k < length; k++){
			resultado[filasSnake[k]][columnasSnake[k]] = 'S';
		}
		resultado[filasSnake[0]][columnasSnake[0]] = 'H';
		return resultado;
	}
}
