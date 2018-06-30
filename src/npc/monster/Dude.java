package npc.monster;

import npc.Monster;

public class Dude extends Monster {
	public Dude (int x,int y, int id) {
		super(x,y,id,"pics/monster/small/dude_30.png");
		this.atack = 5;
		this.defence = 1;
		this.examineText = "Who's this ?";
		this.max_hp = 100;
		this.mom_hp = 100;
		this.name = "Dude";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage("pics/monster/small/dude_100.png");
		this.walkingRadius = 10;
		this.sleepingTime = 1200; // 50
		this.regenHP = 25;
		this.regenTime = 15000;
		this.spawnTime = 45000;
		this.option1 = "Attack";
	}
	
	
	public void killed (mainPackage.Player pl) {
		int r = mainPackage.MainApplet.getRandom (1,7);
		
		if ( r == 1)
			pl.addItem(new items.HealthPotion());
		if ( r == 2)
			pl.addItem(new items.BigHealthPotion_3());
		if ( r == 3)
			pl.addItem(new items.Sword());
		if ( r == 4) {
			int r2 = mainPackage.MainApplet.getRandom(0, 30);
			pl.addCoins(r2);
		}
		if ( r == 5) {
			int r2 = mainPackage.MainApplet.getRandom(80, 95);
			pl.addCoins(r2);
		}
		
		// when r == 6 or 7 , player gets nothing
	}
	
	
	public void use () {
		attack();
	}
}
