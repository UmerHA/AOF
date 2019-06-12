package localServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

class ClientConnector {

	static int port = 50000;

    static List<ClientConnection> connections = new ArrayList<ClientConnection>();    

    static void connect() throws IOException {
        int clientNumber = 0;
        
        ServerSocket listener = null;
        try {
            listener = new ServerSocket(port);
            
        	while (true) {
            	ClientConnection newConnection = new ClientConnection(listener.accept(), clientNumber);
            	
            	connections.add(newConnection);
            	Server.players.add(null); // Client is active, but no player is logged in

            	newConnection.start();
            	
            	clientNumber++;
            }
        }  catch (java.net.BindException e) {
        	System.err.println("Port " + port + " already in use. Exiting Server.");
        } finally {
        	if (listener != null)
        			listener.close();
        }    	
    	
    	
    }
    

    static void sendMessage(String message, int clientNumber) {
    	connections.get(clientNumber).to_client.println(message);
    	Server.displayMessage("To client " + clientNumber +  ": " + message + "\n");
    }
    static void sendResponses(List<Response> responses) {
		if (responses == null)
			return;
		
		for (Response response : responses)
			sendMessage(response.message, response.clientNumber);	
    }
	static void receiveMessage(String message, int clientNumber) {
		Server.displayMessage("From client " + clientNumber + ": " + message + "\n");
		List<Response> responses = Server.handle(message, clientNumber);
		sendResponses(responses);
	}
	
	static List<Integer> getPlayingClients () {
		List<Integer> playingClients = new ArrayList<Integer>();
		
		for (int i=0; i<connections.size(); i++)
			if (connections.get(i) != null && Server.players.get(i) != null)
				playingClients.add(connections.get(i).clientNumber);
		
		return playingClients;
	}
	
    public static void main(String[] args) throws Exception {
        System.out.println("The server is now running.");
        new ClientConnector();
    }
}