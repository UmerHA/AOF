package localServer.npc.tnpc;

import localServer.NPC;

public class Adventurer extends NPC {
	public Adventurer(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 2;
		this.sleepingTime = 1500;
	}
}
