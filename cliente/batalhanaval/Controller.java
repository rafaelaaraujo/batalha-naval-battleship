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

	public static void setEstadoJogo(Estado estado) {
		try {
			servidor.setEstadoJogo(estado);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static Estado getEstadoJogo() {
		try {
			return servidor.getEstadoJogo();
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

	}

	public static Jogador getOponente() {
		try {
			return servidor.getJogador(getIdOponente());
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static Estado getIdOponente() {
		return jogadorId == Estado.JOGADOR_1 ? Estado.JOGADOR_2
				: Estado.JOGADOR_1;
	}

	public static void desconectar() {
		try {
			servidor.desconectar(jogadorId);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static Evento getEventos() {
		try {
			return servidor.getJogo().getEvento();
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static void retiraEstadoPosicionandoNavio() {
		try {
			servidor.retiraEstadoAdicionandoNavio(jogadorId);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static void alteraPosicaoNavio(int idNavio, Point posicao) {
		try {
			servidor.alteraPosicaoDoNavio(jogadorId, idNavio, posicao);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static void alteraOrientacaoNavio(int idNavio,
			OrientacaoNavio orientacao) {
		try {
			servidor.alteraOrientacaoNavio(jogadorId, idNavio, orientacao);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static void adicionaNavio(Navio navio) {
		try {
			servidor.adicionaNavioTabuleiro(jogadorId, navio);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static void atirarNoOponente(int coluna, int linha) {
		try {
			servidor.atirarNoOponente(jogadorId, coluna, linha);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static Jogador getJogador() {
		try {
			return servidor.getJogador(jogadorId);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Point> getTiros(Estado id) {
		try {
			return servidor.getTiros(id);
		} catch (ErroServidorException e) {
			e.mostrarAlerta();
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

}
