package localServer.npc.monster;

import localServer.NPC;

public class BigGoblin extends NPC {

	public BigGoblin(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 4;
		this.sleepingTime = 1500;
	}
}
