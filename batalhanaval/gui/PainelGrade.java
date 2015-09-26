package batalhanaval.gui;

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

import enuns.TipoEstado;
import batalhanaval.*;
import batalhanaval.exceptions.PosicaoJaAtingidaException;

@SuppressWarnings("serial")
public class PainelGrade extends JPanel {
	public static final int DIM_QUADRADO = 30;

	private JanelaPrincipal principal;
	private TratarImagens tratarImagens = new TratarImagens();
	private Jogador jogador;
	private Image fundo;
	private Dimension dim;

	private int idNavioAtual;
	private int orientacaoAtual;
	public Point posicaoAtual;

	private boolean mostrarNavios;

	public PainelGrade(JanelaPrincipal p, Jogador j, int tipoMar) {
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
			orientacaoAtual = Navio.HORIZONTAL;
			jogador.getNavio(idNavioAtual).setPosicao(posicaoAtual);
			principal.mostraEventos();
			principal
					.mostraEvento("Movimente o navio com o mouse e clique com o "
							+ "botão esquerdo para posicioná-lo.\n"
							+ "Para mudar a orientação, clique com o botão direito.");
		}

		TratadorMouse tm = new TratadorMouse(this,jogador);
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
		g.drawImage(fundo, 0, 0, null);
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		for (int i = 1; i < 20; i++) {
			g.drawLine(i * 30, 0, i * 30,
					jogador.getTabuleiro().getMapa().length * DIM_QUADRADO);
			g.drawLine(0, i * 30, jogador.getTabuleiro().getMapa().length
					* DIM_QUADRADO, i * 30);
		}

		for (Navio navio : jogador.getFrota()) {
			if (navio.getPosicao() != null)
				if (mostrarNavios || navio.estaDestruido())
					g.drawImage(
							tratarImagens.getImagemNavio(navio.getId(),
									navio.getOrientacao()),
							navio.getPosicao().x * 30,
							navio.getPosicao().y * 30, null);
		}

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

	public void alteraOrientacaoNavio() {
		int orientacaoAntiga = orientacaoAtual;
		orientacaoAtual = (orientacaoAtual == Navio.VERTICAL ? Navio.HORIZONTAL
				: Navio.VERTICAL);

		jogador.getNavio(idNavioAtual).setOrientacao(orientacaoAtual);

		if (!jogador.getTabuleiro().cabeNavio(jogador.getNavio(idNavioAtual))) {
			jogador.getNavio(idNavioAtual).setOrientacao(orientacaoAntiga);
		}

		repaint();
	}

	public void adicionarNavio() {
		if (idNavioAtual <= 32) { // Botão esquerdo?
			try {
				jogador.getTabuleiro().adicionaNavio(
						jogador.getNavio(idNavioAtual));
				if (idNavioAtual == 32) {
					jogador.getJogo().setEstado(TipoEstado.VEZ_JOG1);
				} else
					idNavioAtual *= 2;
			} catch (NullPointerException npe) {
			}
		}
	}
	
	public void adicionarJogada(){
		Point pos = posicaoAtual;
		
		try {
			int res = jogador.getOponente().atira(pos.x, pos.y);
			repaint();
			if (res == 1) {
				jogador.getJogo().setEstado(TipoEstado.VEZ_JOG2);
				principal.tempoDeEspera();
			} else if ( res > 1){
					if (jogador.getNavio(res).estaDestruido()) {
//                        principal.mostraEvento("Você afundou o "
//                                + jogador.getNavio(res).getNome().toLowerCase()
//                                + " do adversário!");								
                        principal.mostraEventos();
					}
					if (jogador.getJogo().getEstado() == TipoEstado.TERMINADO) {
                        principal.mostraEventos();
//                        principal.mostraEvento("A batalha terminou! Você venceu!");
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
