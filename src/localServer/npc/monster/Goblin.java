package localServer.npc.monster;

import localServer.NPC;

public class Goblin extends NPC {

	public Goblin(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 3;
		this.sleepingTime = 1700;
	}
}
