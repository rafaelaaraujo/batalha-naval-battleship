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
		
			return getNavios(or).get(tipoNavio - 2);

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
			String nomeNavios[] = new String[] { "Corveta", "Fragata",
					"Destroier", "Cruzador", "Submarino", "Encouracado",
					"PortaAvioes" };

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
