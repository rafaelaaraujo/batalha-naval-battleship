package batalhanaval;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import navios.Navio;
import enuns.OrientacaoNavio;
import enuns.TipoNavio;

public class TratarImagens {
	// Imagens
	private ArrayList<Image> imagensNaviosVertical;
	private ArrayList<Image> imagensNaviosHorizonal;

	public TratarImagens() {
		abreImagensNavios();
	}

	public Image getImagemNavio(int tipoNavio, OrientacaoNavio or) {
		switch (tipoNavio) {
		case 2:
			return (or == OrientacaoNavio.VERTICAL ? imagensNaviosVertical.get(0)
					: imagensNaviosHorizonal.get(0));
		case 4:
			return (or == OrientacaoNavio.VERTICAL ? imagensNaviosVertical.get(1)
					: imagensNaviosHorizonal.get(1));
		case 8:
			return (or == OrientacaoNavio.VERTICAL ? imagensNaviosVertical.get(2)
					: imagensNaviosHorizonal.get(2));
		case 16:
			return (or == OrientacaoNavio.VERTICAL ? imagensNaviosVertical.get(3)
					: imagensNaviosHorizonal.get(3));
		case 32:
			return (or == OrientacaoNavio.VERTICAL ? imagensNaviosVertical.get(4)
					: imagensNaviosHorizonal.get(4));
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
			imagensNaviosVertical = new ArrayList<Image>();
			String nomeNavios[] = new String[] { "BarcoPatrulha", "Destroier",
					"Submarino", "Encouracado", "PortaAvioes" };

			imagensNaviosHorizonal = new ArrayList<Image>();
			for (int i = 0; i < 5; i++) {
				imagensNaviosVertical.add(i, getImagem(nomeNavios[i] + "V"));
				imagensNaviosHorizonal.add(i, getImagem(nomeNavios[i] + "H"));
			}

		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

	private Image getImagem(String img) throws IOException {
		return ImageIO.read(getClass().getClassLoader().getResource(
				"img/" + img + ".png"));
	}

}
