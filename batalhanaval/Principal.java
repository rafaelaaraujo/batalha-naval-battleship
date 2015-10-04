package batalhanaval;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Server.Servidor;
import Server.ServidorImpl;
import telas.TelaPrincipal;

public class Principal {

	public static void main(String[] args) {
		Servidor s = null;
		try {			
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2050);
			s = (Servidor) registry.lookup("Server");

			TelaPrincipal principal = new TelaPrincipal(s);
			principal.pack();
			principal.setVisible(true);

		} catch (RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}

	}

}
