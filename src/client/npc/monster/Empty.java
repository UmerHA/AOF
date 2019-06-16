package client.npc.monster;

import client.npc.NPC;

public class Empty extends NPC {

	public Empty (int x,int y, int id) {
		super(x,y,id,"pics/monster/small/dead.png");
		this.name = "None";
	}

}
