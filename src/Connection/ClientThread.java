// Listener Thread

package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import mainPackage.ExternalPlayer;
import mainPackage.MainApplet;

public class ClientThread extends Thread {

	private boolean DEBUG = false; // Debug f�r Umer :D
	private BufferedReader input;

	protected void Handle(String message) {		
		if (DEBUG)
			System.out.println("Handle:" + message);
		
		String data[] = message.split("~");
		
		/*Handle data depending of prefix : */
		try {
		
		if (data[0].equals("chars")) {
			MainApplet.getCCPanel().setData(data);
			MainApplet.applet.setContentPanel(2);
		}
		
		
		
		if (data[0].equals("loin")) {//Log in
			if (data[1].equals("dne")) {//done
				// Format: loin~dne~x~y~z~name
				MainApplet.map.create(data[2],data[3],data[4]);
				MainApplet.applet.setContentPanel(1);
				MainApplet.actPlayer.setData(data[2],data[3],data[4]);
				MainApplet.actPlayer.setName(data[5]);
				MainApplet.actPlayer.moveByServer();//to prevent player from being drawn at (8|6) {<- Default} at the beginning
				
			} else if (data[1].equals("bafo")) {//bad format
				if (data[2].equals("pw"))
					MainApplet.applet.alert("Wrong password!");
			} else if (data[1].equals("nf")) {//not found
				MainApplet.applet.alert("User does not exist!");
			} else if (data[1].equals("miss")) {//mistake
				if (data[2].equals("mloin"))
					MainApplet.applet.alert("This user is already logged in!");
				if (data[2].equals("wpw"))
					MainApplet.applet.alert("Wrong password!");	
				if (data[2].equals("ipmloin")) {
					MainApplet.applet.alert("To many connections fomr this ip");
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
				
				MainApplet.addInfo(username + " is now online.");
				ExternalPlayer.createNew(username, x, y, z);
			} else if (data[2].equals("ofl")) {
				MainApplet.addInfo(data[1] + " is now offline.");
				ExternalPlayer.destroy(data[1]);
			} else {						
				if (data[1].equals(MainApplet.actPlayer.getName())) {
					/// x, y, z position
					MainApplet.actPlayer.setData(data[2], data[3], data[4]);
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
				MainApplet.addInfo(data[1] + " : " + data[2]);//otherwise just show the received message
			}
		}
		if (data[0].equals("gmss")) {
			if (data[1].equals("miss") && data[2].equals("no")) {
				System.exit(0);//not online
			} else {
				MainApplet.addBlueInfo(data[1] + " : " + data[2]);//otherwise just show the received message
			}
		}
		if (data[0].equals("gmsw")) {
			if (data[1].equals("miss") && data[2].equals("no")) {
				System.exit(0);//not online
			} else {
				MainApplet.addBlueInfo(data[1] + " : " + data[2]);//otherwise just show the received message
			}
		}
		if (data[0].equals("msw")) {//whisper
			if (data[1].equals("miss") && data[2].equals("no")) {
				System.exit(0);//not online
			} else if (data[1].equals("miss") && data[2].equals("tno")){
				MainApplet.addInfo("Player not online");
			} else {
				MainApplet.addGreenInfo(data[1] + " : " + data[2]);//otherwise just show the received message
			}
		}
		
		/* MOVE HANDLING */
		if (data[0].equals("mve")) {	
			//System.out.println("ClientThread.handel :: mve - = "+data[1]);
			if (data[1].equals("dne"));
				MainApplet.actPlayer.moveByServer();
			if (data[1].equals("hck")) {
				System.out.println("Kicking");
				System.exit(0);//kick the speed hacker - muhaha
			}
		}
		
		/* FRIEND-LIST HANDLING*/
		if (data[0].equals("frl")) {
			
			String names = message.substring(4,message.length());
			MainApplet.actPlayer.updateFriendsList (names.split("~"));
		}
		
		/* INVENTORY/EQUIP-INVENTORY HANDLING */
		if (data[0].equals("invl")) {
			for (int i=0;i<30;i++) {
				MainApplet.actPlayer.getInventoryManager().setItem(items.Item.getItemByID(Integer.parseInt(data[i+1])), i);
			}
		}
		if (data[0].equals("equil")) {
			for (int i=0;i<10;i++) {
				MainApplet.actPlayer.getInventoryManager().setItemEq(items.Item.getItemByID(Integer.parseInt(data[i+1])), i);
			}
		}	
		
		/* NPC MOVE HANDLING */
		if (data[0].equals("nupd")) {
			int id = Integer.parseInt(data[1]);
			int x = Integer.parseInt(data[2]);
			int y = Integer.parseInt(data[3]);
			MainApplet.map.getNpcById(id).moveByServer(x, y);
		}
		
		
		/* TRADE HANDLING  */
		if (data[0].equals("trdinv")) {
			//System.out.println("ClientThread :: Invited by :"+data[1]);
			MainApplet.getGamePanel().invitScreen().setName(data[1],true);
			MainApplet.getGamePanel().invitScreen().setVisible(true);
		}
		if (data[0].equals("trinv")) {
			if (data[1].equals("miss"))
				MainApplet.addInfo(data[2]+" is currently trading with somebody else.");
		}
		if (data[0].equals("trdd")) {
			if (MainApplet.getGamePanel().tradeScreen().isTrading()) {
				MainApplet.getGamePanel().tradeScreen().setVisible(false);
				MainApplet.actPlayer.getInventoryManager().ejectAllTr();//eject all items from the trade inventory into the normal one
			} else {
				MainApplet.addInfo("Your trading invitation has been declined.");
			}
		}
		if (data[0].equals("trdinva")) {
			MainApplet.addInfo("Your trading invitation has been accepted.");
			MainApplet.getGamePanel().tradeScreen().setVisible(true,"a player");
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
		System.out.println("Listen Thread gestartet");
	}

	public void run() {
		do {
			String in = null;
			try {
				in = input.readLine();
			} catch (IOException e) {
				// Server is down
				MainApplet.applet.alert("Server is down");
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