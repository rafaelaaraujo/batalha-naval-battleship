package Server;

import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jogo.Jogador;
import jogo.Jogo;
import enuns.Estado;
import enuns.OrientacaoNavio;
import listeners.MessageListener;
import navios.Navio;

public class ServidorImpl extends UnicastRemoteObject implements Servidor {

	private static final long serialVersionUID = 1L;
	private static final int PORTA = 2050;
	private Jogo jogo;
	private List<MessageListener> messageListeners;

	protected ServidorImpl() throws Exception {
		super();
	}

	public static void main(String[] args) {
		try {

			Servidor s = new ServidorImpl();

			Registry reg = LocateRegistry.createRegistry(PORTA);
			//System.setProperty("java.rmi.server.hostname","127.0.0.1");
			reg.rebind("Server", s);
			System.out.println("Server is ready");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro no servidor: " + e.getMessage());
		}
	}

	public Estado conectar() {
		if (jogo == null) {
			jogo = new Jogo();
		}

		if (jogo.getJogadores().size() <= 2) {
			Estado id;
			if (jogo.getJogadores().size() == 0) {
				id = Estado.JOGADOR_1;
			} else {
				id = Estado.JOGADOR_2;
			}

			jogo.addJogador(new Jogador(id));
			return id;
		} else {
			return null;
		}

	}

	public void desconectar(Estado e) throws RemoteException{

		Iterator<Jogador> jogadores = jogo.getJogadores().iterator();
		while (jogadores.hasNext()) {
			Jogador jogador = jogadores.next();
			if (jogador.getId() == e) {
				jogadores.remove();
			}
		}

		if (jogo.getJogadores().isEmpty()) {
			jogo = null;
			messageListeners = null;
		}else{
			sendMessages(Estado.OPONENTE_DESCONECTADO);
		}

	}

	@Override
	public void setEstadoJogo(Estado estado) throws RemoteException{
		jogo.setEstado(estado);
		sendMessages(estado);
	}

	@Override
	public List<Point> getTiros(Estado id) {
		return getJogador(id).getTiros();
	}

	@Override
	public Jogo getJogo() {
		return jogo;
	}

	@Override
	public boolean oponenteConectado() {
		if (jogo.getJogadores().size() > 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Estado getEstadoJogo() {
		return jogo.getEstado();
	}

	@Override
	public Jogador getJogador(Estado estado) {
		for (Jogador jogador : jogo.getJogadores()) {
			if (jogador.getId() == estado) {
				return jogador;
			}
		}
		return null;
	}

	@Override
	public void retiraEstadoAdicionandoNavio(Estado estado) throws RemoteException{

		getJogador(estado).setPosicionandoNavio(false);
		for (Jogador j : jogo.getJogadores()) {
			if (j.isPosicionandoNavio()) {
				return;
			}
		}

		sendMessages(Estado.JOGO_INICIADO);
		// inicia jogo pelo jogador 1
		setEstadoJogo(Estado.JOGADOR_1);

	}

	@Override
	public void alteraPosicaoDoNavio(Estado idJogador, int idNavio, Point posicao) {
		getJogador(idJogador).alteraPosicaoNavio(idNavio, posicao);
	}

	@Override
	public void alteraOrientacaoNavio(Estado idJogador, int idNavio, OrientacaoNavio orientacao) {
		getJogador(idJogador).alteraOrientacaoNavio(idNavio, orientacao);
	}

	@Override
	public void adicionaNavioTabuleiro(Estado idJogador, Navio navio) {
		getJogador(idJogador).adicionaNavio(navio);
	}

	@Override
	public void atirarNoOponente(Estado idJogadorAtual, int coluna, int linha) throws RemoteException {
		Jogador jogador = getJogador(idJogadorAtual);
		Jogador oponente = getJogador(getIdOponente(idJogadorAtual));

		jogador.getTiros().add(new Point(coluna, linha));
		int valorAtual = oponente.getTabuleiro().getValorPosicao(coluna, linha);
		oponente.getTabuleiro().setPosicao(coluna, linha, -valorAtual);

		sendMessages(Estado.NOVO_TIRO);

	}

	public Estado getIdOponente(Estado idJogadorAtual) {
		return idJogadorAtual == Estado.JOGADOR_1 ? Estado.JOGADOR_2 : Estado.JOGADOR_1;
	}

	@Override
	public void addMessageListener(MessageListener messageListener) throws RemoteException {
		if (messageListeners == null) {
			messageListeners = new ArrayList<>();
		}
		messageListeners.add(messageListener);
	}

	public void sendMessages(Estado msg) throws RemoteException{
		for (MessageListener listener : messageListeners) {
			listener.message(msg);
		}
	}

	@Override
	public List<Navio> getFrota(Estado idJogador) throws RemoteException,
			RemoteException {
		return getJogador(idJogador).getFrota();
	}

	@Override
	public boolean posicioanandoNavio(Estado idJogador)
			throws  RemoteException {
		return getJogador(idJogador).isPosicionandoNavio();
	}

}