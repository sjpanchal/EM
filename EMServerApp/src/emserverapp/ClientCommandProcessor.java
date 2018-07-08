package emserverapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import emserverapp.User;
import emserverapp.UserOperationException;

public class ClientCommandProcessor implements Runnable{
	static final int APP_SERVER_PORT = 2018;
	/*static final ServerSocket generalServerSocket;*/
	static final SSLServerSocket generalSSLServerSocket;
	
	//Database
	static String host = "jacksonportermysqldb.ck9qmzz9yyn6.us-west-2.rds.amazonaws.com";
	static String user = "emserverapp";
	static String password = "IluvEM2018";
	//SQL Variables
	static Connection conn;
		
	/*static {
		ServerSocket temp = null;
		try {
			temp = new ServerSocket(ClientCommandProcessor.APP_SERVER_PORT);
		}catch(IOException e) {
			System.err.println("Static variable error on ServerSocket:generalServerSocket");
			System.exit(1);
		}
		generalServerSocket = temp;
	}*/
	
	static {
		SSLServerSocket temp = null;
		try {
			SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			temp = (SSLServerSocket) sslServerSocketFactory.createServerSocket(ClientCommandProcessor.APP_SERVER_PORT);
		}catch(IOException e) {
			System.err.println("Static variable error on SSLServerSocket:generalSSLServerSocket");
			System.exit(1);
		}
		generalSSLServerSocket = temp;
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
	
	//Thread method
	@Override
	public void run() {
		System.out.println("new thread spawned");
		ClientThread thisThread = (ClientThread) Thread.currentThread();
		ClientConnection clientConnection;
		Boolean terminate;
		
		//Create a new connection!
		try {
			clientConnection = thisThread.getClientConnection();
			clientConnection.writeToClient("g;s;b?Please login to continue.");
			terminate = false;
			
			while(!terminate) {
				String message = clientConnection.readFromClient();
				
				clientConnection.writeToClient(processClientRequest(message));
			}
			
			clientConnection.closeConnection();
			
		} catch (IOException e) {
			System.err.println("Error when connecting/talking to client. Connection closed.");
			terminate = true;
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error with Database. Connection closed.");
			terminate = true;
			e.printStackTrace();
		} catch (MultipleUsersException e) {
			terminate = true;
			e.printStackTrace();
		}
	}


	private String processClientRequest(String message) throws SQLException, MultipleUsersException {
		System.err.println("ATTEMPTING TO PROCESS THE REQUST: " + message);
		
		//DB variables
		Statement statement;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		User currentUser = null;
		
		
		
		
		//Command processor:
		String[] commandSplit = message.split(";");
		if(commandSplit.length < 3) {
			System.err.println("Message from server was invalid. Closing program.");
			System.exit(1);
		}
		else {
			switch (commandSplit[0]) {
				case "g":
					System.err.println("General operation selected.");
					
					String[] subCommandSplit = commandSplit[2].split("\\?");
					
					switch(subCommandSplit[0]) {
						//Login user
						case "i":
							//Check to see if user can be logged in
							preparedStatement = conn.prepareStatement("SELECT emdb.`User`.username, emdb.`User`.password, emdb.`User`.firstname,"
									+ " emdb.`User`.lastname, emdb.`User`.email, emdb.`User`.phonenumber, emdb.`User`.isAdmin,"
									+ " emdb.`User`.isManager, emdb.`User`.isEmployee, emdb.`User`.isOutsideManager,"
									+ " emdb.`User`.isGroupContact,	emdb.`User`.isOutsideVendor FROM emdb.`User`"
									+ " WHERE username=? AND active = 1;");
							preparedStatement.setString(1, subCommandSplit[1]);
							System.err.println("ABOUT TO QUERY: " + preparedStatement.toString());
							resultSet = preparedStatement.executeQuery();
							System.err.println("GOT RESULTS");
							
							if(resultSet.getRow() < 1) {
								//return "g;f;" + subCommandSplit[0] + "?" + subCommandSplit[1] + "?Couldn't login user, USER DOESN'T EXCIST or IS INACTIVE";
							}
							else if(resultSet.getRow() > 1) {
								throw new MultipleUsersException("There are multiple users with this username that are active. We have a problem.");								
							}
							
							resultSet.next();
							System.out.println("PASS TO COMPARE" + resultSet.getString(2));
							
							if(!(subCommandSplit[2].equals(resultSet.getString(2)))) {
								return "g;f;" + subCommandSplit[0] + "?" + subCommandSplit[1] + "?Couldn't login user, USER PASSWORD INCORRECT";
							}
							else {
								currentUser = new User(resultSet.getString(1), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), 
										resultSet.getString(6), Boolean.parseBoolean(resultSet.getString(7)), Boolean.parseBoolean(resultSet.getString(8)),
										Boolean.parseBoolean(resultSet.getString(9)), Boolean.parseBoolean(resultSet.getString(10)), 
										Boolean.parseBoolean(resultSet.getString(11)), Boolean.parseBoolean(resultSet.getString(12)));
								
								return "g;s;" + subCommandSplit[0] + currentUser.userInfo();
							}
							
							
							/*if(commandSplit[1].equals("u")) {
								return "g:f:" + subCommandSplit[0] + "?" + subCommandSplit[1] + "?Couldn't login user, USER DOESN'T EXCIST";
							}
							else if(commandSplit[1].equals("f")) {
								System.out.println("FAIL Message from Server: " + subCommandSplit[1] + "\n\nClosing program!");
								System.exit(1);
							}
							else {
								System.err.println("Incorrect command syntax (s/f). Closing program.");
								System.exit(1);
							}*/		
						
						case "m":
							
						case "o":
							
						case "j":
							
						case "f":
							
						case "d":
							
						default:
							System.err.println("Unknown component subcommand. Closing program.");
							return "g;f;b?Subcommand not supported.";
					}
				default:
					System.err.println("Unknown component. Closing program.");
					return "g;f;b?Component not supported.";
			}
		}
		return "g:f:b?A serious error occured.";
	}

	public void disconnectFromDB() throws SQLException {
		conn.close();		
	}
}
