package emserverapp;

import java.io.IOException;

public class ClientThread extends Thread {
	private ClientConnection clientConnection;

	public ClientThread(ClientCommandProcessor ccp) {
		super(ccp);
	}

	@Override
	public void interrupt() {
		try {
			try {
				clientConnection.closeConnection();
			} catch (IOException e) {
				System.err.println("Couldn't close connection/error with connection! Interrupting anyway.");
				e.printStackTrace();
			}
		}
		finally {
			super.interrupt();
		}
	}


	public ClientConnection getClientConnection() throws IOException {
		System.err.println("REQUESTING CONNECTION!*!");
		if(clientConnection != null) {
			clientConnection = new ClientConnection();
			return clientConnection;
		}
		else {
			return clientConnection;
		}
	}
}
