package localServer.npc.monster;

import localServer.NPC;

public class Big_kA extends NPC {

	public Big_kA(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 10;
		this.sleepingTime = 1000;
	}
}
