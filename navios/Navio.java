package navios;

import java.awt.Point;
import java.io.Serializable;

import batalhanaval.Jogador;

public abstract class Navio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;

	private Jogador jogador;
	private Point posicao;
	private int orientacao;

	public Navio(Jogador jogador) {
		this.jogador = jogador;
		this.posicao = null;
		this.orientacao = HORIZONTAL;
	}

	public abstract String getNome();
	public abstract int getTamanho();
	public abstract int getId();
	
	public Point getPosicao(){
		return posicao;
	}
	
	/**
	 * Gera um array de pontos a partir da primeira posição
	 * 
	 * @return
	 * @throws NullPointerException Se o navio ainda não estiver posicionado.
	 */
	public Point[] getArrayPosicao() throws NullPointerException {
		Point[] arrayPos = new Point[getTamanho()];
		int i = posicao.x;
		int j = posicao.y;
		int k = 0;
		
		while(k < getTamanho()) {
			arrayPos[k++] = new Point(i, j);
			if (orientacao == VERTICAL)
				j++;
			else
				i++;	
		}
		return arrayPos;
	}

	public int getOrientacao() {
		return orientacao;
	}

	public void setPosicao(Point pos) {
		posicao = pos;
	}
	
	public void setOrientacao(int orientacao) {
		this.orientacao = orientacao;
	}

	public boolean estaDestruido() {
		for (Point p: getArrayPosicao()) {
			if (jogador.getTabuleiro().getPosicao(p.x, p.y) > 0) {
				return false;
			}
		}
		return true;
	}
}
