package client.npc.monster;

import client.npc.Monster;

public class Phoenix extends Monster {

	public Phoenix(int x, int y, int id) {
		super(x, y, id,"Phoenix_30.png");
		this.atack = 60;
		this.defence = 40;
		this.examineText = "Reborn from the ash.";
		this.max_hp = 150;
		this.mom_hp = 150;
		this.Img100 = getImage100("Phoenix_100.png");
		this.regenHP = 5;
		this.regenTime = 500;
		this.spawnTime = 2500;
		this.option1 = "Attack";
	}
	
	public void killed (client.Player pl) {
		int r = client.App.getRandom(1, 15);
		
		switch (r) {
			case 1 : 
			case 2 :
			case 3 :
			case 4 : pl.addCoins(client.App.getRandom(50, 350));break;
			case 5 : pl.addItem(new client.items.SlayersHelmet());break;
			case 6 :
			case 7 :
			case 8 :
			case 9 : pl.addItem(new client.items.BigHealthPotion_3());break;
			case 10 :
			case 11 : pl.addItem(new client.items.WoodShield());break;
		}
	}
	
	
	public void use () {
		attack();
	}
}
