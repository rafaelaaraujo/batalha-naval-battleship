package eventos;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;

import exception.ErroServidorException;
import batalhanaval.Controller;
import telas.TelaTabuleiroJogador;

public class TratadorMouseJogador implements MouseListener, MouseMotionListener {
	private TelaTabuleiroJogador painel;

	public TratadorMouseJogador(TelaTabuleiroJogador painel) {
		this.painel = painel;
	}

	@Override
	public void mousePressed(MouseEvent e) {

		try {
			if (Controller.getJogador().isPosicionandoNavio()) {
				if (e.getButton() == MouseEvent.BUTTON3) { // Botão direito
					painel.alteraOrientacaoNavio();

				} else {
					painel.adicionarNavio();
				}
			}
		} catch (ErroServidorException e1) {
			e1.mostrarAlerta();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		painel.posicaoAtual = new Point(e.getX() / 30, e.getY() / 30);
		try {
			if (Controller.getJogador().isPosicionandoNavio()) {
				painel.posicionarNavio();

			}
		} catch (ErroServidorException e1) {
			e1.mostrarAlerta();
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
	}
}
