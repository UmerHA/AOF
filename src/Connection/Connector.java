package Connection;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import MainPackage.MainApplet;

public class Connector {
	private final static boolean DEBUG = true;
	private final static int BUFFSIZE = 128000;
	private final static int port = 50000;
	private final static String address = "127.0.0.1";
	private static BufferedOutputStream output;
	private static Socket sock;

	// string schicken (socket)
	public static boolean Send(String str) {

		/* convertieren string -> array of bytes */

		ByteArrayOutputStream bytestream;
		bytestream = new ByteArrayOutputStream(str.length());

		DataOutputStream out;
		out = new DataOutputStream(bytestream);

		try {
			for (int i = 0; i < str.length(); i++)
				out.write((byte) str.charAt(i));
			
			output.write(bytestream.toByteArray(), 0, bytestream.size());
			output.flush();
		} catch (IOException e) {
			System.err.println("Connector.Send :: IOException : "+e.toString());
		} catch (NullPointerException e) {
			MainApplet.applet.alert("Cannot connect to server");
		}

		if (DEBUG)
			System.out.println("Sending :" + str);
		
		return true;

	}

	public static void start() throws IOException {
		try {
			sock = new Socket(InetAddress.getByName(address), port);
		} catch (java.net.ConnectException e){
			MainApplet.applet.alert("Ther server is currently offline");
			System.exit(0);
		} 
		output = new BufferedOutputStream(sock.getOutputStream(), BUFFSIZE);

		if (DEBUG)
			System.out.println("Client: opening socket to " + address
					+ " on port " + port);

		ClientThread ct = new ClientThread(sock);
		ct.start();
		System.out.println("Client, made connection...");
	}
}
