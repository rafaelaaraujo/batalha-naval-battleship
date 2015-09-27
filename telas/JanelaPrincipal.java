package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import enuns.TipoEstado;
import enuns.TipoNavio;
import batalhanaval.*;

/**
 * A janela principal da aplicação.
 * 
 * @param jogo
 * @author Darlan P. de Campos
 * @author Roger de Córdova Farias
 */

@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame {
	private Jogo jogo;
	private int dificuldadeAtual;

	// Grades
	private PainelGrade mapa1;
	private PainelGrade mapa2;
	private JRadioButtonMenuItem itemNivelFacil;
	private JRadioButtonMenuItem itemNivelMedio;
	private JRadioButtonMenuItem itemNivelDificil;
	private JRadioButtonMenuItem itemNivelAtual; // Identifica o nível atual

	private JTextArea caixaEventos;

	private Timer temp;

	public JanelaPrincipal(Jogo jogo) {
		super("Batalha Naval " + Principal.VERSAO);

		this.jogo = jogo;
		this.dificuldadeAtual = jogo.getDificuldade();

		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		ActionListener fazJogada = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (JanelaPrincipal.this.jogo.getEstado() == TipoEstado.VEZ_JOG2) {
					int res = JanelaPrincipal.this.jogo.getJogador(1).atira();
					mapa1.repaint();

					if (res == 1) {
						temp.stop();
						JanelaPrincipal.this.jogo.setEstado(TipoEstado.VEZ_JOG1);
					} else if (res > 1) {
						if (JanelaPrincipal.this.jogo.getEstado() == TipoEstado.TERMINADO) {
							temp.stop();
							mostraEventos();
						} else if (JanelaPrincipal.this.jogo.getJogador(0)
								.getNavio(res).estaDestruido())
							mostraEventos();
					}
				}
			}
		};

		temp = new Timer(1000, fazJogada);

		adicionaCaixaEventos();
		adicionaGrades();
		adicionaMenus();

	}


	private void adicionaGrades() {
		JPanel mapas = new JPanel(new GridLayout(1, 2, 30, 10));
		mapas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		mapa1 = new PainelGrade(this, this.jogo.getJogador(0), 0);
		mapa2 = new PainelGrade(this, this.jogo.getJogador(1), 1);

		mapas.add(mapa1);
		mapas.add(mapa2);
		getContentPane().add(mapas, BorderLayout.NORTH);
	}

	private void adicionaMenus() {

		itemNivelAtual = itemNivelFacil;

		ButtonGroup grupoNivel = new ButtonGroup();
		grupoNivel.add(itemNivelFacil);
		grupoNivel.add(itemNivelMedio);
		grupoNivel.add(itemNivelDificil);
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
		// Rolagem automática
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
