package emguiclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientDriver {
	static InetAddress serverHost;
	static int serverPortNumber = 2018;
	static Socket socket;
	static InputStream sIn;
	static DataInputStream socketDIS;
	static OutputStream sOut;
	static DataOutputStream socketDOS;
	static User currentUser;
	static boolean terminate;
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		try {
			serverHost = InetAddress.getByName("em.racereg.run");
			socket = new Socket(serverHost, serverPortNumber);
			sIn = socket.getInputStream();
			socketDIS = new DataInputStream(sIn);
			sOut = socket.getOutputStream();
			socketDOS = new DataOutputStream(sOut);
			terminate = false;
			
			
			System.out.println("CONNECTED TO SERVER\n\n");
			
			while(!terminate) {
				//Get message from server
				String message = socketDIS.readUTF();
				
				//Process server command
				processServerCommand(message);
				
				System.out.print("\nEnter your response to the server: (see command EM Server App Protocol Syntax) ");
				String toSendToServer = input.nextLine();
				
				socketDOS.writeUTF(toSendToServer);
			}
			
			
			//System.out.println("MESSAGE FROM SERVER: " + message);
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			socketDOS.close();
			sOut.close();
			socketDIS.close();
			sIn.close();
			socket.close();
			
			
			
			System.out.println("\n\n%%% CLOSING EM CLIENT APP %%%");
			System.exit(0);
			
		} catch (UnknownHostException e) {
			System.err.println("Server not recongized - couldn't connect.");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't connect OR I/O Error");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void processServerCommand(String command) {
		//Command processor:
		String[] commandSplit = command.split(";");
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
						//Board message posting (post a message to the client)
						case "b":
							if(commandSplit[1].equals("s")) {
								System.out.println("Message from Server: " + subCommandSplit[1]);
							}
							else if(commandSplit[1].equals("f")) {
								System.out.println("FAIL Message from Server: " + subCommandSplit[1] + "\n\nClosing program!");
								System.exit(1);
							}
							else {
								System.err.println("Incorrect command syntax (s/f). Closing program.");
								System.exit(1);
							}
							break;
						//Login
						case "i":
							if(commandSplit[1].equals("s")) {
								try {
									currentUser.updateUserInfo(subCommandSplit);
									System.out.println("Logged in user - retrieved user information.");
								} catch (UserOperationException e) {
									e.printStackTrace();
									System.err.println(e.getMessage());
									System.exit(1);
								}
							}
							else if(commandSplit[1].equals("f")) {
								System.err.println("Error for: " + subCommandSplit[1] + ": " + subCommandSplit[2]);
							}
							else {
								System.err.println("Incorrect command syntax (s/f). Closing program.");
								System.exit(1);
							}
							break;
						//Create login
						case "m":
							if(commandSplit[1].equals("s")) {
								currentUser = new User(subCommandSplit[1], subCommandSplit[2], 
										subCommandSplit[3], subCommandSplit[4], 
										subCommandSplit[5], Boolean.parseBoolean(subCommandSplit[6]), 
										Boolean.parseBoolean(subCommandSplit[7]), Boolean.parseBoolean(subCommandSplit[8]),
										Boolean.parseBoolean(subCommandSplit[9]), Boolean.parseBoolean(subCommandSplit[10]),
										Boolean.parseBoolean(subCommandSplit[11]));
								System.out.println("Created user/logged in: " + currentUser.getUsername());
							}
							else if(commandSplit[1].equals("f")) {
								System.err.println("Error : " + subCommandSplit[1]);
							}
							else {
								System.err.println("Incorrect command syntax (s/f). Closing program.");
								System.exit(1);
							}
							break;
						//Update user details (new on server)
						case "o":
							if(commandSplit[1].equals("s")) {
								try {
									currentUser.updateUserInfo(subCommandSplit);
								} catch (UserOperationException e) {
									e.printStackTrace();
									System.err.println(e.getMessage());
									System.exit(1);
								}
								System.out.println("Retrieved updated user information.");
							}
							else if(commandSplit[1].equals("f")) {
								System.err.println("Error updating user: " + subCommandSplit[1] + ": " + subCommandSplit[2]);
							}
							else {
								System.err.println("Incorrect command syntax (s/f). Closing program.");
								System.exit(1);
							}
							break;
						case "j":
							if(commandSplit[1].equals("s")) {
								System.out.println("Changed password for user: " + subCommandSplit[1]);
							}
							else if(commandSplit[1].equals("f")) {
								System.err.println("Error changing password for: " + subCommandSplit[1] + ": " + subCommandSplit[2]);
							}
							else {
								System.err.println("Incorrect command syntax (s/f). Closing program.");
								System.exit(1);
							}
							break;
						case "f":
							if(commandSplit[1].equals("s")) {
								System.out.println("Changed password for user (brute force): " + subCommandSplit[1]);
							}
							else if(commandSplit[1].equals("f")) {
								System.err.println("Error changing password for (brute force): " + subCommandSplit[1] + ": " + subCommandSplit[2]);
							}
							else {
								System.err.println("Incorrect command syntax (s/f). Closing program.");
								System.exit(1);
							}
							break;
						case "d":
							if(commandSplit[1].equals("s")) {
								System.out.println("DISABLED user: " + subCommandSplit[1]);
							}
							else if(commandSplit[1].equals("f")) {
								System.err.println("Error disabling user: " + subCommandSplit[1] + ": " + subCommandSplit[2]);
							}
							else {
								System.err.println("Incorrect command syntax (s/f). Closing program.");
								System.exit(1);
							}
							break;
						default:
							System.err.println("Unknown component subcommand. Closing program.");
							System.exit(1);
					}
				default:
					System.err.println("Unknown component. Closing program.");
					System.exit(1);
			}
		}
	}
	
	
}
