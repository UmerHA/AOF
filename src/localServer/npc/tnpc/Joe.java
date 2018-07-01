package localServer.npc.tnpc;

import localServer.NPC;

public class Joe extends NPC {
	public Joe(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 3;
		this.sleepingTime = 500;
	}
}
