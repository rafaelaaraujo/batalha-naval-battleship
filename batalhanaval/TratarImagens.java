package batalhanaval;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import enuns.TipoNavio;

public class TratarImagens {
	// Imagens
	private ArrayList<Image> imagensNavios;

	public TratarImagens() {
		abreImagensNavios();
	}

	public Image getImagemNavio(int tipoNavio, int or) {
		switch (tipoNavio) {
		case Navio.BARCO_PATRULHA:
			return (or == Navio.VERTICAL ? imagensNavios.get(5) : imagensNavios
					.get(0));
		case Navio.DESTROIER:
			return (or == Navio.VERTICAL ? imagensNavios.get(6) : imagensNavios
					.get(1));
		case Navio.SUBMARINO:
			return (or == Navio.VERTICAL ? imagensNavios.get(7) : imagensNavios
					.get(2));
		case Navio.ENCOURACADO:
			return (or == Navio.VERTICAL ? imagensNavios.get(8) : imagensNavios
					.get(3));
		case Navio.PORTA_AVIOES:
			return (or == Navio.VERTICAL ? imagensNavios.get(9) : imagensNavios
					.get(4));
		default:
			return null;
		}
	}

	public Image getImagemFogo() throws IOException {
		return getImagem("fogo");
	}

	public Image getImagemAgua() throws IOException {
		return getImagem("agua");

	}

	public Image getImagemMar(int jogador) throws IOException {
		if (jogador == 0)
			return getImagem("mar1");
		else
			return getImagem("mar2");
	}


	private void abreImagensNavios() {
		try {
			imagensNavios = new ArrayList<Image>(); // 5 navios
			String nomeNavios[] = new String[] { "BarcoPatrulha", "Destroier",
					"Submarino", "Encouracado", "PortaAvioes" };
			for (int i = 0; i < 10; i++) {
				imagensNavios.add(i, getImagem((i > 4 ? nomeNavios[i - 5] + "V"
						: nomeNavios[i] + "H")));
			}

		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(0);
		}
	}

	private Image getImagem(String img) throws IOException {
		return ImageIO.read(getClass().getClassLoader().getResource(
				"img/" + img + ".png"));
	}

}
