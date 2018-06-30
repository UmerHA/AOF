package localServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class TextClient extends Thread {

	String serverAddress = "127.0.0.1";
	int port = 50000;
	
    private BufferedReader from_server;
    private PrintWriter to_server;
    private JFrame frame = new JFrame("Text Client");
    private JTextField dataField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 60);
	private JScrollPane scrollableMessageArea = new JScrollPane(messageArea);

    private Socket socket;
    
    public TextClient() {
        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");

        // Add Listeners
        dataField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	send_message_to_server(dataField.getText());
            	dataField.setText("");
            }
        });
    }

    public void connectToServer() throws IOException {
        socket = new Socket(serverAddress, port);
        from_server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        to_server = new PrintWriter(socket.getOutputStream(), true);
    }

    
    void send_message_to_server(String message) {
    	to_server.println(message);
    	displayMessage("To Server: " + message + "\n");
    }
	void receive_message_from_server(String message) {
		displayMessage("From Server: " + message + "\n");
	}   
	void displayMessage(String message) {
    	messageArea.append(message);
    	
    	JScrollBar vertical = scrollableMessageArea.getVerticalScrollBar();
    	vertical.setValue( vertical.getMaximum() );
	}
    
    public void run() {
        try {
            while (true) {
                String message = from_server.readLine();
                if (message != null)
                	receive_message_from_server(message);
            }
        } catch (IOException e) {
            log("Error handling server: " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log("Couldn't close a socket, what's going on?");
            }
            log("Connection with serve closed");
        }
    }

    public static void main(String[] args) throws Exception {
        TextClient client = new TextClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.pack();
        client.frame.setVisible(true);
        client.connectToServer();
        client.run();
    }
    
    private void log(String message) {
        System.out.println(message);
    }
}