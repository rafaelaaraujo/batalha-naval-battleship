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
	
	private Tabuleiro tabuleiro;
	private ArrayList<Navio> frota = new ArrayList<Navio>();
	private ArrayList<Point> tiros = new ArrayList<Point>();

	private ArrayList<Navio> frotaRestante = new ArrayList<Navio>();

	private int i;
	private int j;

	private int id = 0;

	public Jogador(int id) {
		this.id = id;
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


	public int getId() {
		return id;
	}
	
}
