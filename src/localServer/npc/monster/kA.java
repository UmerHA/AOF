package localServer.npc.monster;

import localServer.NPC;

public class kA extends NPC {

	public kA(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 2;
		this.sleepingTime = 5000;
	}
}