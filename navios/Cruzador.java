package navios;

import batalhanaval.Jogador;

public class Cruzador extends Navio{

	public Cruzador(Jogador jogador) {
		super(jogador);
	}

	@Override
	public String getNome() {
		return "Cruzador";
	}

	@Override
	public int getTamanho() {
		return 3;
	}

	@Override
	public int getId() {
		return 4;
	}

}
