package client.npc.monster;

import client.npc.Monster;

public class Goblin extends Monster {
	
	public Goblin (int x,int y,int id) {
		super(x,y,id,"goblin_30.gif");
		this.atack = 1;
		this.defence = 0;
		this.examineText = "Green and ugly ...";
		this.max_hp = 15;
		this.mom_hp = 15;
		this.Img100 = getImage100("goblin_100.png");
		this.regenHP = 5;
		this.regenTime = 60000;
		this.spawnTime = 25000;
		this.option1 = "Attack";
	}
	
	public void killed (client.Player pl) {
		//System.out.println ("Monster.Goblin.killed :: player : "+pl.getName());
		int x = client.App.getRandom(1, 6); // get random number from 1 to 6
		
		
		if (x == 1)
			pl.addItem (new client.items.HealthPotion());
		if (x == 2)
			pl.addItem (new client.items.BigHealthPotion_2());
		if (x == 4)
			pl.addItem (new client.items.EnergyPotion());
		if (x == 5)
			pl.addItem (new client.items.Sword());
	}
	
	
	public void use () {
		attack();
	}
}
