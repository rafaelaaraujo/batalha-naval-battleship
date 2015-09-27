package navios;

import java.awt.Point;

import enuns.TipoNavio;
import batalhanaval.Jogador;

public class Destroier extends Navio{

	
	public Destroier(Jogador jogador) {
		super(jogador);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getNome() {
		return "Destroier";
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
