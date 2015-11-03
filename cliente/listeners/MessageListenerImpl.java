package listeners;

import java.rmi.RemoteException;

import batalhanaval.Controller;
import enuns.Estado;
import telas.TelaPrincipal;

public class MessageListenerImpl implements MessageListener {

	private static final long serialVersionUID = 5085368568273372868L;
	private TelaPrincipal principal;

	public MessageListenerImpl(TelaPrincipal principal) {
		this.principal = principal;
	}

	@Override
	public void message(Estado msg) throws RemoteException {

		switch (msg) {

		case NOVO_TIRO:
			principal.atualizaGrades();
			break;

		case JOGADOR_1:
			principal.atualizaGrades();
			if (Controller.jogadorId == msg) {
				principal.mostraEvento("SUA vez de atacar!");

			} else {
				principal.mostraEvento("Vez do JOGADOR 1 atacar");
			}
			break;

		case JOGADOR_2:
			principal.atualizaGrades();
			if (Controller.jogadorId == msg) {
				principal.mostraEvento("SUA vez de atacar!");

			} else {
				principal.mostraEvento("Vez do JOGADOR 2 atacar");
			}

			break;

		case POSICIONANDO_NAVIOS:
			principal.mostraEvento("É preciso que todos os jogadores posicionem os navios");

			break;

		case JOGO_TERMINADO:
			principal.mostraEvento("JOGO TERMINADO");
			principal.mostraEventos();

			break;

		case JOGO_INICIADO:
			principal.mostraEvento("ATENÇÃO JOGO INICIADO");
			break;
			
		case OPONENTE_DESCONECTADO:
			principal.mostraEvento("ATENÇÃO SEU OPONENTE FOI DESCONECTADO");
			principal.mostraEvento("Reinicie o jogo.");

			break;

		}
	}
	// principal.mostraEvento(message.toString());

}
