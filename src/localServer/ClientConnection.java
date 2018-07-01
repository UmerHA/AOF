package localServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientConnection extends Thread {
	Socket socket;
	int clientNumber;
	
	BufferedReader from_client;
	PrintWriter to_client;

    public ClientConnection(Socket socket, int clientNumber) throws IOException {
		this.clientNumber = clientNumber;
        this.socket = socket;
        Util.log("New connection with client# " + clientNumber + " at " + socket);
        
        from_client = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        to_client = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            // Send a welcome message to the client.
            ClientConnector.sendMessage("Hello, you are client #" + clientNumber + ".", clientNumber);

            // Get messages from the client, line by line
            while (true) {
                String input = from_client.readLine();
                if (input == null || input.equals(".")) {
                    break;
                }
                ClientConnector.receiveMessage(input, clientNumber);
            }
        } catch (IOException e) {
            Util.log("Error handling client# " + clientNumber + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                Util.log("Couldn't close a socket, what's going on?");
            }
            Util.log("Connection with client# " + clientNumber + " closed");
        }
    }
}