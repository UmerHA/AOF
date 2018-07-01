package localServer.npc.monster;

import localServer.NPC;

public class Jad extends NPC {
	public Jad(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 2;
		this.sleepingTime = 5000;
	}
}
