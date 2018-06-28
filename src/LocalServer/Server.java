package LocalServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server {
	int port = 50000;
	
    JFrame frame = new JFrame("Server");
    JTextField dataField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 60);

    ClientConnection[] connections = new ClientConnection[20];
    
    public Server() throws IOException {
        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
    	
        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	send_message_to_client(dataField.getText(), 0);
                dataField.setText("");    	            	
            }
        });
    	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);     	
        
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(port);
        
        try {
            while (true) {
            	connections[clientNumber] =  new ClientConnection(listener.accept(), clientNumber, this);
            	connections[clientNumber].start();
            	
            	clientNumber++;
            }
        } finally {
            listener.close();
        }
    }
    
    void send_message_to_client(String message, int clientNumber) {
    	connections[clientNumber].to_client.println(message);
        messageArea.append("To client " + clientNumber +  ": " + message + "\n");
    }
	void receive_message_from_client(String message, int clientNumber) {
		messageArea.append("From client " + clientNumber + ": " + message + "\n");
	}
    
    public static void main(String[] args) throws Exception {
        System.out.println("The server is now running.");
        new Server();
    }

    
    class ClientConnection extends Thread {
    	Server parent;
    	
    	Socket socket;
    	int clientNumber;
    	
    	BufferedReader from_client;
    	PrintWriter to_client;

        public ClientConnection(Socket socket, int clientNumber, Server parent) throws IOException {
        	this.parent = parent;
        	this.clientNumber = clientNumber;
            this.socket = socket;
            log("New connection with client# " + clientNumber + " at " + socket);
            
            from_client = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            to_client = new PrintWriter(socket.getOutputStream(), true);
        }

        public void run() {
            try {
                // Send a welcome message to the client.
                parent.send_message_to_client("Hello, you are client #" + clientNumber + ".", clientNumber);

                // Get messages from the client, line by line
                while (true) {
                    String input = from_client.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    parent.receive_message_from_client(input, clientNumber);
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }



        private void log(String message) {
            System.out.println(message);
        }
    }
}