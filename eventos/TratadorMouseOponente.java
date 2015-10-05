package eventos;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;

import telas.TelaTabuleiroOponente;
import batalhanaval.Jogador;
import enuns.Estado;

public class TratadorMouseOponente implements MouseListener, MouseMotionListener {
	private TelaTabuleiroOponente painel;
	private Jogador jogador;

	public TratadorMouseOponente(TelaTabuleiroOponente painel, Jogador jogador) {
		this.painel = painel;
		this.jogador = jogador;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			if (painel.principal.servidor.getEstadoJogo()  == jogador.getId()) {												// andamento
				painel.adicionarJogada();
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
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
