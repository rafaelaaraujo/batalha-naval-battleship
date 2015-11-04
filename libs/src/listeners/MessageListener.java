package listeners;

import java.io.Serializable;
import java.rmi.Remote;
import enuns.Estado;
import exception.ErroServidorException;

public interface MessageListener extends Serializable, Remote {

	public void message(Estado msg) throws ErroServidorException;

}
