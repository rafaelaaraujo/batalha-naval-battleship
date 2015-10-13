package listeners;

import java.rmi.RemoteException;

public class MessageListenerImpl implements MessageListener {

	private static final long serialVersionUID = 5085368568273372868L;

	@Override
	public void message(String message) throws RemoteException {
		System.out.println(message);
	}

}
