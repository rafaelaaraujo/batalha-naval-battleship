package batalhanaval;

import java.awt.Dimension;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import telas.TelaPrincipal;
import Server.Servidor;

public class Principal {

	private static Timer timer;
	private static Servidor s = null;
	private static TimerTask tt;
	private static Jogador jogador;
	private static JFrame frame;

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2050);
			s = (Servidor) registry.lookup("Server");

			jogador = s.conectar();

			if (jogador == null) {
				JOptionPane.showMessageDialog(null,
						"Já existe um jogo em andamento espere ele terminar");
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

		if (frame != null) {
			frame.setVisible(false);
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
		if (frame == null) {
			JPanel panel = new JPanel();
			panel.setMinimumSize(new Dimension(200, 200));

			JTextField txt = new JTextField();
			txt.setText("Oponente ainda não conectou");
			txt.setEditable(false);

			panel.add(txt);
			frame = new JFrame(
					"JOptionPane showMessageDialog component example");
			frame.add(panel);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setVisible(true);
		}

	}

}
