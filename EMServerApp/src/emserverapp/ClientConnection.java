package emserverapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnection {
	ServerSocket generalServerSocket;
	Socket generalSocket;
	OutputStream generalSOut;
	DataOutputStream generalSocketDOS;
	InputStream generalSIn;
	DataInputStream generalSocketDIS;
	String name;
	
	public ClientConnection() throws IOException {
		generalServerSocket = ClientCommandProcessor.generalServerSocket;
		generalSocket = generalServerSocket.accept();
		generalSOut = generalSocket.getOutputStream();
		generalSocketDOS = new DataOutputStream(generalSOut);
		generalSIn = generalSocket.getInputStream();
		generalSocketDIS = new DataInputStream(generalSIn);
		
		System.out.println("Client connected: " + generalSocket.getInetAddress().toString());		
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
		this.generalSocket.close();
		this.generalServerSocket.close();
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
}
