package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JPanel;

import navios.Navio;
import enuns.OrientacaoNavio;
import enuns.Estado;
import eventos.TratadorMouseJogador;
import exceptions.PosicaoJaAtingidaException;
import batalhanaval.*;

@SuppressWarnings("serial")
public class TelaTabuleiroJogador extends JPanel {

	private static final int TOTAL_NAVIOS = 8;
	public static final int DIM_QUADRADO = 30;

	public TelaPrincipal principal;
	private TratarImagens tratarImagens = new TratarImagens();
	private Jogador jogador;
	private Image fundo;
	private Dimension dim;

	private int idNavioAtual = 2;
	private OrientacaoNavio orientacaoAtual;
	public Point posicaoAtual;

	private TratadorMouseJogador tm;

	public TelaTabuleiroJogador(TelaPrincipal p, Jogador j, int tipoMar) {
		principal = p;
		jogador = j;
		try {
			fundo = tratarImagens.getImagemMar(tipoMar);
		} catch (IOException e) {
			e.printStackTrace();
		}

		dim = new Dimension(jogador.getTabuleiro().getMapa().length
				* DIM_QUADRADO, jogador.getTabuleiro().getMapa()[0].length
				* DIM_QUADRADO);
		setPreferredSize(dim);

		// Quadrado onde o ponteiro est�
		posicaoAtual = new Point(0, 0);

		// Atual navio sendo posicionado
		idNavioAtual = 2;
		orientacaoAtual = OrientacaoNavio.HORIZONTAL;
		jogador.getNavio(idNavioAtual).setPosicao(posicaoAtual);
		principal.mostraEventos();
		principal.mostraEvento("Movimente o navio com o mouse e clique com o "
				+ "bot�o esquerdo para posicion�-lo.\n"
				+ "Para mudar a orienta��o, clique com o bot�o direito.");

		tm = new TratadorMouseJogador(this, jogador);
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

			for (Point pt : jogador.getOponente().getTiros()) {
				int valor = jogador.getTabuleiro().getValorPosicao(pt.x, pt.y);
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
		for (Navio navio : jogador.getFrota()) {
			if (navio.getPosicao() != null)
				g.drawImage(
						tratarImagens.getImagemNavio(navio.getId(),
								navio.getOrientacao()),
						navio.getPosicao().x * 30, navio.getPosicao().y * 30,
						null);
		}
	}

	private void desenhaTabuleiro(Graphics g) {
		g.drawImage(fundo, 0, 0, null);
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

		for (int i = 1; i < 20; i++) {
			g.drawLine(i * 30, 0, i * 30,
					jogador.getTabuleiro().getMapa().length * DIM_QUADRADO); // linha
																				// horizontal
			g.drawLine(0, i * 30, jogador.getTabuleiro().getMapa().length
					* DIM_QUADRADO, i * 30); // linha vertical
		}

	}

	public void alteraOrientacaoNavio() {
		OrientacaoNavio orientacaoAntiga = orientacaoAtual;

		switch (orientacaoAntiga) {

		case HORIZONTAL:
			orientacaoAtual = OrientacaoNavio.VERTICAL;
			break;

		case VERTICAL:
			orientacaoAtual = OrientacaoNavio.DIAGONAL;
			break;

		case DIAGONAL:
			orientacaoAtual = OrientacaoNavio.HORIZONTAL;
			break;

		default:
			break;
		}

		jogador.getNavio(idNavioAtual).setOrientacao(orientacaoAtual);
		repaint();
	}

	public void adicionarNavio() {
		if (idNavioAtual <= TOTAL_NAVIOS) {
			try {
				jogador.getTabuleiro().adicionaNavio(
						jogador.getNavio(idNavioAtual));
				if (idNavioAtual == TOTAL_NAVIOS) {
					principal.setEstadoJogo(Estado.VEZ_JOG1);
				} else
					idNavioAtual++;
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}
	}

	public void adicionarJogada() {
		Point pos = posicaoAtual;

		try {
			int res = jogador.getOponente().atira(pos.x, pos.y);
			repaint();
			if (res == 1) {
				principal.setEstadoJogo(Estado.VEZ_JOG2);
				principal.tempoDeEspera();
			} else if (res > 1) {
				if (jogador.getNavio(res).estaDestruido()) {
					principal.mostraEventos();
				}
				if (principal.getEstadoJogo() == Estado.TERMINADO) {
					principal.mostraEventos();
				}
			}
		} catch (PosicaoJaAtingidaException ex) {
			principal.mostraEvento(ex.getMessage());
		}
	}

	public void posicionarNavio(Point posicaoAtual2) {
		Point posAntiga = jogador.getNavio(idNavioAtual).getPosicao();
		jogador.getNavio(idNavioAtual).setPosicao(posicaoAtual2);
		if (jogador.getTabuleiro().cabeNavio(jogador.getNavio(idNavioAtual))) {
			repaint();
		} else {
			jogador.getNavio(idNavioAtual).setPosicao(posAntiga);
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