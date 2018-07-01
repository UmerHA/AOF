package localServer.npc.monster;

import localServer.NPC;

public class Dude extends NPC {
	public Dude(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 10;
		this.sleepingTime = 1200;
	}
}
