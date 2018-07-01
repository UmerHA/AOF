package localServer.npc.monster;

import localServer.NPC;

public class Dragon extends NPC {

	public Dragon(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 5;
		this.sleepingTime = 2500;
	}
}
