package navios;

import java.awt.Point;

import enuns.TipoNavio;
import batalhanaval.Jogador;

public class Encouracado extends Navio{

	public Encouracado(Jogador jogador) {
		super(jogador);
	}

	@Override
	public String getNome() {
		return "Encouracado";
	}

	@Override
	public int getTamanho() {
		return 4;
	}

	@Override
	public int getId() {
		return 16;
	}


}
