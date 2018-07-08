package emserverapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class ClientConnection {
	/*ServerSocket generalServerSocket;*/
	SSLServerSocket generalSSLServerSocket;
	/*Socket generalSocket;*/
	SSLSocket generalSSLSocket;
	OutputStream generalSOut;
	DataOutputStream generalSocketDOS;
	InputStream generalSIn;
	DataInputStream generalSocketDIS;
	String name;
	
	public ClientConnection() throws IOException {
		System.err.println("Trying to create new connection...");
		/*generalServerSocket = ClientCommandProcessor.generalServerSocket;*/
		generalSSLServerSocket = ClientCommandProcessor.generalSSLServerSocket;
		generalSSLSocket = (SSLSocket) generalSSLServerSocket.accept();
		generalSOut = generalSSLSocket.getOutputStream();
		generalSocketDOS = new DataOutputStream(generalSOut);
		generalSIn = generalSSLSocket.getInputStream();
		generalSocketDIS = new DataInputStream(generalSIn);
		
		System.out.println("Client connected: " + generalSSLSocket.getInetAddress().toString());		
	}
	
	public void writeToClient(String message) throws IOException {
		generalSocketDOS.writeUTF(message);
	}
	
	public String readFromClient() throws IOException {
		return generalSocketDIS.readUTF();
	}

	public void closeConnection() throws IOException {
		this.generalSocketDIS.close();
		this.generalSocketDOS.close();
		this.generalSIn.close();;
		this.generalSOut.close();
		this.generalSSLSocket.close();
		this.generalSSLServerSocket.close();
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
}
