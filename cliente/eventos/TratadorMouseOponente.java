package eventos;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;

import batalhanaval.Controller;
import enuns.Estado;
import telas.TelaTabuleiroOponente;

public class TratadorMouseOponente implements MouseListener, MouseMotionListener {
	private TelaTabuleiroOponente painel;

	public TratadorMouseOponente(TelaTabuleiroOponente painel) {
		this.painel = painel;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			if (Controller.getEstadoJogo() == Controller.jogadorId) {
				painel.adicionarJogada();
			}else if(Controller.getEstadoJogo() != Estado.JOGO_TERMINADO  && Controller.getEstadoJogo() != Estado.OPONENTE_DESCONECTADO ){
				painel.principal.mostraEvento("Aguarde sua vez de jogar");
			}
		} catch (RemoteException e1) {
			Controller.mostrarAlerta();
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

	}
}
