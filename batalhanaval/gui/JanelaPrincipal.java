package batalhanaval.gui;

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

	// Imagens
	private Image imagensNavios[];
	private Image fogo, agua, fundo1, fundo2;
	
	// Grades
	private PainelGrade mapa1;
	private PainelGrade mapa2;
	
	// Menus
	private JMenuBar barraMenus;

	private JMenu menuJogo;
	private JMenuItem itemJogoNovo;
	private JMenuItem itemJogoAbrir;
	private JMenuItem itemJogoSalvar;
	private JMenuItem itemJogoSair;

	private JMenu menuJogoNivel;
	private JRadioButtonMenuItem itemNivelFacil;
	private JRadioButtonMenuItem itemNivelMedio;
	private JRadioButtonMenuItem itemNivelDificil;
	private JRadioButtonMenuItem itemNivelAtual; // Identifica o nível atual

	private JMenu menuAjuda;
	private JMenuItem itemAjudaSobre;

	private JTextArea caixaEventos;
	
	private Timer temp;

	public JanelaPrincipal(Jogo jogo) {
		super("Batalha Naval " + Principal.VERSAO);
		
		this.jogo = jogo;
		this.dificuldadeAtual = jogo.getDificuldade();

		setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		ActionListener fazJogada = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (JanelaPrincipal.this.jogo.getEstado() == Jogo.VEZ_JOG2) {
					int res = JanelaPrincipal.this.jogo.getJogador(1).atira();
					mapa1.repaint();

					if (res == 1) {
						temp.stop();
						JanelaPrincipal.this.jogo.setEstado(Jogo.VEZ_JOG1);
					} else if ( res > 1) {
						if (JanelaPrincipal.this.jogo.getEstado() == Jogo.TERMINADO) {
							temp.stop();
							//mostraEvento("A batalha terminou! Você foi derrotado!");
                            mostraEventos();
						} else if (JanelaPrincipal.this.jogo.getJogador(
								0 ).getNavio(res).estaDestruido())
							//mostraEvento("O adversário afundou o seu "
							//		+ JanelaPrincipal.this.jogo.getJogador(
							//				0 ).getNavio(res).getNome().toLowerCase() + "!");
                            mostraEventos();
					}
				}
			}
		};
		
		temp =  new Timer(1000, fazJogada);
		
		abreImagens();
		adicionaCaixaEventos();
		adicionaGrades();
		adicionaMenus();
		
	}

	/**
	 * Lê as imagens a partir do disco.
	 * 
	 * As imagens podem ser acessadas pelos métodos
	 * getImagemFogo(), getImagemAgua() e getImagemNavio(int idNavio).
	 * 
	 */
	private void abreImagens() {
		imagensNavios = new Image[10]; // 5 navios
		String arquivos1[] = new String[]
		        {"agua", "fogo", "mar1", "mar2"};
		String arquivos2[] = new String[]
		        {"BarcoPatrulha", "Destroier", "Submarino",
				 "Encouracado", "PortaAvioes"};
		try {
			agua = ImageIO.read(getClass().getClassLoader().getResource("img/"
					+ arquivos1[0] + ".png"));
			fogo = ImageIO.read(getClass().getClassLoader().getResource("img/"
					+ arquivos1[1] + ".png"));
			fundo1 = ImageIO.read(getClass().getClassLoader().getResource("img/"
					+ arquivos1[2] + ".png"));
			fundo2 = ImageIO.read(getClass().getClassLoader().getResource("img/"
					+ arquivos1[3] + ".png"));
			for (int i=0; i<10; i++) {
				imagensNavios[i] = ImageIO.read(
					getClass().getClassLoader().getResource("img/"
							+ (i > 4
									? arquivos2[i-5] + "V"
											: arquivos2[i] + "H") + ".png"));
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(0);
		}
	}

	private void adicionaGrades() {
		JPanel mapas = new JPanel(new GridLayout(1, 2, 30, 10));
		//mapas.setPreferredSize(new Dimension(830, 400));
		mapas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		mapa1 = new PainelGrade(this,
				this.jogo.getJogador(0), fundo1);
		mapa2 = new PainelGrade(this,
				this.jogo.getJogador(1), fundo2);
		
		mapas.add(mapa1);
		mapas.add(mapa2);
		add(mapas, BorderLayout.NORTH);
	}

	private void adicionaMenus() {
		menuJogo = new JMenu("Jogo");
		menuJogo.setMnemonic('J');

		TratadorAcoes ta = new TratadorAcoes();
		itemJogoNovo = new JMenuItem("Novo");
		itemJogoNovo.setMnemonic('N');
		itemJogoAbrir = new JMenuItem("Abrir...");
		itemJogoAbrir.setMnemonic('A');
		itemJogoSalvar = new JMenuItem("Salvar");
		itemJogoSalvar.setMnemonic('S');
		itemJogoSair = new JMenuItem("Sair");
		itemJogoSair.setMnemonic('R');
		
		itemJogoNovo.addActionListener(ta);
		itemJogoAbrir.addActionListener(ta);
		itemJogoSalvar.addActionListener(ta);
		itemJogoSair.addActionListener(ta);

		menuJogoNivel = new JMenu("Nível");
		menuJogoNivel.setMnemonic('N');

		itemNivelFacil = new JRadioButtonMenuItem("Fácil");
		itemNivelFacil.setMnemonic('F');
		itemNivelFacil.addActionListener(ta);
		itemNivelMedio = new JRadioButtonMenuItem("Médio");
		itemNivelMedio.setMnemonic('M');
		itemNivelMedio.addActionListener(ta);
		itemNivelMedio.setSelected(true);
		itemNivelDificil = new JRadioButtonMenuItem("Difícil");
		itemNivelDificil.setMnemonic('D');
		itemNivelDificil.addActionListener(ta);
		
		itemNivelAtual = itemNivelFacil;

		ButtonGroup grupoNivel = new ButtonGroup();
		grupoNivel.add(itemNivelFacil);
		grupoNivel.add(itemNivelMedio);
		grupoNivel.add(itemNivelDificil);

		menuJogoNivel.add(itemNivelFacil);
		menuJogoNivel.add(itemNivelMedio);
		menuJogoNivel.add(itemNivelDificil);

		menuJogo.add(itemJogoNovo);
		menuJogo.add(itemJogoAbrir);
		menuJogo.add(itemJogoSalvar);
		menuJogo.add(menuJogoNivel);
		menuJogo.addSeparator();
		menuJogo.add(itemJogoSair);

		barraMenus = new JMenuBar();
		barraMenus.add(menuJogo);

		menuAjuda = new JMenu("Ajuda");
		menuAjuda.setMnemonic('A');

		itemAjudaSobre = new JMenuItem("Sobre...");
		itemAjudaSobre.setMnemonic('S');
		itemAjudaSobre.addActionListener(ta);

		menuAjuda.add(itemAjudaSobre);
		barraMenus.add(menuAjuda);

		setJMenuBar(barraMenus);
	}

	public Image getImagemFogo () {
		return fogo;
	}
	
	public Image getImagemAgua() {
		return agua;
	}

	public Image getImagemNavio(int id, int or) {
		switch (id) {
		case Navio.BARCO_PATRULHA:
			return (or == Navio.VERTICAL ? imagensNavios[5] : imagensNavios[0]);
		case Navio.DESTROIER:                                             
			return (or == Navio.VERTICAL ? imagensNavios[6] : imagensNavios[1]);
		case Navio.SUBMARINO:                                             
			return (or == Navio.VERTICAL ? imagensNavios[7] : imagensNavios[2]);
		case Navio.ENCOURACADO:                                           
			return (or == Navio.VERTICAL ? imagensNavios[8] : imagensNavios[3]);
		case Navio.PORTA_AVIOES:                                          
			return (or == Navio.VERTICAL ? imagensNavios[9] : imagensNavios[4]);
		default:
			return null;
		}
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
		add(painelEventos, BorderLayout.SOUTH);
	}

	public void mostraEvento (String msg) {
		caixaEventos.append("> " + msg + "\n");
		// Rolagem automática
		caixaEventos.setCaretPosition(caixaEventos.getDocument().getLength() );		
	}

    public void mostraEventos () {
        Evento e = jogo.getEvento();

        while (e != null) {
            mostraEvento(e.getMensagem());

            e = jogo.getEvento();
        }
    }

	private class TratadorAcoes implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object src = e.getSource();

			if (src == itemJogoSair) {
				System.exit(0);
			} else if (src == itemJogoNovo) {
				jogo = new Jogo(dificuldadeAtual);
				mostraEvento("Nova batalha iniciada!");
				mapa1.reset(jogo.getJogador(0));
				mapa2.reset(jogo.getJogador(1));
			} else if (src == itemJogoSalvar) {
				try {
					ObjectOutputStream saida = new ObjectOutputStream(
							new FileOutputStream("dados" + File.separator + "jogosalvo.dat"));
					saida.writeObject(jogo);
					saida.close();
				} catch (FileNotFoundException e1) {
					mostraEvento("Arquivo não encontrado!");
					e1.printStackTrace();
				} catch (IOException e1) {
					mostraEvento("Não foi possível salvar!");
					e1.printStackTrace();
				}
				mostraEvento ("Jogo salvo com sucesso!");
			}else if (src == itemJogoAbrir) {
				ObjectInputStream entrada;
				try {
					entrada = new ObjectInputStream(
							new FileInputStream("dados" + File.separator + "jogosalvo.dat"));
					jogo = (Jogo)entrada.readObject();
					
					// Restaura a dificuldade do jogo
					if (jogo.getDificuldade() == Jogo.FACIL) {
						itemNivelFacil.setSelected(true);
						dificuldadeAtual = Jogo.FACIL;
					} else if (jogo.getDificuldade() == Jogo.MEDIO) {
						itemNivelMedio.setSelected(true);
						dificuldadeAtual = Jogo.MEDIO;
					} else {
						itemNivelDificil.setSelected(true);
						dificuldadeAtual = Jogo.DIFICIL;
					}
					
					mapa1.reset(jogo.getJogador(0));
					mapa2.reset(jogo.getJogador(1));
					mostraEvento ("Jogo aberto com sucesso!");
					entrada.close();
				} catch (FileNotFoundException e1) {
					mostraEvento("Arquivo não encontrado!");
					e1.printStackTrace();
				} catch (IOException e1) {
					mostraEvento("Não foi possível salvar!");
					e1.printStackTrace();
				} catch (ClassNotFoundException e2) {
					e2.printStackTrace();
				}
			} else if (src instanceof JRadioButtonMenuItem
					&& src != itemNivelAtual) {
				JOptionPane.showMessageDialog(null,
						"O grau de dificuldade será modificado no próximo jogo.",
						"Dificuldade", JOptionPane.INFORMATION_MESSAGE);
				dificuldadeAtual = (src == itemNivelFacil ? Jogo.FACIL
						: (src == itemNivelMedio ? Jogo.MEDIO
								: Jogo.DIFICIL));
					itemNivelAtual = (JRadioButtonMenuItem)src;
			} else if (src == itemAjudaSobre) {
				JanelaSobre sobre = new JanelaSobre(JanelaPrincipal.this);
				sobre.pack();
				sobre.setVisible(true);
			}
		}
	}
	
	/**
	 * Inicia o temporizador do jogador automático.
	 * 
	 */
	public void tempoDeEspera (){
		temp.start();
	}
}
