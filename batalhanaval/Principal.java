package batalhanaval;

import java.awt.Dialog;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import Server.Servidor;
import telas.TelaPrincipal;

public class Principal {

	private static Timer timer;
	private static Servidor s = null;
	private static JDialog dialog = null;
	private static TimerTask tt;
	private static Jogador jogador;

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2050);
			s = (Servidor) registry.lookup("Server");

			jogador = s.conectar();

			if (jogador == null) {
				JOptionPane.showMessageDialog(null,"Já existe um jogo em andamento espere ele terminar");
			} else {

				if (s.oponenteConectado()) {
					iniciaTelaPrincipal();
				} else {
					verificarSeOponenteConectou();
				}
			}

		} catch (RemoteException | NotBoundException e1) {
			e1.printStackTrace();
			denconectar();
		}

	}

	private static void denconectar() {
		try {
			s.desconectar(jogador);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	private static void iniciaTelaPrincipal() {
		if (timer != null) {
			timer.cancel();
			tt.cancel();
		}

		TelaPrincipal principal = new TelaPrincipal(s, jogador);
		principal.pack();
		principal.setVisible(true);
	}

	private static void verificarSeOponenteConectou() {
		timer = new Timer();
		tt = new TimerTask() {
			@Override
			public void run() {
				try {
					if (s.oponenteConectado()) {
						closeDialog();
						iniciaTelaPrincipal();
					} else {
						mostrarAlertaAguardandoOponente();
					}

				} catch (RemoteException e) {
					e.printStackTrace();
				}
			};
		};

		timer.schedule(tt, 0, 1000);
	}

	private static void mostrarAlertaAguardandoOponente() {
		final JOptionPane optionPane = new JOptionPane(
				"Oponente não conectado", JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);

		dialog = new JDialog();
		dialog.setTitle("Message");
		dialog.setModal(true);

		dialog.setContentPane(optionPane);

		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		dialog.pack();
		dialog.show();
	}

	private static void closeDialog() {
		dialog.dispose();
	}
}
