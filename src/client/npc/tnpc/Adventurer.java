package client.npc.tnpc;

import client.App;
import client.npc.TNPC;

public class Adventurer extends TNPC {

	public Adventurer(int x, int y, int id) {
		super(x, y, id, "joe.png");
		this.examineText = "A man who has seen the world.";
	}
	
	public void use () {
		App.addInfo("The adventurer doesn't want to talk to you");
	}

}
