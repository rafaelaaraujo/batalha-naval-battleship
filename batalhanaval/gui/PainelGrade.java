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

import javax.swing.JPanel;

import batalhanaval.*;
import batalhanaval.exceptions.PosicaoJaAtingidaException;

/**
 * Grade representando os tabuleiros dos jogadores.
 * 
 * 
 * 
 * @param jogador O jogador a quem o tabuleiro pertence.
 * @author Darlan P. de Campos
 * @author Roger de Córdova Farias
 */
@SuppressWarnings("serial")
public class PainelGrade extends JPanel {
	public static final int DIM_QUADRADO = 30;
	
	private JanelaPrincipal principal;
	private Jogador jogador;
	private Image fundo;
	private Dimension dim;
	
	private int idNavioAtual;
	private int orientacaoAtual;
	private Point posicaoAtual;
	
	private boolean mostrarNavios;

	public PainelGrade(JanelaPrincipal p, Jogador j, Image f) {
		principal = p;
		jogador = j;
		fundo = f;
		dim = new Dimension(jogador.getTabuleiro().getMapa().length*DIM_QUADRADO,
						jogador.getTabuleiro().getMapa()[0].length*DIM_QUADRADO);
		setPreferredSize(dim);

		// Quadrado onde o ponteiro está
		posicaoAtual = new Point(0, 0);
		
		// Atual navio sendo posicionado
		if (!(jogador instanceof Robo)) {
			idNavioAtual = 2;
			orientacaoAtual = Navio.HORIZONTAL;
			jogador.getNavio(idNavioAtual).setPosicao(posicaoAtual);
            principal.mostraEventos();
            principal.mostraEvento("Movimente o navio com o mouse e clique com o " +
                                   "botão esquerdo para posicioná-lo.\n" +
                                   "Para mudar a orientação, clique com o botão direito.");
		}
		
		TratadorMouse tm = new TratadorMouse();
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
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		for (int i = 1; i < 11; i++) {
			g.drawLine(i * 30, 0, i * 30, 300);
			g.drawLine(0, i * 30, 300, i * 30);
		}

		for (Navio navio : jogador.getFrota()) {
			if (navio.getPosicao() != null)
				if (mostrarNavios || navio.estaDestruido())
					g.drawImage(principal.getImagemNavio(navio.getId(),
							navio.getOrientacao()),
							navio.getPosicao().x*30,
							navio.getPosicao().y*30, null);
		}
		
		for (Point pt : jogador.getOponente().getTiros()) {
			int valor = jogador.getTabuleiro().getPosicao(pt.x, pt.y);
			if (valor == -1) {
				g.drawImage(principal.getImagemAgua(), pt.x*30, pt.y*30, null);
			} else if (valor < 0) {
				g.drawImage(principal.getImagemFogo(), pt.x*30, pt.y*30, null);
			}
		}
	}

	private class TratadorMouse implements MouseListener, MouseMotionListener {
		PainelGrade painel = PainelGrade.this;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!(jogador instanceof Robo) // Jogador humano?
					&& jogador.getJogo().getEstado() == Jogo.POSICIONANDO_NAVIOS) {
				if ((e.getModifiers() & InputEvent.BUTTON3_MASK)
						== InputEvent.BUTTON3_MASK) { // Botão direito?
					int orientacaoAntiga = orientacaoAtual;
					orientacaoAtual = (orientacaoAtual == Navio.VERTICAL
							? Navio.HORIZONTAL
									: Navio.VERTICAL);
					
					jogador.getNavio(idNavioAtual).setOrientacao(orientacaoAtual);
					
					if (!jogador.getTabuleiro().cabeNavio(
							jogador.getNavio(idNavioAtual))) {
						jogador.getNavio(idNavioAtual).setOrientacao(
								orientacaoAntiga);
					}

					painel.repaint();
				} else if (idNavioAtual <= 32 ) { // Botão esquerdo?
					try {
						jogador.getTabuleiro().adicionaNavio(
								jogador.getNavio(idNavioAtual));
						if (idNavioAtual == 32) {
							jogador.getJogo().setEstado(Jogo.VEZ_JOG1);
						} else
							idNavioAtual *= 2;
					} catch (NullPointerException npe) {}
				}
			} else if (jogador.getJogo().getEstado() == Jogo.VEZ_JOG1
					&& (jogador instanceof Robo)){ // Jogo em andamento?
				Point pos = painel.posicaoAtual;
				
				try {
					int res = jogador.getOponente().atira(pos.x, pos.y);
					painel.repaint();
					if (res == 1) {
						jogador.getJogo().setEstado(Jogo.VEZ_JOG2);
						principal.tempoDeEspera();
					} else if ( res > 1){
							if (jogador.getNavio(res).estaDestruido()) {
//                                principal.mostraEvento("Você afundou o "
//                                        + jogador.getNavio(res).getNome().toLowerCase()
//                                        + " do adversário!");								
                                principal.mostraEventos();
							}
							if (jogador.getJogo().getEstado() == Jogo.TERMINADO) {
                                principal.mostraEventos();
//                                principal.mostraEvento("A batalha terminou! Você venceu!");
							}
					}
				} catch (PosicaoJaAtingidaException ex) {
					principal.mostraEvento(ex.getMessage());
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			Point pos = painel.posicaoAtual;
			if (pos.x != e.getX()/30 || pos.y != e.getY()/30) {
				posicaoAtual = new Point(e.getX() / 30, e
						.getY() / 30);
				if (jogador.getJogo().getEstado() == Jogo.POSICIONANDO_NAVIOS
						&& !(jogador instanceof Robo)) {
					Point posAntiga = jogador.getNavio(idNavioAtual).getPosicao();
					jogador.getNavio(idNavioAtual).setPosicao(posicaoAtual);
					if (jogador.getTabuleiro().cabeNavio(
							jogador.getNavio(idNavioAtual))) {
						painel.repaint();
					} else {
						jogador.getNavio(idNavioAtual).setPosicao(posAntiga);
					}
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) { mouseMoved(e); }

		// Métodos não usados
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}
}
