package localServer.npc.monster;

import localServer.NPC;

public class Demon extends NPC {
	public Demon(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 3;
		this.sleepingTime = 1200;
	}
}