package navios;

import java.awt.Point;

import enuns.TipoNavio;
import batalhanaval.Jogador;

public class BarcoPatrulha extends Navio{

	public BarcoPatrulha(Jogador jogador) {
		super(jogador);
	}
	
	@Override
	public String getNome() {
		return "BarcoPatrulha";
	}

	@Override
	public int getTamanho() {
		return 2;
	}

	@Override
	public int getId() {
		return 2;
	}

}
