package jogo;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import enuns.Estado;
import enuns.OrientacaoNavio;
import navios.Corveta;
import navios.Cruzador;
import navios.Destroier;
import navios.Encouracado;
import navios.Fragata;
import navios.Navio;
import navios.PortaAvioes;
import navios.Submarino;

public class Jogador implements Serializable {

	private static final long serialVersionUID = 1L;

	private Tabuleiro tabuleiro;
	private ArrayList<Navio> frota = new ArrayList<Navio>();
	private ArrayList<Point> tiros = new ArrayList<Point>();

	private boolean posicionandoNavio = true;

	private int i;
	private int j;

	private Estado id;

	public Jogador(Estado id) {
		this.id = id;
		tabuleiro = new Tabuleiro(); // Tabuleiro zerado

		frota.add(new Fragata(this));
		frota.add(new Corveta(this));
		frota.add(new PortaAvioes(this));
		frota.add(new Encouracado(this));
		frota.add(new Submarino(this));
		frota.add(new Destroier(this));
		frota.add(new Cruzador(this));

	}

	public void posicionarNavio(Point pos, int id) {
		getNavio(id).setPosicao(pos);
		i = pos.x;
		j = pos.y;
		int k = 0;

		while (k < getNavio(id).getTamanho()) {
			tabuleiro.setPosicao(pos.x, pos.y, id);

			if (getNavio(id).getOrientacao() == OrientacaoNavio.VERTICAL)
				j++;
			else
				i++;
			k++;
		}
	}

	public boolean navioRestante() {
		for(Navio n: frota){
			if(!n.estaDestruido()){
				return true;
			}
		}
		
		return false;
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

	public Estado getId() {
		return id;
	}

	public boolean isPosicionandoNavio() {
		return posicionandoNavio;
	}

	public void setPosicionandoNavio(boolean posicionandoNavio) {
		this.posicionandoNavio = posicionandoNavio;
	}

	public void alteraPosicaoNavio(int idNavio, Point posicao) {
		getNavio(idNavio).setPosicao(posicao);
	}

	public void alteraOrientacaoNavio(int idNavio, OrientacaoNavio orientacao) {
		getNavio(idNavio).setOrientacao(orientacao);
	}

	public void adicionaNavio(Navio navio) {
		tabuleiro.adicionaNavio(navio);
	}
}
