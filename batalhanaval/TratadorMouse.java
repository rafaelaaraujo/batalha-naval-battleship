package batalhanaval;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import telas.PainelGrade;
import enuns.TipoEstado;
import exceptions.PosicaoJaAtingidaException;

public class TratadorMouse implements MouseListener, MouseMotionListener {
	private PainelGrade painel;
	private Jogador jogador;

	public TratadorMouse(PainelGrade painel, Jogador jogador) {
		this.painel = painel;
		this.jogador = jogador;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!(jogador instanceof Robo) && jogador.getJogo().getEstado() == TipoEstado.POSICIONANDO_NAVIOS) {
			if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) { // Botão direito?
				painel.alteraOrientacaoNavio();

			} else {
				painel.adicionarNavio();
			}
		} else if (jogador.getJogo().getEstado() == TipoEstado.VEZ_JOG1&& (jogador instanceof Robo)) { // Jogo em andamento?
			painel.adicionarJogada();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point pos = painel.posicaoAtual;
		if (pos.x != e.getX() / 30 || pos.y != e.getY() / 30) {
			painel.posicaoAtual = new Point(e.getX() / 30, e.getY() / 30);
			if (jogador.getJogo().getEstado() == TipoEstado.POSICIONANDO_NAVIOS && !(jogador instanceof Robo)) {
				painel.posicionarNavio(painel.posicaoAtual);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	// Métodos não usados
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
