package client.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import client.App;
import client.Util;

public class Connector {
	private final static boolean DEBUG = true;
	private final static int port = 50000;
	private final static String address = "127.0.0.1";
	private static PrintWriter output;
	private static Socket sock;

	public static boolean Send(String str) {
		try {
			output.println(str);
		} catch (NullPointerException e) {
			App.app.alert("Cannot connect to server");
		}

		if (DEBUG)
			Util.timedLog("Sending: " + str);
		
		return true;
	}

	public static void start() throws IOException {
		try {
			sock = new Socket(InetAddress.getByName(address), port);
		} catch (java.net.ConnectException e){
			App.app.alert("Ther server is currently offline");
			System.exit(0);
		} 
		output = new PrintWriter(sock.getOutputStream(), true);
		
		if (DEBUG)
			Util.timedLog("Client: opening socket to " + address + " on port " + port);

		ClientThread ct = new ClientThread(sock);
		ct.start();
		Util.timedLog("Client, made connection...");
		
		Send("Hello Server, this is AOF.");
	}
}
