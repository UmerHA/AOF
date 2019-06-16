package client.npc.monster;

import client.npc.Monster;

public class Dragon extends Monster {

	public Dragon (int x,int y,int id) {
		super(x,y,id,"red_dragon_30.jpg");
		this.atack = 125;
		this.defence = 110;
		this.examineText = "One of the most powerful creatures the world has ever seen.";
		this.max_hp = 180;
		this.mom_hp = 180;
		this.Img100 = getImage100("red_dragon_100.jpg");
		this.regenHP = 5;
		this.regenTime = 3000;
		this.spawnTime = 90000;
		this.option1 = "Attack";
	}
	
	public void killed (client.Player pl) {
		int r = client.App.getRandom(1, 20);
		
		switch (r) {
			case 1 : 
			case 2 :
			case 3 :
			case 4 : pl.addCoins(client.App.getRandom(50, 350));break;
			case 5 : pl.addItem(new client.items.SlayersPlatelegs());break;
			case 6 : pl.addItem(new client.items.SlayersGloves());
			case 7 :
			case 8 :
			case 9 : pl.addItem(new client.items.BigHealthPotion_3());break;
			case 10 :
			case 11 : pl.addItem(new client.items.WoodShield());break;
			default : pl.addCoins(250);
		}
	}
	
	
	public void use () {
		attack();
	}
}
