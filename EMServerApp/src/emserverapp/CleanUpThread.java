package emserverapp;

import java.sql.SQLException;

public class CleanUpThread extends Thread{
	private ClientCommandProcessor ccp; 
	private Thread[] clients;
	
	public CleanUpThread(ClientCommandProcessor ccp, Thread[] clients)
	{
		
	}
	
	public void killAllClientThreads() {
		for(int i = 0; i < clients.length; i++) {
			try {
				clients[i].interrupt(); 
				clients[i].join();
			} catch (InterruptedException e) {
				System.err.println("CLIENT THREAD " + i + " HAD AN ERROR.");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		System.out.println("\n\n\n"
				+ "************************\n"
				+ "*                      *\n"
				+ "* Recieved kill signal *\n"
				+ "*                      *\n"
				+ "*  CLOSING EM PROGRAM  *\n"
				+ "*                      *\n"
				+ "************************\n");
		
		try {
			ccp.disconnectFromDB();
		} catch (SQLException e) {
			System.err.println("Error with diconnecting from DB");
			e.printStackTrace();
		}
		this.killAllClientThreads();
	}
}
