package navios;

import batalhanaval.Jogador;

public class Submarino extends Navio {

	private static final long serialVersionUID = 1L;

	public Submarino(Jogador jogador) {
		super(jogador);
	}

	@Override
	public String getNome() {
		return "Submarino";
	}

	@Override
	public int getTamanho() {
		return 4;
	}

	@Override
	public int getId() {
		return 6;
	}

}
