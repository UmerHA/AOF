package localServer.npc.monster;

import localServer.NPC;

public class MithrilDragon extends NPC {

	public MithrilDragon(int x, int y, int id) {
		super(x, y, id);
		this.walkingRadius = 6;
		this.sleepingTime = 500;
	}
}
