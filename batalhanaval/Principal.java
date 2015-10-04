package batalhanaval;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Server.Servidor;
import Server.ServidorImpl;
import telas.TelaPrincipal;

public class Principal {

	public static void main(String[] args) {
		Servidor s = null;
		try {
			for (String temp : Naming.list("//127.0.0.1:2050/Server")) {
				System.out.println(temp);				
			}
			System.out.println(Naming.lookup("//127.0.0.1:2050/Server"));
			//s = (ServidorImpl) Naming.lookup("//127.0.0.1:2050/Server");
			s = (Servidor) Naming.lookup("//127.0.0.1:2050/Server");
			
			TelaPrincipal principal = new TelaPrincipal(s);
			principal.pack();
			principal.setVisible(true);

		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}

	}

}
