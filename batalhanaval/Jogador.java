package batalhanaval;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import batalhanaval.exceptions.PosicaoJaAtingidaException;

/**
 * Jogador de batalha naval.
 * 
 * @param jogo
 * @author Darlan P. de Campos
 * @author Roger de Cordova Farias
 */

public class Jogador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Jogo jogo;
	private Tabuleiro tabuleiro;
	private Navio[] frota;

	private ArrayList<Point> tiros;

	private int frotaRestante;

	/**
	 * Cria um novo jogador para o jogo.
	 * 
	 * @param jogo
	 *            Jogo do qual o jogador participa.
	 */

	public Jogador(Jogo jogo) {
		this.jogo = jogo;
		this.tabuleiro = new Tabuleiro(10, 10); // Tabuleiro zerado
		this.frota = new Navio[5];

		this.tiros = new ArrayList<Point>();

        frota[0] = Navio.constroiNavio(Navio.BARCO_PATRULHA, this);
        frota[1] = Navio.constroiNavio(Navio.DESTROIER, this);
        frota[2] = Navio.constroiNavio(Navio.SUBMARINO, this);
        frota[3] = Navio.constroiNavio(Navio.ENCOURACADO, this);
        frota[4] = Navio.constroiNavio(Navio.PORTA_AVIOES, this);

		for (int i = 0; i < frota.length; i++)
			this.frotaRestante += frota[i].getId();
	}

	/**
	 * Atira num ponto determinado.
	 * 
	 * @param linha
	 * @param coluna
	 */
	public int atira(int coluna, int linha) throws PosicaoJaAtingidaException{
		int valorAtual = getOponente().getTabuleiro().getPosicao(
				coluna, linha);

		// Quadrado nao atingido?
		if (valorAtual >= 1) {
			tiros.add(new Point(coluna, linha));
			getOponente().getTabuleiro().setPosicao(coluna, linha, -valorAtual);
			if (valorAtual > 1 
					&& getOponente().getNavio(valorAtual).estaDestruido()) {
				getOponente().destroiNavio(valorAtual);
			}
		} else {
			throw new PosicaoJaAtingidaException();
		}
		return valorAtual;
	}

	/**
	 * Atira aleatoriamente.
	 * 
	 */
	public int atira() {
		int largura = tabuleiro.getMapa()[0].length;
		int altura = tabuleiro.getMapa().length;
		int x = (int)(Math.random()*largura);
		int y = (int)(Math.random()*altura);

		try {
			return atira(x, y);
		} catch (Exception e) {
			return atira();
		}
	}

	/**
	 * Posiciona e define a orientação de um navio no tabuleiro.
	 * 
	 * @param pos Posição do navio.
	 * @param or Orientação (vertical ou horizontal).
	 * @param id Identificador.
	 */
	public void posicionaNavio(Point pos, int id) {
		getNavio(id).setPosicao(pos);
		int i = pos.x;
		int j = pos.y;
		int k = 0;

		while(k < getNavio(id).getTamanho()) {
			tabuleiro.setPosicao(pos.x, pos.y, id);
			
			if (getNavio(id).getOrientacao() == Navio.VERTICAL)
				j++;
			else
				i++;
			k++;			
		}
	}

	/**
	 * Destroi um navio do jogador, subtraindo id de seu
	 * total de identificadores de navios.
	 * 
	 * @param id O identificador do navio destruído.
	 */
	private void destroiNavio(int id) {
		frotaRestante -= id;
		
        jogo.addEvento( getOponente() instanceof Robo
                ? "O adversário afundou o seu " + getNavio(id).getNome().toLowerCase() + "!"
                : "Você afundou o " + getNavio(id).getNome().toLowerCase() + " do adversário!" );
		
		if (frotaRestante == 0)
			jogo.setEstado(Jogo.TERMINADO);
	}

	public Jogo getJogo() {
		return jogo;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public Navio[] getFrota() {
		return frota;
	}
	
	public Navio getNavio(int id) {
		for (int i = 0; i < frota.length; i++) {
			if (frota[i].getId() == id)
				return frota[i];
		}
		return null;
	}

	public ArrayList<Point> getTiros() {
		return tiros;
	}

	public int getFrotaRestante() {
		return frotaRestante;
	}

	/**
	 * Retorna o oponente do jogador.
	 * 
	 * @return <code>Jogador</code>
	 */
	public Jogador getOponente() {
		return (this == jogo.getJogador(0))
				? jogo.getJogador(1)
				: jogo.getJogador(0);
	}
}
