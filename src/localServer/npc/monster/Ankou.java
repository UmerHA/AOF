package localServer.npc.monster;

import localServer.NPC;

public class Ankou extends NPC {

	public Ankou(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 7;
		this.sleepingTime = 2000;
	}
}
