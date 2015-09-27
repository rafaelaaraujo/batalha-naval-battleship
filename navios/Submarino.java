package navios;

import java.awt.Point;

import enuns.TipoNavio;
import batalhanaval.Jogador;

public class Submarino extends Navio{

	public Submarino(Jogador jogador) {
		super(jogador);
	}

	@Override
	public String getNome() {
		return "Submarino";
	}

	@Override
	public int getTamanho() {
		return 3;
	}

	@Override
	public int getId() {
		return 8;
	}

}
