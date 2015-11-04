package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import batalhanaval.Controller;
import eventos.Evento;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

	private TelaTabuleiroJogador mapa1;
	private TelaTabuleiroOponente mapa2;
	private JTextArea caixaEventos;

	public TelaPrincipal() {

		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setTitle(Controller.jogadorId.toString());

		setWindowListner();
		adicionaCaixaEventos();
		adicionaTabuleiros();
	}

	private void setWindowListner() {
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					Controller.desconectar();
			}
			
		});
	}

	private void adicionaTabuleiros() {
		JPanel mapas = new JPanel(new GridLayout(1, 2, 30, 10));
		mapas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		mapa1 = new TelaTabuleiroJogador(this);
		mapa2 = new TelaTabuleiroOponente(this);

		mapas.add(mapa1);
		mapas.add(mapa2);

		getContentPane().add(mapas, BorderLayout.NORTH);
	}

	public void atualizaGrades() {
		mapa1.repaint();
		mapa2.repaint();
	}

	private void adicionaCaixaEventos() {

		JPanel painelEventos = new JPanel(new GridLayout(1, 1));
		painelEventos.setPreferredSize(new Dimension(630, 150));
		painelEventos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		caixaEventos = new JTextArea();
		JScrollPane rolagemEventos = new JScrollPane(caixaEventos);
		caixaEventos.setEditable(false);

		painelEventos.add(rolagemEventos);
		getContentPane().add(painelEventos, BorderLayout.SOUTH);
	}

	public void mostraEvento(String msg) {
		caixaEventos.append("--> " + msg + "\n");
		// Rolagem automatica
		caixaEventos.setCaretPosition(caixaEventos.getDocument().getLength());
	}

	public void mostraEventos() {
		Evento e;
		try {
			e = Controller.getEventos();
			if (e != null)
				mostraEvento(e.getMensagem());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
