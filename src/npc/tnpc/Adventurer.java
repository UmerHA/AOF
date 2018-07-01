package npc.tnpc;

import mainPackage.MainApplet;
import npc.TNPC;

public class Adventurer extends TNPC {

	public Adventurer(int x, int y, int id) {
		super(x, y, id, "joe.png");
		this.examineText = "A man who has seen the world.";
	}
	
	public void use () {
		MainApplet.addInfo("The adventurer doesn't want to talk to you");
	}

}
