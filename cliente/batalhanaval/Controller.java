package batalhanaval;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JOptionPane;

import jogo.Jogador;
import Server.Servidor;
import enuns.Estado;
import enuns.OrientacaoNavio;
import eventos.Evento;
import navios.Navio;

public class Controller {

	public static Servidor servidor;
	public static Estado jogadorId;

	public static void setEstadoJogo(Estado estado) {
		try {
			servidor.setEstadoJogo(estado);

		} catch (RemoteException e) {
			mostrarAlerta();
		}
	}

	public static Estado getEstadoJogo() throws RemoteException {
		return servidor.getEstadoJogo();

	}

	public static Jogador getOponente() throws RemoteException {
		return servidor.getJogador(getIdOponente());

	}

	public static Estado getIdOponente() {
		return jogadorId == Estado.JOGADOR_1 ? Estado.JOGADOR_2
				: Estado.JOGADOR_1;
	}

	public static void desconectar() {
		try {
			servidor.desconectar(jogadorId);

		} catch (RemoteException e) {
			mostrarAlerta();
		}
	}

	public static Evento getEventos() throws RemoteException {
		return servidor.getJogo().getEvento();

	}

	public static void retiraEstadoPosicionandoNavio() {
		try {
			servidor.retiraEstadoAdicionandoNavio(jogadorId);

		} catch (RemoteException e) {
			mostrarAlerta();
		}
	}

	public static void alteraPosicaoNavio(int idNavio, Point posicao) {
		try {
			servidor.alteraPosicaoDoNavio(jogadorId, idNavio, posicao);

		} catch (RemoteException e) {
			mostrarAlerta();
		}
	}

	public static void alteraOrientacaoNavio(int idNavio,
			OrientacaoNavio orientacao) {
		try {
			servidor.alteraOrientacaoNavio(jogadorId, idNavio, orientacao);

		} catch (RemoteException e) {
			mostrarAlerta();
		}
	}

	public static void adicionaNavio(Navio navio) {
		try {
			servidor.adicionaNavioTabuleiro(jogadorId, navio);

		} catch (RemoteException e) {
			mostrarAlerta();
		}
	}

	public static void atirarNoOponente(int coluna, int linha) {
		try {
			servidor.atirarNoOponente(jogadorId, coluna, linha);

		} catch (RemoteException e) {
			mostrarAlerta();
		}
	}

	public static Jogador getJogador() throws RemoteException {
		return servidor.getJogador(jogadorId);

	}

	public static List<Point> getTiros(Estado id) throws RemoteException {
		return servidor.getTiros(id);

	}

	public static void mostrarAlerta() {
		JOptionPane
				.showMessageDialog(
						null,
						"Não foi possivel estabelecer conexão com o servidor do jogo, verifique sua internet");
	}

}
