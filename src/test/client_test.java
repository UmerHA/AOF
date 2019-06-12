// Simple test of the Java client class für Umer damit ers versteht ^^

package test;

import java.io.*;
import java.net.*;

import connection.ClientThread;

public class client_test {

	static boolean DEBUG = true;
	static int BUFFSIZE = 128000;
	static int port = 50000;
	static String address = "188.97.69.130";
	static BufferedOutputStream output;

	// string schicken (socket)
	public static void Send(String str) throws IOException {

		/* convertieren string -> array of bytes */

		ByteArrayOutputStream bytestream;
		bytestream = new ByteArrayOutputStream(str.length());

		DataOutputStream out;
		out = new DataOutputStream(bytestream);

		for (int i = 0; i < str.length(); i++)
			out.write((byte) str.charAt(i));

		output.write(bytestream.toByteArray(), 0, bytestream.size());
		output.flush();

		System.out.println("Client: sending '" + str + "'");

	}

	public static void main(String args[]) throws IOException {

		boolean quit = false;
		Socket sock;

		sock = new Socket(InetAddress.getByName(client_test.address), port);
		output = new BufferedOutputStream(sock.getOutputStream(), BUFFSIZE);

		if (DEBUG)
			System.out.println("Client: opening socket to " + address
					+ " on port " + port);

		ClientThread ct = new ClientThread(sock);
		ct.start();
		System.out.println("Client, made connection...");

		do {
			System.out.print("Enter send Text: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String input = null;

			try {
				input = br.readLine();
			} catch (IOException ioe) {
				System.out.println("IO error trying to read input!");
				System.exit(1);
			}

			if (input.equals("Shutdown")) {
				Send("Shudo~#");

			} else if (input.equals("logout")) {
				quit = true;
				Send(input);

			} else {
				Send(input);
			}

		} while (!quit);

		System.out.println("Client, closing connection...");
		sock.close();

		System.out.println("Client, done...");
	}
}
