package navios;

import batalhanaval.Jogador;

public class Fragata extends Navio{

	public Fragata(Jogador jogador) {
		super(jogador);
	}

	@Override
	public String getNome() {
		return "Fragata";
	}

	@Override
	public int getTamanho() {
		return 1;
	}

	@Override
	public int getId() {
		return 32;
	}

}
