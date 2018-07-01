package npc.monster;

import npc.Monster;

public class Dog extends Monster {

	public Dog(int x, int y, int id) {
		super(x, y, id, "Dog_30.gif");
		this.atack = 5;
		this.defence = 5;
		this.examineText = "A hot dog ...";
		this.max_hp = 50;
		this.mom_hp = 50;
		this.Img100 = getImage100("Dog_100.gif");
		this.regenHP = 22;
		this.regenTime = 1500;
		this.spawnTime = 2500;
		this.option1 = "Attack";
	}

	public void use() {
		attack();
	}
}
