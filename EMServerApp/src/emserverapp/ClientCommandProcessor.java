package emserverapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientCommandProcessor implements Runnable{
	static final int APP_SERVER_PORT = 2018;
	static final ServerSocket generalServerSocket;
	
	//Database
	static String host = "jacksonportermysqldb.ck9qmzz9yyn6.us-west-2.rds.amazonaws.com";
	static String user = "emserverapp";
	static String password = "IluvEM2018";
	//SQL Variables
	static Connection conn;
	static Statement statement;
	static PreparedStatement preparedStatement;
	static ResultSet resultSet;
		
	static {
		ServerSocket temp = null;
		try {
			temp = new ServerSocket(ClientCommandProcessor.APP_SERVER_PORT);
		}catch(IOException e) {
			System.out.println("Static variable error on ServerSocket:generalServerSocket");
		}
		generalServerSocket = temp;
	}
	
	public ClientCommandProcessor() {
		//Connect to Database
		System.out.println("CONNECTING TO DB");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + host + "/emdb", user, password);
		} catch (SQLException e) {
			System.err.println("APP SERVER CLOSING. COULDN'T CONNECT TO DATABSE.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	@Override
	public void run() {
		//Create a new connection!
		try {
			ClientConnection clientConnection = new ClientConnection();
			clientConnection.writeToClient("g;b;Please login to continue.");
			clientConnection.writeToClient("g;b;Development is under process, thanks for waiting. Closing connection.");
			System.out.println("Development is stopped here. Closing connection");
			clientConnection.closeConnection();
			
		} catch (IOException e) {
			System.err.println("Error when connecting/talking to client. Connection closed.");
			e.printStackTrace();
		}
	}
}
