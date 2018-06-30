package LocalServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Server {

	static int port = 50000;
	static Dao<Player, String> playerDao;
	static ConnectionSource dbConnectionSource;
	
    static JFrame frame = new JFrame("Server");
    static JTextField dataField = new JTextField(40);
    static JTextArea messageArea = new JTextArea(8, 60);

    static List<ClientConnection> connections = new ArrayList<ClientConnection>();    
    static List<Player> players = new ArrayList<Player>();

    static Dao<Player, String> playerDao() {return playerDao;}
    
    public Server() throws IOException {
        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "South");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
    	
        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sendMessage(dataField.getText(), 0);
                dataField.setText("");    	            	
            }
        });
    	
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);  // this will center the JFrame
        frame.setVisible(true);     	
        
        connectToDB();
        
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(port);
        
        try {
            while (true) {
            	ClientConnection newConnection = new ClientConnection(listener.accept(), clientNumber, this);
            	
            	connections.add(newConnection);
            	players.add(new Player());
            	
            	newConnection.start();
            	
            	clientNumber++;
            }
        } finally {
            listener.close();
        }
    }
    
    void sendMessage(String message, int clientNumber) {
    	connections.get(clientNumber).to_client.println(message);
        messageArea.append("To client " + clientNumber +  ": " + message + "\n");
    }
	void receiveMessage(String message, int clientNumber) {
		messageArea.append("From client " + clientNumber + ": " + message + "\n");
		String response = Protocol.handle(message, clientNumber);
		
		if (response != null)
			sendMessage(response, clientNumber);			
	}
    
	static void connectToDB() {
		try {
			String url = "jdbc:sqlite:src/data/players.db";

			dbConnectionSource = new JdbcConnectionSource(url);

			playerDao = DaoManager.createDao(dbConnectionSource, Player.class);
			
			TableUtils.createTableIfNotExists(dbConnectionSource, Player.class);
			
			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
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
                parent.sendMessage("Hello, you are client #" + clientNumber + ".", clientNumber);

                // Get messages from the client, line by line
                while (true) {
                    String input = from_client.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    parent.receiveMessage(input, clientNumber);
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