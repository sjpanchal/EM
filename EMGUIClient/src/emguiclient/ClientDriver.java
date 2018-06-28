package emguiclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientDriver {
	static InetAddress serverHost;
	static int serverPortNumber = 2018;
	static Socket socket;
	static InputStream sIn;
	static DataInputStream socketDIS;
	static OutputStream sOut;
	static DataOutputStream socketDOS;
	
	public static void main(String[] args) {
		try {
			serverHost = InetAddress.getByName("ec2-54-244-160-77.us-west-2.compute.amazonaws.com");
			socket = new Socket(serverHost, serverPortNumber);
			sIn = socket.getInputStream();
			socketDIS = new DataInputStream(sIn);
			sOut = socket.getOutputStream();
			socketDOS = new DataOutputStream(sOut);
			
			System.out.println("CONNECTED TO SERVER\n\n");
			
			String message = socketDIS.readUTF();
			System.out.println("MESSAGE FROM SERVER: " + message);
			
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
	
	
	
}
