package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import batalhanaval.Evento;
import batalhanaval.Jogo;
import enuns.Estado;


@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
	private Jogo jogo;

	private TelaTabuleiro mapa1;
	private TelaTabuleiro mapa2;
	private JTextArea caixaEventos;

	private Timer temp;
	
	public TelaPrincipal() {
		
		this.jogo = new Jogo();

		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		ActionListener fazJogada = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (TelaPrincipal.this.jogo.getEstado() == Estado.VEZ_JOG2) {
					int res = TelaPrincipal.this.jogo.getJogador(1).atira();
					mapa1.repaint();

					if (res == 1) {
						temp.stop();
						TelaPrincipal.this.jogo.setEstado(Estado.VEZ_JOG1);
					} else if (res > 1) {
						if (TelaPrincipal.this.jogo.getEstado() == Estado.TERMINADO) {
							temp.stop();
							mostraEventos();
						} else if (TelaPrincipal.this.jogo.getJogador(0)
								.getNavio(res).estaDestruido())
							mostraEventos();
					}
				}
			}
		};

		temp = new Timer(1000, fazJogada);

		adicionaCaixaEventos();
		adicionaGrades();
	}


	private void adicionaGrades() {
		JPanel mapas = new JPanel(new GridLayout(1, 2, 30, 10));
		mapas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		mapa1 = new TelaTabuleiro(this, this.jogo.getJogador(0), 0);
		mapa2 = new TelaTabuleiro(this, this.jogo.getJogador(1), 1);

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
		Evento e = jogo.getEvento();

		while (e != null) {
			mostraEvento(e.getMensagem());

			e = jogo.getEvento();
		}
	}

	/**
	 * Inicia o temporizador do jogador automático.
	 * 
	 */
	public void tempoDeEspera() {
		temp.start();
	}
}
