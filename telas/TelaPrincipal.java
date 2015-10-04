package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

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

	private Timer temp;

	public TelaPrincipal(Servidor servidor) {
		this.servidor = servidor;
		try {
			jogador = servidor.conectar();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		if (jogador == null) {
			JOptionPane.showMessageDialog(getContentPane(),
					"J· existe um jogo em andamento espere ele terminar");
		}

		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		/*
		 * ActionListener fazJogada = new ActionListener() { public void
		 * actionPerformed(ActionEvent evt) { if
		 * (TelaPrincipal.this.getEstadoJogo() == Estado.VEZ_JOG2) { int res =
		 * TelaPrincipal.this.jogo.getJogador(1).atira(); mapa1.repaint();
		 * 
		 * if (res == 1) { temp.stop();
		 * TelaPrincipal.this.setEstadoJogo(Estado.VEZ_JOG1); } else if (res >
		 * 1) { if (TelaPrincipal.this.getEstadoJogo() == Estado.TERMINADO) {
		 * temp.stop(); mostraEventos(); } else if
		 * (TelaPrincipal.this.jogo.getJogador(0)
		 * .getNavio(res).estaDestruido()) mostraEventos(); } } } };
		 * 
		 * temp = new Timer(1000, fazJogada);
		 */

		adicionaCaixaEventos();
		try {
			adicionaGrades();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public Estado getEstadoJogo() {
		try {
			return servidor.getEstadoJodo();
		} catch (RemoteException e) {
			e.printStackTrace();
			return Estado.TERMINADO;
		}
	}

	public void setEstadoJogo(Estado estado) {
		try {
			servidor.setEstadoJodo(estado);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void adicionaGrades() throws RemoteException {
		JPanel mapas = new JPanel(new GridLayout(1, 2, 30, 10));
		mapas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		mapa1 = new TelaTabuleiroJogador(this, jogador);
		mapa2 = new TelaTabuleiroOponente(this, servidor.getOponente(jogador.getId()));

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

			while (e != null) {
				mostraEvento(e.getMensagem());
				e = servidor.getJogo().getEvento();
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Inicia o temporizador do jogador autom√°tico.
	 * 
	 */
	public void tempoDeEspera() {
		temp.start();
	}
}
