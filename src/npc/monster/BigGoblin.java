package npc.monster;

import npc.Monster;

public class BigGoblin extends Monster {

	public BigGoblin(int x, int y, int id) {
		super(x, y, id,"BigGoblin_30.gif");
		this.atack = 29;
		this.defence = 4;
		this.examineText = "Big, green and ugly ...";
		this.max_hp = 25;
		this.mom_hp = 25;
		this.name = "Big Goblin";
		this.Img100 = getImage100("BigGoblin_100.gif");
		this.regenHP = 2;
		this.regenTime = 2000;
		this.spawnTime = 10000;
		this.option1 = "Attack";
	}
	
	public void killed (mainPackage.Player pl) {
		int r = mainPackage.MainApplet.getRandom(1, 3);
		
		if (r==1)
			pl.addItem(new items.MageHat());
		if (r==2)
			pl.addItem(new items.FistOfZen());
	}
	
	public void use () {
		attack();
	}
}
