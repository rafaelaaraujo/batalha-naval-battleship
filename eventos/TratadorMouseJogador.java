package eventos;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import batalhanaval.Jogador;
import telas.TelaTabuleiroOponente;
import telas.TelaTabuleiroJogador;
import enuns.Estado;

public class TratadorMouseJogador implements MouseListener, MouseMotionListener {
	private TelaTabuleiroJogador painel;
	private Jogador jogador;

	public TratadorMouseJogador(TelaTabuleiroJogador painel, Jogador jogador) {
		this.painel = painel;
		this.jogador = jogador;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (painel.jogador.isPosicionandoNavio()) {
			if (e.getButton() == MouseEvent.BUTTON3) { // Botão direito
				painel.alteraOrientacaoNavio();

			} else {
				painel.adicionarNavio();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
			painel.posicaoAtual = new Point(e.getX() / 30, e.getY() / 30);
			if (painel.jogador.isPosicionandoNavio()) {
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
