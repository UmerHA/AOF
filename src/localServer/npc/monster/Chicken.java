package localServer.npc.monster;

import localServer.NPC;

public class Chicken extends NPC {
	public Chicken(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 15;
		this.sleepingTime = 100;
	}
}
