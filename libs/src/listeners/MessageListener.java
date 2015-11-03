package listeners;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import enuns.Estado;

public interface MessageListener extends Serializable, Remote {

	public void message(Estado msg) throws RemoteException;

}
