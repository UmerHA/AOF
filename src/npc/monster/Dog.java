package npc.monster;

import npc.Monster;

public class Dog extends Monster {

	public Dog(int x, int y, int id) {

		super(x, y, id,"pics/monster/small/Dog_30.gif");
		this.atack = 5;
		this.defence = 5;
		this.examineText = "A hot dog ...";
		this.max_hp = 50;
		this.mom_hp = 50;
		this.name = "Dog";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"pics/monster/small/Dog_100.gif");
		this.walkingRadius = 2;
		this.sleepingTime = 1200;
		this.regenHP = 22;
		this.regenTime = 1500;
		this.spawnTime = 2500;
		this.option1 = "Attack";
	}
	
	
	public void use () {
		attack();
	}
}
