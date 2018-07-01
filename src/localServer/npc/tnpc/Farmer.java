package localServer.npc.tnpc;

import localServer.NPC;

public class Farmer extends NPC {
	public Farmer(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 2;
		this.sleepingTime = 1200;
	}
}
