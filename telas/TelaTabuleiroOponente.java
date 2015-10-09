package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.cert.PKIXRevocationChecker.Option;

import javax.swing.JPanel;

import navios.Navio;
import enuns.OrientacaoNavio;
import enuns.Estado;
import eventos.TratadorMouseOponente;
import eventos.TratadorMouseJogador;
import exceptions.PosicaoJaAtingidaException;
import Server.Servidor;
import batalhanaval.*;

@SuppressWarnings("serial")
public class TelaTabuleiroOponente extends JPanel {

	public static final int DIM_QUADRADO = 30;

	public TelaPrincipal principal;
	private TratarImagens tratarImagens = new TratarImagens();
	private Image fundo;
	private Dimension dim;

	public Point posicaoAtual;

	private TratadorMouseOponente tm;
	private Jogador oponente;

	public TelaTabuleiroOponente(TelaPrincipal p, Jogador jogador) {
		this.oponente = jogador;
		principal = p;
		try {
			fundo = tratarImagens.getImagemMar(1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		dim = new Dimension(jogador.getTabuleiro().getMapa().length
				* DIM_QUADRADO, jogador.getTabuleiro().getMapa()[0].length
				* DIM_QUADRADO);
		setPreferredSize(dim);

		// Quadrado onde o ponteiro está
		posicaoAtual = new Point(0, 0);

		principal.mostraEventos();

		tm = new TratadorMouseOponente(this, oponente);
		addMouseListeners();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		desenhaTabuleiro(g);
		desenhaFrota(g);
		desenhaTiros(g);

	}

	private void desenhaTiros(Graphics g) {
		try {
			Graphics2D g2 = (Graphics2D) g;

			for (Point pt : oponente.getTiros()) {
				int valor = oponente.getTabuleiro().getValorPosicao(pt.x, pt.y);
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

	private void desenhaFrota(Graphics g) {
		for (Navio navio : oponente.getFrota()) {
			if (navio.getPosicao() != null)
				if (navio.estaDestruido())
					g.drawImage(
							tratarImagens.getImagemNavio(navio.getId(),
									navio.getOrientacao()),
							navio.getPosicao().x * 30,
							navio.getPosicao().y * 30, null);
		}
	}

	private void desenhaTabuleiro(Graphics g) {
		g.drawImage(fundo, 0, 0, null);
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

		for (int i = 1; i < 20; i++) {
			g.drawLine(i * 30, 0, i * 30,
					oponente.getTabuleiro().getMapa().length * DIM_QUADRADO); // linha
																				// horizontal
			g.drawLine(0, i * 30, oponente.getTabuleiro().getMapa().length
					* DIM_QUADRADO, i * 30); // linha vertical
		}

	}
	
	public int atira(Estado id, int coluna, int linha) throws PosicaoJaAtingidaException, RemoteException {
		int valorAtual = oponente.getTabuleiro().getValorPosicao(coluna, linha);

		if (valorAtual >= 1) { // quando posição é atingida seu valor fica
								// negativo
			oponente.getTiros().add(new Point(coluna, linha));
			oponente.getTabuleiro().setPosicao(coluna, linha, -valorAtual); // coloca

			if (valorAtual > 1 && oponente.getNavio(valorAtual).estaDestruido()) {
				destroirNavio(oponente, valorAtual);
			}

		} else {
			throw new PosicaoJaAtingidaException();
		}

		return valorAtual;

	}
	
	/**
	 * Destroi um navio do jogador, subtraindo id de seu total de
	 * identificadores de navios.
	 * 
	 * @param id
	 *            O identificador do navio destruído.
	 */
	private void destroirNavio(Jogador jogador, int id) {
		jogador.removerNavio(id);

		principal.mostraEvento("Você afundou o "
				+ jogador.getNavio(id).getNome().toLowerCase()
				+ " do adversário!");

		//colocar servidor p/ avisar q  navio foi destruido
		//if (jogador.getFrotaRestante().size() == 0)
			//jogo.setEstado(Estado.TERMINADO);
	}


	public void adicionarJogada() {
		Point pos = posicaoAtual;

		try {
			int res = atira(oponente.getId(), pos.x, pos.y);
			repaint();
			if (res == 1) {
				principal
				.setEstadoJogo(oponente.getId() == Estado.JOGADOR_1 ? Estado.JOGADOR_2
						: Estado.JOGADOR_1);				// principal.tempoDeEspera();
			} else if (res > 1) {
				if (oponente.getNavio(res).estaDestruido()) {
					principal.mostraEventos();
				}
				if (principal.getEstadoJogo() == Estado.TERMINADO) {
					principal.mostraEventos();
				}
			}
		} catch (PosicaoJaAtingidaException | RemoteException ex) {
			principal.mostraEvento(ex.getMessage());
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
