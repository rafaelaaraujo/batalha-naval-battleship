package eventos;

import java.io.Serializable;

public class Evento implements Serializable {

	private static final long serialVersionUID = 742010242566568034L;
	private String mensagem;

	public Evento(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return this.mensagem;
	}
}
