package batalhanaval;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Jogador automático, utilizando uma inteligência computacional
 * básica.
 * 
 * @param jogo O jogo atual
 * @author Darlan P. de Campos
 * @author Roger de Córdova Farias
 */
public class Robo extends Jogador {
	
	private static final long serialVersionUID = 1L;
	
	public static final int ACERTOU_AGUA = 0;
	public static final int ACERTOU_NAVIO = 1;
	
	public static final int PERDIDO = -1;
	public static final int NORTE = 0;
	public static final int SUL = 1;
	public static final int LESTE = 2;
	public static final int OESTE = 3;

	private int estado;
	private int direcao;
	private Point ultimoAcerto;
	private ArrayList<Point> naviosAcertados;
	
	public Robo(Jogo jogo) {
		super(jogo);
		
		direcao = PERDIDO;
		naviosAcertados = new ArrayList<Point>();
		
		for (Navio navio : getFrota()) {
			posicionaNavio(navio);
		}
	}
	
	/**
	 * O método de ataque do jogador robô.
	 * 
	 * Age de acordo com o nível de dificuldade do jogo:<br><br>
	 * 
	 * Fácil: atira aleatoriamente.<br>
	 * Médio: pode atirar aleatoriamente ou com inteligência.<br>
	 * Difícil: atira com inteligência.
	 * 
	 */
	public int atira (){
		if (getJogo().getDificuldade()==Jogo.FACIL){
			return super.atira();
		}else if(getJogo().getDificuldade()==Jogo.MEDIO){
			int i = (int)(Math.random()*2);

			if (i==1){
				return super.atira();
			}
		}
		return atiraInteligente();
	}
	
	/**
	 * Posiciona aleatoriamente os navios.
	 * 
	 * @param n Navio sendo posicionado.
	 */
	private void posicionaNavio(Navio n) {
		int x = (int) (Math.random() * 10);
		int y = (int) (Math.random() * 10);
		int orientacao = (int) (Math.random() * 2);

		Point pos = new Point(x, y);
		n.setPosicao(pos);
		n.setOrientacao(orientacao);
		if (!getTabuleiro().cabeNavio(n)) {
			posicionaNavio(n);
		} else{
			getTabuleiro().adicionaNavio(n);
		}
	}

	/**
	 * Efetua um ataque inteligente ao tabuleiro do
	 * inimigo.
	 * 
	 * @return int Valor atingido.
	 */
	private int atiraInteligente(){
		if (estado == 0){			
			int res = super.atira();
			
			if (res != 1) {
				estado = ACERTOU_NAVIO;
				ultimoAcerto = getTiros().get(getTiros().size()-1);
				naviosAcertados.add(ultimoAcerto);
			}
			return res;
		} else {			
			int y = ultimoAcerto.y;
			int x = ultimoAcerto.x;

			// Quando o robô chegar a uma posição em que o último
			// acerto está cercado por posições inválidas,
			// não pode entrar em loop infinito tentando encontrar
			// uma posição válida.
			int tentativas = 0;
			
			do {
				if (direcao == PERDIDO){
					direcao = (int)(Math.random()*4);
				}

				y = ultimoAcerto.y;
				x = ultimoAcerto.x;
				
				tentativas++;
				
				switch (direcao) {
				case NORTE:
					y -= 1;				
					break;
				case SUL:
					y += 1;				
					break;
				case LESTE:
					x += 1;				
					break;
				case OESTE:
					x -= 1;				
					break;
				}
				if(!getOponente().getTabuleiro().posicaoValida(x, y)){
					if (tentativas <= 4){
						direcao = (direcao + 1) % 4;
					} else if (naviosAcertados.lastIndexOf(ultimoAcerto) > 0){
						tentativas = 0;
						direcao = PERDIDO;
						ultimoAcerto = naviosAcertados.get(
								naviosAcertados.lastIndexOf(ultimoAcerto)-1 );
					} else {
						estado = ACERTOU_AGUA;
						return super.atira();
					}
				}
			} while (!getOponente().getTabuleiro().posicaoValida(x, y));
			
			try {
				int res = super.atira(x, y);
				if (res > 1) {
					if (getOponente().getNavio(res).estaDestruido()) {
						estado = ACERTOU_AGUA;
						// Verifica se acertou algum outro navio antes
						while (naviosAcertados.size() > 0 && estado == ACERTOU_AGUA) {
							Point pt = naviosAcertados.get(naviosAcertados.size()-1);
							if (getOponente().getTabuleiro().getPosicao(pt.x, pt.y)
									== -res) {
								naviosAcertados.remove(naviosAcertados.size()-1);
							} else {
								ultimoAcerto = pt;
								estado = ACERTOU_NAVIO;
							}
						}
					} else {
						ultimoAcerto = getTiros().get(getTiros().size()-1);
						naviosAcertados.add(ultimoAcerto);
					}
				} else {
					direcao = PERDIDO;
				}
				return res;
				
			} catch (Exception e) { return 0; }
		
		}
	}
}
