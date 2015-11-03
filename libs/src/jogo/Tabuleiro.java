package jogo;

import java.awt.Point;
import java.io.Serializable;

import navios.Navio;

public class Tabuleiro implements Serializable {

	private static final long serialVersionUID = 1L;

	private int mapa[][];

	public Tabuleiro() {
		mapa = new int[15][15];

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
	 * Retorna true ou false se a posição do navio cabe no tabuleiro.
	 * 
	 * @param posicao
	 * @return
	 */
	public boolean cabeNavio(Navio navio) {
		try {
			for (Point p : navio.getArrayPosicao()) {
				if (p.x > mapa.length - 1 || p.y > mapa[0].length - 1 || mapa[p.x][p.y] > 1) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Define uma posicao do tabuleiro.
	 * 
	 * @param x Coluna
	 * @param y Linha
	 * @param tipo
	 *            Tipo do valor
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

	public int getValorPosicao(int x, int y) {
		return mapa[x][y];
	}

	public boolean posicaoValida(int x, int y) {
		return (x < mapa.length && y < mapa[0].length && x >= 0 && y >= 0 && mapa[x][y] > 0);
	}
}
