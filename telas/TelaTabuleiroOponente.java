package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import jogo.Jogador;
import batalhanaval.JogadorServer;
import batalhanaval.TratarImagens;
import enuns.Estado;
import eventos.TratadorMouseOponente;
import navios.Navio;

@SuppressWarnings("serial")
public class TelaTabuleiroOponente extends JPanel {

	public static final int DIM_QUADRADO = 30;

	public TelaPrincipal principal;
	private TratarImagens tratarImagens = new TratarImagens();
	private Image fundo;
	private Dimension dim;

	public Point posicaoAtual;

	private TratadorMouseOponente tm;

	public TelaTabuleiroOponente(TelaPrincipal principal) {
		this.principal = principal;

		try {
			fundo = tratarImagens.getImagemMar(1);

			dim = new Dimension(JogadorServer.getOponente().getTabuleiro()
					.getMapa().length
					* DIM_QUADRADO, JogadorServer.getOponente().getTabuleiro()
					.getMapa()[0].length
					* DIM_QUADRADO);

			setPreferredSize(dim);

			// Quadrado onde o ponteiro está
			posicaoAtual = new Point(0, 0);

			tm = new TratadorMouseOponente(this);
			addMouseListeners();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			desenhaTabuleiro(g);
			desenhaFrota(g);
			desenhaTiros(g);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	private void desenhaTiros(Graphics g) {
		try {
			Graphics2D g2 = (Graphics2D) g;

			for (Point pt : JogadorServer.getJogador().getTiros()) {
				int valor = JogadorServer.getOponente().getTabuleiro()
						.getValorPosicao(pt.x, pt.y);
				if (valor == -1) {

					g2.drawImage(tratarImagens.getImagemAgua(), pt.x * 30,
							pt.y * 30, this);

				} else if (valor < 0) {
					g2.drawImage(tratarImagens.getImagemFogo(), pt.x * 30,
							pt.y * 30, this);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void desenhaFrota(Graphics g) throws RemoteException {
		for (Navio navio : JogadorServer.getOponente().getFrota()) {
			if (navio.getPosicao() != null)
				if (navio.estaDestruido())
					g.drawImage(
							tratarImagens.getImagemNavio(navio.getId(),
									navio.getOrientacao()),
							navio.getPosicao().x * 30,
							navio.getPosicao().y * 30, null);
		}
	}

	private void desenhaTabuleiro(Graphics g) throws RemoteException {

		g.drawImage(fundo, 0, 0, null);
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

		for (int i = 1; i < 21; i++) {

			g.drawLine(i * 30, 0, i * 30, JogadorServer.getOponente()
					.getTabuleiro().getMapa().length
					* DIM_QUADRADO);
			// horizontal
			g.drawLine(0, i * 30, JogadorServer.getOponente().getTabuleiro()
					.getMapa().length
					* DIM_QUADRADO, i * 30); // linha vertical
		}

	}

	public int atira(int coluna, int linha) throws RemoteException {
		int valorAtual = JogadorServer.getOponente().getTabuleiro()
				.getValorPosicao(coluna, linha);

		if (valorAtual >= 1) { // quando posição é atingida seu valor fica negativo
			JogadorServer.atirarNoOponente(coluna, linha);

			if (valorAtual > 1
					&& JogadorServer.getOponente().getNavio(valorAtual)
							.estaDestruido()) {
				destroirNavio(valorAtual);
			}

		} else {
			return 0;
		}

		return valorAtual;

	}

	/**
	 * Destroi um navio do jogador, subtraindo id de seu total de
	 * identificadores de navios.
	 * 
	 * @param id O identificador do navio destruído.
	 */
	private void destroirNavio(int id) {
		try {

			Jogador oponente = JogadorServer.getOponente();
			Navio navio = oponente.getNavio(id);
			String nomeNavio = "Você afundou o " + navio.getNome().toUpperCase()
					+ " do adversário!";

			principal.mostraEvento(nomeNavio);
			oponente.removerNavio(id);

			if (oponente.getFrotaRestante().size() == 0) {
				JogadorServer.setEstadoJogo(Estado.TERMINADO);
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void adicionarJogada() {
		Point pos = posicaoAtual;

		try {
			int res = atira(pos.x, pos.y);
			repaint();
			
			if(res == 0){
				principal.mostraEvento("Está posição já foi atingida!!!");
				return;
				
			}else if (res == 1) {
				JogadorServer
						.setEstadoJogo(JogadorServer.jogadorId == Estado.JOGADOR_1 ? Estado.JOGADOR_2
								: Estado.JOGADOR_1);

			} else if (res > 1) {
				if (JogadorServer.getOponente().getNavio(res).estaDestruido()) {
					principal.mostraEventos();
				}
				if (JogadorServer.getEstadoJogo() == Estado.TERMINADO) {
					principal.mostraEventos();
				}
			}
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	private void addMouseListeners() {
		addMouseListener(tm);
		addMouseMotionListener(tm);
	}

	public void removeMouseListeners() {
		removeMouseListener(tm);
		removeMouseMotionListener(tm);
	}

}
