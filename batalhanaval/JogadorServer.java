package batalhanaval;

import java.awt.Point;
import java.rmi.RemoteException;

import jogo.Jogador;
import Server.Servidor;
import enuns.Estado;
import enuns.OrientacaoNavio;
import eventos.Evento;
import navios.Navio;

public class JogadorServer {

	public static Servidor servidor;
	public static Estado jogadorId;

	public static Jogador getJogador() throws RemoteException {
		return servidor.getJogador(jogadorId);
	}

	public static void setEstadoJogo(Estado estado) {
		try {
			servidor.setEstadoJogo(estado);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static Estado getEstadoJogo() throws RemoteException {
		return servidor.getEstadoJogo();

	}

	public static Jogador getOponente() throws RemoteException {
		Estado estadoOponente = jogadorId == Estado.JOGADOR_1 ? Estado.JOGADOR_2 : Estado.JOGADOR_1;

		return servidor.getJogador(estadoOponente);
	}

	public static void desconectar() throws RemoteException {
		servidor.desconectar(jogadorId);
	}

	public static Evento getEventos() throws RemoteException {
		return servidor.getJogo().getEvento();
	}

	public static void retiraEstadoPosicionandoNavio() throws RemoteException {
		servidor.retiraEstadoAdicionandoNavio(jogadorId);
	}

	public static void alteraPosicaoNavio(int idNavio, Point posicao) throws RemoteException {
		servidor.alteraPosicaoDoNavio(jogadorId, idNavio, posicao);
	}

	public static void alteraOrientacaoNavio(int idNavio, OrientacaoNavio orientacao) throws RemoteException {
		servidor.alteraOrientacaoNavio(jogadorId, idNavio, orientacao);
	}

	public static void adicionaNavio(Navio navio) throws RemoteException {
		servidor.adicionaNavioTabuleiro(jogadorId, navio);
	}

	public static void atirarNoOponente(int coluna, int linha) {
		try {
			servidor.atirarNoOponente(jogadorId, coluna, linha);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
