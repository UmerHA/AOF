package localServer.npc.monster;

import localServer.NPC;

public class Phoenix extends NPC {

	public Phoenix(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 10;
		this.sleepingTime = 1000;
	}
}
