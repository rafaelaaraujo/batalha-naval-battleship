package telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JPanel;

import jogo.Jogador;
import batalhanaval.Controller;
import batalhanaval.TratarImagens;
import enuns.Estado;
import enuns.OrientacaoNavio;
import eventos.TratadorMouseJogador;
import exception.ErroServidorException;
import navios.Navio;

public class TelaTabuleiroJogador extends JPanel {

	private static final long serialVersionUID = 1L;
	private  final int TOTAL_NAVIOS = 8;
	public  final int DIM_QUADRADO = 30;	
	public  final int TAM_MAPA = 15;


	public TelaPrincipal principal;
	private TratarImagens tratarImagens = new TratarImagens();

	private int idNavioAtual = 2;
	public Point posicaoAtual;

	private TratadorMouseJogador tm;

	public TelaTabuleiroJogador(TelaPrincipal p) {
			principal = p;
			Dimension dim;

			dim = new Dimension(TAM_MAPA* DIM_QUADRADO,TAM_MAPA * DIM_QUADRADO);

			setPreferredSize(dim);

			// Quadrado onde o ponteiro está
			posicaoAtual = new Point(0, 0);

			// Atual navio sendo posicionado
			idNavioAtual = 2;
			Controller.alteraPosicaoNavio(idNavioAtual, posicaoAtual);
			principal.mostraEventos();
			principal.mostraEvento("**Movimente o navio com o mouse e clique com o "
					+ "botão esquerdo para posicioná-lo.\n**Para mudar a orientação, clique com o botão direito.");

			tm = new TratadorMouseJogador(this);
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

			for (Point pt : Controller.getTiros(Controller.getIdOponente())) {
				int valor = Controller.getJogador().getTabuleiro().getValorPosicao(pt.x, pt.y);
				if (valor == -1) {

					g2.drawImage(tratarImagens.getImagemAgua(), pt.x * 30, pt.y * 30, this);

				} else if (valor < 0) {
					g2.drawImage(tratarImagens.getImagemFogo(), pt.x * 30, pt.y * 30, this);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void desenhaFrota(Graphics g) {
		for (Navio navio : Controller.getJogador().getFrota()) {
			if (navio.getPosicao() != null)
				g.drawImage(tratarImagens.getImagemNavio(navio.getId(), navio.getOrientacao()),
						navio.getPosicao().x * 30, navio.getPosicao().y * 30, null);
		}
	}

	private void desenhaTabuleiro(Graphics g) {
		try {
			g.drawImage(tratarImagens.getImagemMar(0), 0, 0, null);

			g.setColor(Color.BLUE);
			g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

			for (int i = 1; i < 21; i++) {
				g.drawLine(i * 30, 0, i * 30, TAM_MAPA * DIM_QUADRADO); // linha horizontal
				g.drawLine(0, i * 30, TAM_MAPA * DIM_QUADRADO, i * 30); // linha vertical
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void alteraOrientacaoNavio() throws ErroServidorException {
		OrientacaoNavio orientacaoAntiga = Controller.getJogador().getNavio(idNavioAtual).getOrientacao();
		OrientacaoNavio orientacaoAtual;
		
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
			orientacaoAtual = OrientacaoNavio.HORIZONTAL;
			break;
		}

		Controller.alteraOrientacaoNavio(idNavioAtual, orientacaoAtual);

		repaint();
	}

	public void adicionarNavio() {
		if (idNavioAtual <= TOTAL_NAVIOS) {
			try {
				Navio navio = Controller.getJogador().getNavio(idNavioAtual);
				Controller.adicionaNavio(navio);

				if (idNavioAtual == TOTAL_NAVIOS) {
					Controller.retiraEstadoPosicionandoNavio();

					if (Controller.getEstadoJogo() == Estado.POSICIONANDO_NAVIOS)
						principal.mostraEvento("Aguarde oponente posicionar todos os navios");

				} else {
					idNavioAtual++;
				}
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}
	}

	public void posicionarNavio() {
		try {
			Point posAntiga = Controller.getJogador().getNavio(idNavioAtual).getPosicao();
			Controller.alteraPosicaoNavio(idNavioAtual, posicaoAtual);
			Jogador jogador = Controller.getJogador();
			Navio navio = jogador.getNavio(idNavioAtual);

			if (jogador.getTabuleiro().cabeNavio(navio)) {
				repaint();
			} else {
				Controller.alteraPosicaoNavio(idNavioAtual, posAntiga);
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
