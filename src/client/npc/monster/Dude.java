package client.npc.monster;

import client.npc.Monster;

public class Dude extends Monster {
	public Dude (int x,int y, int id) {
		super(x,y,id,"dude_30.png");
		this.atack = 5;
		this.defence = 1;
		this.examineText = "Who's this ?";
		this.max_hp = 100;
		this.mom_hp = 100;
		this.Img100 = getImage100("dude_100.png");
		this.regenHP = 25;
		this.regenTime = 15000;
		this.spawnTime = 45000;
		this.option1 = "Attack";
	}
	
	
	public void killed (client.Player pl) {
		int r = client.App.getRandom (1,7);
		
		if ( r == 1)
			pl.addItem(new client.items.HealthPotion());
		if ( r == 2)
			pl.addItem(new client.items.BigHealthPotion_3());
		if ( r == 3)
			pl.addItem(new client.items.Sword());
		if ( r == 4) {
			int r2 = client.App.getRandom(0, 30);
			pl.addCoins(r2);
		}
		if ( r == 5) {
			int r2 = client.App.getRandom(80, 95);
			pl.addCoins(r2);
		}
		
		// when r == 6 or 7 , player gets nothing
	}
	
	
	public void use () {
		attack();
	}
}
