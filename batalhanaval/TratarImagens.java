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
	private ArrayList<Image> imagensNaviosVertical = new ArrayList<Image>();
	private ArrayList<Image> imagensNaviosHorizonal = new ArrayList<Image>();
	private ArrayList<Image> imagensNaviosDiagonal = new ArrayList<Image>();

	public TratarImagens() {
		abreImagensNavios();
	}

	public Image getImagemNavio(int tipoNavio, OrientacaoNavio or) {
		switch (tipoNavio) {
		case 2:
			return getNavios(or).get(0);
		case 4:
			return getNavios(or).get(1);
		case 8:
			return getNavios(or).get(2);
		case 16:
			return getNavios(or).get(3);
		case 32:
			return getNavios(or).get(4);
		case 64:
			return getNavios(or).get(5);
		case 128:
			return getNavios(or).get(6);
		default:
			return null;
		}
	}

	private ArrayList<Image> getNavios(OrientacaoNavio on) {
		switch (on) {
		case VERTICAL:
			return imagensNaviosVertical;

		case HORIZONTAL:
			return imagensNaviosHorizonal;

		case DIAGONAL:
			return imagensNaviosDiagonal;
		default:
			return imagensNaviosHorizonal;
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
			String nomeNavios[] = new String[] { "Corveta", "Cruzador",
					"Destroier", "Encouracado", "Fragata", "PortaAvioes",
					"Submarino" };

			imagensNaviosHorizonal = new ArrayList<Image>();
			for (int i = 0; i < nomeNavios.length; i++) {
				imagensNaviosVertical.add(i, getImagem(nomeNavios[i] + "V"));
				imagensNaviosHorizonal.add(i, getImagem(nomeNavios[i] + "H"));
				imagensNaviosDiagonal.add(i, getImagem(nomeNavios[i] + "D"));

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
