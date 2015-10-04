package eventos;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import batalhanaval.Jogador;
import telas.TelaTabuleiroDireita;
import telas.TelaTabuleiroEsquerda;
import enuns.Estado;

public class TratadorMouseDireita implements MouseListener, MouseMotionListener {
	private TelaTabuleiroDireita painel;
	private Jogador jogador;

	public TratadorMouseDireita(TelaTabuleiroDireita painel, Jogador jogador) {
		this.painel = painel;
		this.jogador = jogador;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (jogador.getJogo().getEstado() == Estado.POSICIONANDO_NAVIOS) {
			return;
	
		} else if (jogador.getJogo().getEstado() == Estado.VEZ_JOG1) {												// andamento
			painel.adicionarJogada();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		painel.posicaoAtual = new Point(e.getX() / 30, e.getY() / 30);
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
