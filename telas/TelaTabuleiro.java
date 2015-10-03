package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JPanel;

import navios.Navio;
import enuns.OrientacaoNavio;
import enuns.Estado;
import exceptions.PosicaoJaAtingidaException;
import batalhanaval.*;

@SuppressWarnings("serial")
public class TelaTabuleiro extends JPanel {
	public static final int DIM_QUADRADO = 30;

	private TelaPrincipal principal;
	private TratarImagens tratarImagens = new TratarImagens();
	private Jogador jogador;
	private Image fundo;
	private Dimension dim;

	private int idNavioAtual = 2;
	private OrientacaoNavio orientacaoAtual;
	public Point posicaoAtual;

	private boolean mostrarNavios;

	public TelaTabuleiro(TelaPrincipal p, Jogador j, int tipoMar) {
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

		// Quadrado onde o ponteiro está
		posicaoAtual = new Point(0, 0);

		// Atual navio sendo posicionado
		if (!(jogador instanceof Robo)) {
			idNavioAtual = 2;
			orientacaoAtual = OrientacaoNavio.HORIZONTAL;
			jogador.getNavio(idNavioAtual).setPosicao(posicaoAtual);
			principal.mostraEventos();
			principal
					.mostraEvento("Movimente o navio com o mouse e clique com o "
							+ "botão esquerdo para posicioná-lo.\n"
							+ "Para mudar a orientação, clique com o botão direito.");
		}

		TratadorMouse tm = new TratadorMouse(this, jogador);
		addMouseListener(tm);
		addMouseMotionListener(tm);

		// Mostrar navios?
		mostrarNavios = (jogador.getOponente() instanceof Robo ? true : false);
	}

	public void reset(Jogador j) {
		this.jogador = j;
		idNavioAtual = 2;
		repaint();
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
			for (Point pt : jogador.getOponente().getTiros()) {
				int valor = jogador.getTabuleiro().getPosicao(pt.x, pt.y);
				if (valor == -1) {

					g.drawImage(tratarImagens.getImagemAgua(), pt.x * 30,
							pt.y * 30, null);

				} else if (valor < 0) {
					g.drawImage(tratarImagens.getImagemFogo(), pt.x * 30,
							pt.y * 30, null);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void desenhaFrota(Graphics g) {
		for (Navio navio : jogador.getFrota()) {
			if (navio.getPosicao() != null)
				if (mostrarNavios || navio.estaDestruido())
					g.drawImage(
							tratarImagens.getImagemNavio(navio.getId(),navio.getOrientacao()),
							navio.getPosicao().x * 30,
							navio.getPosicao().y * 30, null);
		}
	}

	private void desenhaTabuleiro(Graphics g) {
		g.drawImage(fundo, 0, 0, null);
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, this.getWidth() - 2, this.getHeight() - 2);
		
		for (int i = 1; i < 20; i++) {
			g.drawLine(i * 30, 0, i * 30,
					jogador.getTabuleiro().getMapa().length * DIM_QUADRADO);
			g.drawLine(0, i * 30, jogador.getTabuleiro().getMapa().length* DIM_QUADRADO, i * 30);
		}

	}

	public void alteraOrientacaoNavio() {
		OrientacaoNavio orientacaoAntiga = orientacaoAtual;

		switch (orientacaoAtual) {
		case VERTICAL:
			orientacaoAtual = OrientacaoNavio.DIAGONAL;
			break;

		case HORIZONTAL:
			orientacaoAtual = OrientacaoNavio.VERTICAL;
			break;

		case DIAGONAL:
			orientacaoAtual = OrientacaoNavio.HORIZONTAL;
			break;

		default:
			break;
		}

		jogador.getNavio(idNavioAtual).setOrientacao(orientacaoAtual);

		if (!jogador.getTabuleiro().cabeNavio(jogador.getNavio(idNavioAtual))) {
			jogador.getNavio(idNavioAtual).setOrientacao(orientacaoAntiga);
		}

		repaint();
	}

	public void adicionarNavio() {
		if (idNavioAtual <= 128) { // Botão esquerdo?
			try {
				jogador.getTabuleiro().adicionaNavio(jogador.getNavio(idNavioAtual));
				if (idNavioAtual == 128) {
					jogador.getJogo().setEstado(Estado.VEZ_JOG1);
				} else
					idNavioAtual *= 2;
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
				jogador.getJogo().setEstado(Estado.VEZ_JOG2);
				principal.tempoDeEspera();
			} else if (res > 1) {
				if (jogador.getNavio(res).estaDestruido()) {
					principal.mostraEventos();
				}
				if (jogador.getJogo().getEstado() == Estado.TERMINADO) {
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

}
