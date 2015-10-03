package batalhanaval;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import navios.Corveta;
import navios.Cruzador;
import navios.Destroier;
import navios.Encouracado;
import navios.Fragata;
import navios.Navio;
import navios.PortaAvioes;
import navios.Submarino;
import enuns.Estado;
import enuns.OrientacaoNavio;
import exceptions.PosicaoJaAtingidaException;
public class Jogador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Jogo jogo;
	private Tabuleiro tabuleiro;
	private ArrayList<Navio> frota = new ArrayList<Navio>();
	private ArrayList<Point> tiros = new ArrayList<Point>();

	private ArrayList<Navio> frotaRestante = new ArrayList<Navio>();

	private int i;
	private int j;

	/**
	 * Cria um novo jogador para o jogo.
	 * 
	 * @param jogo
	 *            Jogo do qual o jogador participa.
	 */

	public Jogador(Jogo jogo) {
		this.jogo = jogo;
		this.tabuleiro = new Tabuleiro(); // Tabuleiro zerado
		
        frota.add(new Fragata(this));
        frota.add(new Corveta(this));
        frota.add(new PortaAvioes(this));
        frota.add(new Encouracado(this));
        frota.add(new Submarino(this));
        frota.add(new Destroier(this));
        frota.add(new Cruzador(this));

        frotaRestante.addAll(frota);
        
	}


	/**
	 * Atira num ponto determinado.
	 * 
	 * @param linha
	 * @param coluna
	 */
	public int atira(int coluna, int linha) throws PosicaoJaAtingidaException{
		int valorAtual = getOponente().getTabuleiro().getPosicao(coluna, linha);

		if (valorAtual >= 1) {   // quando posição é atingida seu valor fica negativo 
			tiros.add(new Point(coluna, linha));
			getOponente().getTabuleiro().setPosicao(coluna, linha, -valorAtual); // coloca posicao como atingida
			if (valorAtual > 1 && getOponente().getNavio(valorAtual).estaDestruido()) {
				getOponente().destroirNavio(valorAtual);
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


	public void posicionarNavio(Point pos, int id) {
		getNavio(id).setPosicao(pos);
		i = pos.x;
		j = pos.y;
		int k = 0;

		while(k < getNavio(id).getTamanho()) {
			tabuleiro.setPosicao(pos.x, pos.y, id);
			
			if (getNavio(id).getOrientacao() == OrientacaoNavio.VERTICAL)
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
	private void destroirNavio(int id) {
		removerNavio(id);
		
        jogo.addEvento( getOponente() instanceof Robo
                ? "O adversário afundou o seu " + getNavio(id).getNome().toLowerCase() + "!"
                : "Você afundou o " + getNavio(id).getNome().toLowerCase() + " do adversário!" );
		
		if (frotaRestante.size() == 0)
			jogo.setEstado(Estado.TERMINADO);
	}

	private void removerNavio(int id) {
		for(int i =  0; i< frotaRestante.size();i++){
			if(frotaRestante.get(i).getId() == id){
				frotaRestante.remove(i);
				break;
			}
		}
		
	}


	public Jogo getJogo() {
		return jogo;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public ArrayList<Navio> getFrota() {
		return frota;
	}
	
	public Navio getNavio(int id) {
		for (int i = 0; i < frota.size(); i++) {
			if (frota.get(i).getId() == id)
				return frota.get(i);
		}
		return null;
	}

	public ArrayList<Point> getTiros() {
		return tiros;
	}

	public ArrayList<Navio> getFrotaRestante() {
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
