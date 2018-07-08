package emserverapp;

import java.util.Scanner;

import java.security.Security;
import com.sun.net.ssl.internal.ssl.Provider;

public class ServerDriver {
	static Scanner scanner;
	
	
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
								try {
									System.out.print("Enter jks location (full path): ");
									String location = scanner.nextLine();
									startNetworkProgram(location);
									System.out.println("Starting network program. If you selected other arguments, they will not be excuted until the network program is terminated.");
								} catch (InterruptedException e) {
									System.err.println("The network thread had a problem. Closing EM Server App.");
									System.exit(1);
								}
								break;
							case "u":
								System.out.println("USAGE: "
										+ "java -jar EMServerApp.jar -<arguments>\n\n"
										+ "ARGUMENTS:\n"
										+ "(n) Start Network Program\n"
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
						+ "\n(u) Display command line usage"
						+ "\n(q) QUIT server application"
						+ "\nOPTION: ");
				
				input = scanner.nextLine();
				
				switch (input) {
					case "n":
						try {
							System.out.print("Enter jks location (full path): ");
							String location = scanner.nextLine();
							startNetworkProgram(location);
							System.out.println("Starting network program...");
						} catch (InterruptedException e) {
							System.err.println("The network thread had a problem. Closing EM Server App.");
							System.exit(1);
						}
						break;
					case "u":
						System.out.println("USAGE: "
								+ "java -jar EMServerApp.jar -<arguments>\n\n"
								+ "ARGUMENTS:\n"
								+ "(n) Start Network Program\n"
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
	

/*	public static void getAllGroupInformation() {
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
			
		} catch (ClassNotFoundException e) {
			System.err.println("Couldn't detect/find driver.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Couldn't make connection or SQL query/command error.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	*/
	
	
	private static void startNetworkProgram(String jksLocation) throws InterruptedException {
		//Prep system to use keystore/ssl
		//Adds the Java Secure Socket Extension (JSSE) provider - provides SSL and TLS protocols with functionality for data encrpytion, server auth, message integrity, and client auth.
        Security.addProvider(new Provider());

        System.setProperty("javax.net.ssl.keyStore", jksLocation);
        System.setProperty("javax.net.ssl.keyStorePassword", "18emapps**1997");
		
		ClientCommandProcessor ccp = new ClientCommandProcessor();

		//Add support for multiple clients in the future.
		
		//Connect a single client
		ClientThread client1 = new ClientThread(ccp);
		client1.start();
		
		client1.join();
	}
	
	
}
