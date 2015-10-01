package navios;

import batalhanaval.Jogador;

public class Agua extends Navio{
	
	private static final long serialVersionUID = 1L;

	public Agua(Jogador jogador) {
		super(jogador);
	}

	@Override
	public String getNome() {
		return "agua";
	}

	@Override
	public int getTamanho() {
		return 0;
	}

	@Override
	public int getId() {
		return 1;
	}

}
