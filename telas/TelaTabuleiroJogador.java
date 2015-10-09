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

import navios.Navio;
import enuns.OrientacaoNavio;
import enuns.Estado;
import eventos.TratadorMouseJogador;
import exceptions.PosicaoJaAtingidaException;
import batalhanaval.*;

public class TelaTabuleiroJogador extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int TOTAL_NAVIOS = 8;
	public static final int DIM_QUADRADO = 30;

	public TelaPrincipal principal;
	private TratarImagens tratarImagens = new TratarImagens();
	public Jogador jogador;

	private int idNavioAtual = 2;
	private OrientacaoNavio orientacaoAtual;
	public Point posicaoAtual;

	private TratadorMouseJogador tm;

	public TelaTabuleiroJogador(TelaPrincipal p, Jogador j) {
		principal = p;
		jogador = j;

		Dimension dim = new Dimension(jogador.getTabuleiro().getMapa().length
				* DIM_QUADRADO, jogador.getTabuleiro().getMapa()[0].length
				* DIM_QUADRADO);

		setPreferredSize(dim);

		// Quadrado onde o ponteiro está
		posicaoAtual = new Point(0, 0);

		// Atual navio sendo posicionado
		idNavioAtual = 2;
		orientacaoAtual = OrientacaoNavio.HORIZONTAL;
		jogador.getNavio(idNavioAtual).setPosicao(posicaoAtual);
		principal.mostraEventos();
		principal.mostraEvento("Movimente o navio com o mouse e clique com o "
				+ "botão esquerdo para posicioná-lo.\n"
				+ "Para mudar a orientação, clique com o botão direito.");

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

			for (Point pt : principal.getOponente(jogador).getTiros()) {
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
		try {
			g.drawImage(tratarImagens.getImagemMar(0), 0, 0, null);

			g.setColor(Color.BLUE);
			g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

			//desenha linahs vertical e horizontal
			for (int i = 1; i < 20; i++) {
				g.drawLine(i * 30, 0, i * 30,
						jogador.getTabuleiro().getMapa().length * DIM_QUADRADO); // linha
																					// horizontal
				g.drawLine(0, i * 30, jogador.getTabuleiro().getMapa().length
						* DIM_QUADRADO, i * 30); // linha vertical
			}
			
		} catch (IOException e) {
			e.printStackTrace();
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
					jogador.setPosicionandoNavio(false);

					try {
						principal.servidor.retiraEstadoAdicionandoNavio(jogador
								.getId());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else {
					idNavioAtual++;
				}
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}

	}

	
	public void posicionarNavio(Point posicaoAtual2) {
		try {
			Point posAntiga = jogador.getNavio(idNavioAtual).getPosicao();
			jogador.getNavio(idNavioAtual).setPosicao(posicaoAtual2);
			if (jogador.getTabuleiro().cabeNavio(jogador.getNavio(idNavioAtual))) {
				repaint();
			} else {
				jogador.getNavio(idNavioAtual).setPosicao(posAntiga);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
