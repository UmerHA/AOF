package localServer.npc.tnpc;

import localServer.NPC;

public class Edward extends NPC {
	public Edward(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 3;
		this.sleepingTime = 1200;
		this.walkingProbability = 0.5F;
	}
}
