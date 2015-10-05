package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import java.util.Timer;
import java.util.TimerTask;

import Server.Servidor;
import batalhanaval.Jogador;
import enuns.Estado;
import eventos.Evento;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

	private TelaTabuleiroJogador mapa1;
	private TelaTabuleiroOponente mapa2;
	private JTextArea caixaEventos;
	public Servidor servidor;
	private Jogador jogador;

	public TelaPrincipal(Servidor servidor, Jogador jogador) {
		this.servidor = servidor;
		this.jogador = jogador;


		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        try {
					servidor.desconectar(jogador);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
		    }
		}); 
		
		adicionaCaixaEventos();
		adicionaGrades();
	}


	private void adicionaGrades() {
		JPanel mapas = new JPanel(new GridLayout(1, 2, 30, 10));
		mapas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		mapa1 = new TelaTabuleiroJogador(this, jogador);
		try {
			mapa2 = new TelaTabuleiroOponente(this,
					getOponente(jogador));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

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
		painelEventos
				.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		caixaEventos = new JTextArea();
		JScrollPane rolagemEventos = new JScrollPane(caixaEventos);
		caixaEventos.setEditable(false);

		painelEventos.add(rolagemEventos);
		getContentPane().add(painelEventos, BorderLayout.SOUTH);
	}

	public void mostraEvento(String msg) {
		caixaEventos.append("> " + msg + "\n");
		// Rolagem automatica
		caixaEventos.setCaretPosition(caixaEventos.getDocument().getLength());
	}

	public void mostraEventos() {
		Evento e;
		try {
			e = servidor.getJogo().getEvento();

			// while (e != null) {
			mostraEvento(e.getMensagem());
			e = servidor.getJogo().getEvento();
			// }
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}



	public void setEstadoJogo(Estado estado) {
		try {
			servidor.setEstadoJogo(estado);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}



	public Estado getEstadoJogo() {
		try {
			return servidor.getEstadoJogo();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return Estado.JOGADOR_2;
	}

	public Jogador getOponente(Jogador jogador) throws RemoteException {
		Estado estadoOponente = jogador.getId() == Estado.JOGADOR_1 ? Estado.JOGADOR_2
				: Estado.JOGADOR_2;

		return servidor.getJogador(estadoOponente);
	}
	

}
