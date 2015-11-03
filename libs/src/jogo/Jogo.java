package jogo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import enuns.Estado;
import eventos.Evento;

public class Jogo implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Jogador> jogadores = new ArrayList<Jogador>();
	private Estado estado;

	// Fila de eventos
	private ArrayList<Evento> eventos;

	public Jogo() {
		eventos = new ArrayList<Evento>();
		setEstado(Estado.POSICIONANDO_NAVIOS);
	}

	public void setEstado(Estado estado) {
		 if (estado == Estado.JOGO_TERMINADO) {
			addEvento("A batalha terminou! O JOGADOR " + getVencedor().getId() + " É O VENCEDOR!");
		}

		this.estado = estado;
	}

	public Jogador getJogador(int i) {
		return jogadores.get(i);
	}

	public Estado getEstado() {
		return estado;
	}

	public Jogador getVencedor() {

		for (Jogador j : jogadores) {
			if (j.navioRestante()) {
				return j;
			}
		}

		return null;
	}

	/**
	 * Adiciona um evento ao final da fila.
	 *
	 * @param mensagem
	 *            A mensagem do evento
	 */
	public void addEvento(String mensagem) {
		this.eventos.add(new Evento(mensagem));
	}

	/**
	 * Remove e retorna o primeiro evento da fila.
	 *
	 */
	public Evento getEvento() {
		if (eventos.size() > 0)
			return eventos.remove(0);
		else
			return null;
	}

	public void addJogador(Jogador j) {
		jogadores.add(j);
	}

	public List<Jogador> getJogadores() {
		return jogadores;
	}

}
