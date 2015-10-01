package navios;

import enuns.TipoNavio;
import batalhanaval.Jogador;

public class PortaAvioes extends Navio{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PortaAvioes(Jogador jogador) {
		super(jogador);
	}

	@Override
	public String getNome() {
		return "Porta Avioes";
	}

	@Override
	public int getTamanho() {
		return 5;
	}

	@Override
	public int getId() {
		return 64;
	}

}
