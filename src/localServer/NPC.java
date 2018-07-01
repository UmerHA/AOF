package localServer;

import localServer.npc.monster.Ankou;
import localServer.npc.monster.BigGoblin;
import localServer.npc.monster.Big_kA;
import localServer.npc.monster.Chicken;
import localServer.npc.monster.Demon;
import localServer.npc.monster.Dog;
import localServer.npc.monster.Dragon;
import localServer.npc.monster.Dude;
import localServer.npc.monster.Goblin;
import localServer.npc.monster.Jad;
import localServer.npc.monster.MithrilDragon;
import localServer.npc.monster.Phoenix;
import localServer.npc.monster.Sheep;
import localServer.npc.monster.kA;
import localServer.npc.tnpc.Adventurer;
import localServer.npc.tnpc.Edward;
import localServer.npc.tnpc.Farmer;
import localServer.npc.tnpc.Joe;

public class NPC {
	int id;

	int posX;
	int posY;
	
	int spawnX;
	int spawnY;

	protected int sleepingTime;
	protected int walkingRadius;
	protected float walkingProbability = 0.8F;

	float directionChangeProbability = 0.5F;
	Direction direction = new Direction(); // start with random direction

	public NPC(int x, int y, int id) {
		this.id = id;
		this.posX = x;
		this.posY = y;
		this.spawnX = x;
		this.spawnY = y;
	}

	public static NPC createNpcByName(String name, int x, int y, int id) {
		// TNPC
		if (name.equals("Joe"))
			return new Joe(x, y, id);
		if (name.equals("Edward"))
			return new Edward(x, y, id);
		if (name.equals("Adventurer"))
			return new Adventurer(x, y, id);
		if (name.equals("Farmer"))
			return new Farmer(x, y, id);

		// monster
		if (name.equals("Dragon"))
			return new Dragon(x, y, id);
		if (name.equals("Demon"))
			return new Demon(x, y, id);
		if (name.equals("Dude"))
			return new Dude(x, y, id);
		;
		if (name.equals("Goblin"))
			return new Goblin(x, y, id);
		if (name.equals("Chicken"))
			return new Chicken(x, y, id);
		if (name.equals("kA"))
			return new kA(x, y, id);
		if (name.equals("MithrilDragon"))
			return new MithrilDragon(x, y, id);
		if (name.equals("Dog"))
			return new Dog(x, y, id);
		if (name.equals("Phoenix"))
			return new Phoenix(x, y, id);
		if (name.equals("BigGoblin"))
			return new BigGoblin(x, y, id);
		if (name.equals("Ankou"))
			return new Ankou(x, y, id);
		;
		if (name.equals("Big_kA"))
			return new Big_kA(x, y, id);
		if (name.equals("Jad"))
			return new Jad(x, y, id);
		if (name.equals("Sheep"))
			return new Sheep(x, y, id);

		return null;
	}

	public String getName() {
		String[] tmp = this.getClass().toString().split("\\.");
		return tmp[tmp.length - 1];
	}

	void updateDirection() {
		if (Math.random() < directionChangeProbability)
			return;
		else {
			int rand = Util.randInt(1, 3);

			switch (rand) {
			case 0:
				direction.rotateClockwise();
				break;
			case 1:
				direction.rotateCounterClockwise();
				break;
			case 2:
				direction.reverse();
				break;
			}
		}
	}
	
	boolean wouldWalkOutOfMap(int stepX, int stepY) {
		return posX + stepX < 1 || posY + stepY < 1;
	}
	boolean wouldWalkOutOfRadius(int stepX, int stepY) {
		if (Math.abs(posX + stepX - spawnX) > walkingRadius)
			return true;
		if (Math.abs(posY + stepY - spawnY) > walkingRadius)
			return true;
				
		return false;
	}
}
