package batalhanaval;

import java.awt.Point;
import java.io.Serializable;

/**
 * O tabuleiro do jogador.
 * 
 * Possui um array bidimensional de inteiros (mapa)
 * representando as seguintes informações:<br>
 * 
 * 1 - Quadrado vazio<br>
 * 2 - Barco de patrulha<br>
 * 4 - Destróier<br>
 * 8 - Submarino<br>
 * 16 - Encouraçado<br>
 * 32 - Porta-aviões<br>
 * 
 * Quando o quadrado é atingido, passa para o negativo.
 * 
 * @author Darlan P. de Campos
 * @author Roger de Córdova Farias
 *
 */
public class Tabuleiro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int mapa[][];

	public Tabuleiro(int largura, int altura) {
		mapa = new int[largura][altura];
		
		for (int i = 0; i < mapa.length; i++)
			for (int j = 0; j < mapa[i].length; j++)
				mapa[i][j] = 1;
	}

	/**
	 * Adiciona um navio ao tabuleiro.
	 * 
	 * @param navio
	 * @throws NullPointerException Se o navio não estiver ainda posicionado.
	 */
	public void adicionaNavio(Navio navio) throws NullPointerException {
		for (Point p : navio.getArrayPosicao()) {
			mapa[p.x][p.y] = navio.getId();
		}
	}

	/**
	 * Retorna true ou false se a posição do navio
	 * cabe no tabuleiro.
	 * 
	 * @param posicao
	 * @return
	 */
	public boolean cabeNavio(Navio navio) {
		try {
			for (Point p : navio.getArrayPosicao()) {
				if (p.x > mapa.length-1 || p.y > mapa[0].length-1
						|| mapa[p.x][p.y] > 1) {
					return false;				
				}
			}
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	/**
	 * Define uma posição do tabuleiro.
	 * 
	 * @param x Coluna
	 * @param y Linha
	 * @param tipo Tipo do valor
	 */
	public void setPosicao(int x, int y, int tipo) {
		mapa[x][y] = tipo;
	}

	/**
	 * Retorna o mapa do tabuleiro.
	 * 
	 * @return mapa
	 */
	public int[][] getMapa() {
		return mapa;
	}

	/**
	 * Retorna uma posição do tabuleiro.
	 * 
	 * @param x Coluna
	 * @param y Linha
	 */
	public int getPosicao(int x, int y) {
		return mapa[x][y];
	}

	public boolean posicaoValida(int x, int y) {
		return (x < mapa.length && y < mapa[0].length
				&& x >= 0 && y >= 0 
				&& mapa[x][y] > 0);
	}
	
	@Override
	public String toString() {
		String tab = new String();
		
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[i].length; j++)
				tab += String.format("%4d", mapa[j][i]);
			tab += "\n";
		}
		
		return tab;
	}
}
