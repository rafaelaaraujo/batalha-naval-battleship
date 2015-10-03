package batalhanaval;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import telas.TelaTabuleiro;
import enuns.Estado;

public class TratadorMouse implements MouseListener, MouseMotionListener {
	private TelaTabuleiro painel;
	private Jogador jogador;

	public TratadorMouse(TelaTabuleiro painel, Jogador jogador) {
		this.painel = painel;
		this.jogador = jogador;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (jogador.getJogo().getEstado() == Estado.POSICIONANDO_NAVIOS) {
			if (e.getButton() == MouseEvent.BUTTON3) { // Botão direito
				painel.alteraOrientacaoNavio();

			} else {
				painel.adicionarNavio();
			}
		} else if (jogador.getJogo().getEstado() == Estado.VEZ_JOG1) { // Jogo em andamento
			painel.adicionarJogada();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
			painel.posicaoAtual = new Point(e.getX() / 30, e.getY() / 30);
			if (jogador.getJogo().getEstado() == Estado.POSICIONANDO_NAVIOS) {
				painel.posicionarNavio(painel.posicaoAtual);
			
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	// Métodos não usados
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
