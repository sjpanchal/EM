package emserverapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerDriver {
	static Scanner scanner;
	
	//SQL Variables
	static Connection conn;
	static Statement statement;
	static PreparedStatement preparedStatement;
	static ResultSet resultSet;
	
	//Database variables
	static String host = "jacksonportermysqldb.ck9qmzz9yyn6.us-west-2.rds.amazonaws.com";
	static String user = "emserverapp";
	static String password = "IluvEM2018";
	
	//Networking (TCP) variables
	static int socketNumber = 2018;
	static ServerSocket serverSocket;
	static Socket socket;
	static InputStream sIn;
	static DataInputStream socketDIS;
	static OutputStream sOut;
	static DataOutputStream socketDOS;
	
	public static void main(String[] args) {		
		//Check to see if there are arguments!
		if(args.length > 0) {
			boolean error = false;
			
			for(int i = 0; i < args.length; i++) {
				if(args[i].charAt(0) != '-') {
					error = true;
				}
			}
			
			if(error) {
				System.err.println("Woops! You must start your argument lists with a '-'");
				System.exit(1);
			}
			else {
				for(int i = 0; i < args.length; i++) {
					for(int k = 1; k < args[i].length(); k++) {
						String input = "" + args[i].charAt(k);
							
						switch (input) {
							case "n":
								startNetworkProgram();
								break;
							case "g":
								getAllGroupInformation();
								break;
							case "u":
								System.out.println("USAGE: "
										+ "java -jar EMServerApp.jar -<arguments>\n\n"
										+ "ARGUMENTS:\n"
										+ "(n) Start Network Program\n"
										+ "(g) Retrieve ALL Group Information\n"
										+ "(u) Get usage\n"
										+ "\nArguments for EM Server App are issued in sequential order.\n");
								break;
							default:
								System.out.println("\n**INPUT NOT RECONGNIZED. TRY AGAIN.**");
						}
					}
				}
			}
		}
		//Run interactive program
		else {
			System.out.println("EM Server Application Starting...");
			
			//Get variables ready
			scanner = new Scanner(System.in);
			String input = "";
			
			//Start menu
			while(!input.equals("q")) {
				System.out.print("\nWelcome to the EM Server Application. Choose an option: "
						+ "\n(n) Start Network Program"
						+ "\n(g) Display ALL group information"
						+ "\n(u) Display command line usage"
						+ "\n(q) QUIT server application"
						+ "\nOPTION: ");
				
				input = scanner.nextLine();
				
				switch (input) {
					case "n":
						startNetworkProgram();
						break;
					case "g":
						getAllGroupInformation();
						break;
					case "u":
						System.out.println("USAGE: "
								+ "java -jar EMServerApp.jar -<arguments>\n\n"
								+ "ARGUMENTS:\n"
								+ "(n) Start Network Program\n"
								+ "(g) Retrieve ALL Group Information\n"
								+ "(u) Get usage\n"
								+ "\nArguments for EM Server App are issued in sequential order.\n");
						break;
					case "q":
						break;
					default:
						System.out.println("\n**INPUT NOT RECONGNIZED. TRY AGAIN.**");
				}
				
			}
		}	
		
		System.out.println("\n\n%%% CLOSING EM SERVER APP %%%");
		System.exit(0);	
	}
	

	public static void getAllGroupInformation() {
		System.out.println("CONNECTING TO DB");
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://" + host + "/emdb", user, password);
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM emdb.`Group`;");
			
			ArrayList<Group> groups = new ArrayList<Group>();
			
			while(resultSet.next()) {
				
				int id = resultSet.getInt(1);
				int numtotal = resultSet.getInt(2);
				int nummale = resultSet.getInt(3);
				int numfemale = resultSet.getInt(4);
				String name = resultSet.getString(5);
				String notes = resultSet.getString(6);
				java.sql.Date sqlStartDate = resultSet.getDate(7);
				java.sql.Date sqlEndDate = resultSet.getDate(8);
				
				Group group = new Group(id, numtotal, nummale, numfemale, name, notes, sqlStartDate, sqlEndDate);
				groups.add(group);
			}
			
			System.out.println("GROUP RESULTS:\n");
			
			for(int i = 0; i < groups.size(); i++) {
				System.out.println(groups.get(i).toString());
			}
			
		/*} catch (ClassNotFoundException e) {
			System.err.println("Couldn't detect/find driver.");
			e.printStackTrace();*/
		} catch (SQLException e) {
			System.out.println("Couldn't make connection or SQL query/command error.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static void startNetworkProgram() {
		try {
			//Database connection
			System.out.println("CONNECTING TO DB");
			conn = DriverManager.getConnection("jdbc:mysql://" + host + "/emdb", user, password);
			
			//Client connection
			System.out.println("Waiting for connection");
			
			serverSocket = new ServerSocket(socketNumber);
			socket = serverSocket.accept();
			
			//Input
			sIn = socket.getInputStream();
			socketDIS = new DataInputStream(sIn);
			
			//Output
			sOut = socket.getOutputStream();
			socketDOS = new DataOutputStream(sOut);
			
			System.out.println("CONNECTION RECIEVED");
			
			socketDOS.writeUTF("Sorry, the network app server is in development. Check back later.");
			
			socketDOS.close();
			sOut.close();
			socketDIS.close();
			sIn.close();
			socket.close();
			serverSocket.close();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("Couldn't make connection or SQL query/command error.");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("I/O or Networking Error.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
}