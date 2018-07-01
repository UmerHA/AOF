package localServer.npc.monster;

import localServer.NPC;

public class Sheep extends NPC {
	public Sheep(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 2;
		this.sleepingTime = 1200;
	}
}