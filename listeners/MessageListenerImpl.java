package listeners;

import java.rmi.RemoteException;

import enuns.Estado;
import telas.TelaPrincipal;

public class MessageListenerImpl implements MessageListener {

	private static final long serialVersionUID = 5085368568273372868L;
	private TelaPrincipal principal;

	public MessageListenerImpl(TelaPrincipal principal) {
		this.principal = principal;
	}

	@Override
	public void message(String msg) throws RemoteException {
		if(msg.equals("TIRO")){
			principal.atualizaGrades();
		}else{
	switch (msg) {
		case "JOGADOR_1":
			principal.atualizaGrades();
			principal.mostraEvento("Vez do JOGADOR 1 atacar");
			break;

		case "JOGADOR_2":
			principal.atualizaGrades();
			principal.mostraEvento("Vez do JOGADOR 2 atacar");

			break;
			
		case "POSICIONANDO_NAVIOS":
			principal.mostraEvento("É preciso que todos os jogadores posicionem os navios");

			break;
			
		case "TERMINADO":
			principal.mostraEvento("JOGO TERMINADO");


			break;
		}
		}
		//principal.mostraEvento(message.toString());
	}

}
