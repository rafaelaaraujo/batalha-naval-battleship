package navios;

import batalhanaval.Jogador;

public class Destroier extends Navio{

	public Destroier(Jogador jogador) {
		super(jogador);
	}
	
	@Override
	public String getNome() {
		return "Destroier";
	}

	@Override
	public int getTamanho() {
		return 2;
	}

	@Override
	public int getId() {
		return 4;
	}

}
