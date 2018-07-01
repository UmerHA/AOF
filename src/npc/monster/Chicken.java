package npc.monster;

import npc.Monster;

public class Chicken extends Monster {
	public Chicken (int x,int y,int id) {
		super(x,y,id,"chicken_30.png");
		this.atack = 1;
		this.defence = 1;
		this.examineText = "Let's see if I can catch this ...";
		this.max_hp = 10;
		this.mom_hp = 10;
		this.Img100 = getImage100("chicken_100.png");
		this.regenHP = 1;
		this.regenTime = 10000;
		this.spawnTime = 3000;
		this.option1 = "Attack";
	}
	
	public void use () {
		attack();
	}
}
