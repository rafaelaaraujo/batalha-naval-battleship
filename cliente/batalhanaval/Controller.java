package batalhanaval;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.List;

import jogo.Jogador;
import Server.Servidor;
import enuns.Estado;
import enuns.OrientacaoNavio;
import eventos.Evento;
import exception.ErroServidorException;
import navios.Navio;

public class Controller {

	public static Servidor servidor;
	public static Estado jogadorId;

	public static 
	Jogador getJogador() throws ErroServidorException {
		return servidor.getJogador(jogadorId);
	}

	public static void setEstadoJogo(Estado estado) {
		try {
			servidor.setEstadoJogo(estado);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		}
	}

	public static Estado getEstadoJogo() throws ErroServidorException {
		return servidor.getEstadoJogo();

	}

	public static Jogador getOponente() throws ErroServidorException {
		return servidor.getJogador(getIdOponente());
	}

	public static Estado getIdOponente() {
		return jogadorId == Estado.JOGADOR_1 ? Estado.JOGADOR_2 : Estado.JOGADOR_1;
	}

	
	public static void desconectar(){
		try {
			servidor.desconectar(jogadorId);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		}
	}

	public static Evento getEventos() throws ErroServidorException {
		return servidor.getJogo().getEvento();
	}

	public static void retiraEstadoPosicionandoNavio() throws ErroServidorException {
		servidor.retiraEstadoAdicionandoNavio(jogadorId);
	}

	public static void alteraPosicaoNavio(int idNavio, Point posicao){
		try {
			servidor.alteraPosicaoDoNavio(jogadorId, idNavio, posicao);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		}
	}

	public static void alteraOrientacaoNavio(int idNavio, OrientacaoNavio orientacao){
		try {
			servidor.alteraOrientacaoNavio(jogadorId, idNavio, orientacao);
		} catch (ErroServidorException e) {
			e.printStackTrace();
		}
	}

	public static void adicionaNavio(Navio navio){
		try {
			servidor.adicionaNavioTabuleiro(jogadorId, navio);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		}
	}

	public static void atirarNoOponente(int coluna, int linha) {
		try {
			servidor.atirarNoOponente(jogadorId, coluna, linha);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		}
	}

	public static List<Point> getTiros(Estado id) throws ErroServidorException {
		return servidor.getTiros(id);
	}

}
