package batalhanaval;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Server.Servidor;
import Server.ServidorImpl;
import telas.TelaPrincipal;



public class Principal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Servidor s = null;
		try {
			s = (Servidor) Naming.lookup("Servidor");			
			TelaPrincipal principal = new TelaPrincipal(s);
			principal.pack();
			principal.setVisible(true);

		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}

	}
}
