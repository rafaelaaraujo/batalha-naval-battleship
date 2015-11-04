package batalhanaval;

import java.awt.Dimension;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Server.Servidor;
import enuns.Estado;
import exception.ErroServidorException;
import listeners.MessageListener;
import listeners.MessageListenerImpl;
import telas.TelaPrincipal;

public class Principal {

	private static Timer timer;
	private static Servidor s = null;
	private static TimerTask tt;
	private static Estado id;
	private static JFrame frame;
	private static String ip;

	public static void main(String[] args) {
		try {

			ip = JOptionPane.showInputDialog("Informe o ip");
			
			if (ip == null || ip.equals("")) {
				showMessage("É preciso que informe o ip para continuar");
			} else {
				inicializarServidor();

				if (Controller.jogadorId == null) {
					showMessage("Já existe um jogo em andamento espere ele terminar");
				} else {

					if (Controller.servidor.oponenteConectado()) {
						iniciaTelaPrincipal();
					} else {
						verificarSeOponenteConectou();
					}
				}
			}

		} catch (Exception e1){
			showMessage("Erro ao conectar no servidor. Verifique o ip e a sua conexao a internet.");
		}

	}

	private static void inicializarServidor() throws Exception, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(ip, 2050);
		s = (Servidor) registry.lookup("Server");
		Controller.servidor = s;
		Controller.jogadorId = s.conectar();
	}

	private static void inicializarListenerService(TelaPrincipal principal) throws Exception {
		MessageListener listener = new MessageListenerImpl(principal);
		UnicastRemoteObject.exportObject(listener, 0);
		Controller.servidor.addMessageListener(listener);
	}


	private static void iniciaTelaPrincipal() throws Exception {
		if (timer != null) {
			timer.cancel();
			tt.cancel();
		}

		if (frame != null) {
			frame.setVisible(false);
		}

		TelaPrincipal principal = new TelaPrincipal();
		principal.pack();
		principal.setVisible(true);
		inicializarListenerService(principal);
	}

	private static void verificarSeOponenteConectou() {
		timer = new Timer();
		tt = new TimerTask() {
			@Override
			public void run() {
				try {
					if (s.oponenteConectado()) {
						iniciaTelaPrincipal();
					} else {
						mostrarAlertaAguardandoOponente();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		};

		timer.schedule(tt, 0, 1000);
	}

	private static void mostrarAlertaAguardandoOponente() {
		if (frame == null) {
			JPanel panel = new JPanel();
			panel.setMinimumSize(new Dimension(200, 200));

			JTextField txt = new JTextField();
			txt.setText("Oponente ainda não conectou");
			txt.setEditable(false);

			panel.add(txt);
			frame = new JFrame("JOptionPane showMessageDialog component example");
			frame.add(panel);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setVisible(true);
		}

	}
	
	private static void showMessage(String mensagem){
		JOptionPane.showMessageDialog(null, mensagem);
	}

}
