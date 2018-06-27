package TNPC;

import MainPackage.MainApplet;

public class Adventurer extends TNPC {

	public Adventurer(int x, int y, int id) {
		super(x, y, id, "pics/monster/small/joe.png");
		this.examineText = "A man who has seen the world.";
		this.name = "Adventurer";
		this.sleepingTime = 1500;
		this.walkingRadius = 2;
	}
	
	public void use () {
		MainApplet.addInfo("The adventurer doesn't want to talk to you");
	}

}
