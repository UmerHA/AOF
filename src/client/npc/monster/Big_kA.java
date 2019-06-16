package client.npc.monster;

import client.npc.Monster;

public class Big_kA extends Monster {

	public Big_kA(int x, int y, int id) {
		super(x,y,id,"Big kA_30.png");
		this.atack = 10;
		this.defence = 10;
		this.examineText = "A very big ... kA ?";
		this.max_hp = 8000;
		this.mom_hp = 8000;
		this.name = "Big kA";
		this.Img100 = getImage100("Big kA_100.png");
		this.regenHP = 4000;
		this.regenTime = 10000;
		this.spawnTime = 1500;
		this.option1 = "Attack";
	}
	
	public void use () {
		attack();
	}
}
