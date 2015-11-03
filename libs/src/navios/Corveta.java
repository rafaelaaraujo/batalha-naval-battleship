package navios;

import jogo.Jogador;

public class Corveta extends Navio {

	public Corveta(Jogador jogador) {
		super(jogador);
	}

	@Override
	public String getNome() {
		return "Corveta";
	}

	@Override
	public int getTamanho() {
		return 1;
	}

	@Override
	public int getId() {
		return 2;
	}

}
