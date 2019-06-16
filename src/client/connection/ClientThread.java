// Listener Thread

package client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

import client.App;
import client.ExternalPlayer;
import client.Util;

public class ClientThread extends Thread { 
	private static final Logger LOGGER = Util.getLogger();

	private boolean DEBUG = true; // Debug für Umer :D
	private BufferedReader input;

	protected void Handle(String message) {		
		if (DEBUG) 
			Util.timedLog("Handle:" + message);
		LOGGER.fine("Handle: " + message);
		
		String data[] = message.split("~");
		
		/*Handle data depending of prefix : */
		try {
		
		if (data[0].equals("chars")) {
			App.getCCPanel().setData(data);
			App.app.setContentPanel(2);
		}
		
		
		
		if (data[0].equals("loin")) {//Log in
			if (data[1].equals("dne")) {//done
				// Format: loin~dne~x~y~z~name
				App.map.create(data[2],data[3],data[4]);
				App.app.setContentPanel(1);
				App.actPlayer.setData(data[2],data[3],data[4]);
				App.actPlayer.setName(data[5]);
				App.actPlayer.moveByServer();//to prevent player from being drawn at (8|6) {<- Default} at the beginning
				
			} else if (data[1].equals("bafo")) {//bad format
				if (data[2].equals("pw"))
					App.app.alert("Wrong password!");
			} else if (data[1].equals("nf")) {//not found
				App.app.alert("User does not exist!");
			} else if (data[1].equals("miss")) {//mistake
				if (data[2].equals("mloin"))
					App.app.alert("This user is already logged in!");
				if (data[2].equals("wpw"))
					App.app.alert("Wrong password!");	
				if (data[2].equals("ipmloin")) {
					App.app.alert("To many connections fomr this ip");
					System.exit(0);
				}
			}
		}
		
		// initialize all external players that where logged in when player logged in
		if (data[0].equals("extinit")) {
			String username = data[1];
			int x = Integer.parseInt(data[2]);
			int y = Integer.parseInt(data[3]);
			int z = Integer.parseInt(data[4]);			
			
			ExternalPlayer.createNew(username, x, y, z);
		}
		
		
		if (data[0].equals("upd")) {//update
			if (data[2].equals("on")) {		
				String username = data[1];
				int x = Integer.parseInt(data[3]);
				int y = Integer.parseInt(data[4]);
				int z = Integer.parseInt(data[5]);
				
				App.addInfo(username + " is now online.");
				ExternalPlayer.createNew(username, x, y, z);
			} else if (data[2].equals("ofl")) {
				App.addInfo(data[1] + " is now offline.");
				ExternalPlayer.destroy(data[1]);
			} else {						
				if (data[1].equals(App.actPlayer.getName())) {
					/// x, y, z position
					App.actPlayer.setData(data[2], data[3], data[4]);
				} else {
					//MainApplet.addInfo(data[1] + " just got updated.");
					ExternalPlayer.setPosition(data[1], data[2], data[3], data[4]);
				}
			}
		}	
		
		
		/* MESSAGE HANDLING : */
		if (data[0].equals("mss")) {
			if (data[1].equals("miss") && data[2].equals("no")) {
				System.exit(0);//not online
			} else {
				App.addInfo(data[1] + " : " + data[2]);//otherwise just show the received message
			}
		}
		if (data[0].equals("gmss")) {
			if (data[1].equals("miss") && data[2].equals("no")) {
				System.exit(0);//not online
			} else {
				App.addBlueInfo(data[1] + " : " + data[2]);//otherwise just show the received message
			}
		}
		if (data[0].equals("gmsw")) {
			if (data[1].equals("miss") && data[2].equals("no")) {
				System.exit(0);//not online
			} else {
				App.addBlueInfo(data[1] + " : " + data[2]);//otherwise just show the received message
			}
		}
		if (data[0].equals("msw")) {//whisper
			if (data[1].equals("miss") && data[2].equals("no")) {
				System.exit(0);//not online
			} else if (data[1].equals("miss") && data[2].equals("tno")){
				App.addInfo("Player not online");
			} else {
				App.addGreenInfo(data[1] + " : " + data[2]);//otherwise just show the received message
			}
		}
		
		/* MOVE HANDLING */
		if (data[0].equals("mve")) {	
			//System.out.println("ClientThread.handel :: mve - = "+data[1]);
			if (data[1].equals("dne"));
				App.actPlayer.moveByServer();
			if (data[1].equals("hck")) {
				System.out.println("Kicking");
				System.exit(0);//kick the speed hacker - muhaha
			}
		}
		
		/* FRIEND-LIST HANDLING*/
		if (data[0].equals("frl")) {
			
			String names = message.substring(4,message.length());
			App.actPlayer.updateFriendsList (names.split("~"));
		}
		
		/* INVENTORY/EQUIP-INVENTORY HANDLING */
		if (data[0].equals("invl")) {
			for (int i=0;i<30;i++) {
				App.actPlayer.getInventoryManager().setItem(client.items.Item.getItemByID(Integer.parseInt(data[i+1])), i);
			}
		}
		if (data[0].equals("equil")) {
			for (int i=0;i<10;i++) {
				App.actPlayer.getInventoryManager().setItemEq(client.items.Item.getItemByID(Integer.parseInt(data[i+1])), i);
			}
		}	
		
		/* NPC MOVE HANDLING */
		if (data[0].equals("nupd")) {
			int id = Integer.parseInt(data[1]);
			int x = Integer.parseInt(data[2]);
			int y = Integer.parseInt(data[3]);
			App.map.getNpcById(id).moveByServer(x, y);
		}
		
		
		/* TRADE HANDLING  */
		if (data[0].equals("trdinv")) {
			//System.out.println("ClientThread :: Invited by :"+data[1]);
			App.getGamePanel().invitScreen().setName(data[1],true);
			App.getGamePanel().invitScreen().setVisible(true);
		}
		if (data[0].equals("trinv")) {
			if (data[1].equals("miss"))
				App.addInfo(data[2]+" is currently trading with somebody else.");
		}
		if (data[0].equals("trdd")) {
			if (App.getGamePanel().tradeScreen().isTrading()) {
				App.getGamePanel().tradeScreen().setVisible(false);
				App.actPlayer.getInventoryManager().ejectAllTr();//eject all items from the trade inventory into the normal one
			} else {
				App.addInfo("Your trading invitation has been declined.");
			}
		}
		if (data[0].equals("trdinva")) {
			App.addInfo("Your trading invitation has been accepted.");
			App.getGamePanel().tradeScreen().setVisible(true,"a player");
		}
		} catch (NullPointerException e) {
			System.err.println(message);
		}
	}

	public ClientThread(Socket sock) throws IOException {
		try {
			input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Util.timedLog("Listen Thread gestartet");
		LOGGER.fine("Listen Thread gestartet");
	}

	public void run() {
		do {
			String in = null;
			try {
				in = input.readLine();
			} catch (IOException e) {
				// Server is down
				App.app.alert("Server is down");
				Util.timedLog("Server is down");
				LOGGER.severe("Server is down");
				System.exit(0);
			}
			if (in != null) {
				String[] messages = in.split("#");
				
				for (int i = 0; i < messages.length; i++)
			        Handle(messages[0]);
			}
		} while (true);
	}
}