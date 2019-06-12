package npc.monster;

import npc.Monster;

public class Sheep extends Monster {
	public Sheep (int x,int y,int id) {
		super(x,y,id,"sheep_30.png");
		this.atack = 1;
		this.defence = 0;
		this.examineText = "White and sheepy.";
		this.max_hp = 8;
		this.mom_hp = 8;
		this.Img100 = getImage100("sheep_100.png");
		this.regenHP = 1;
		this.regenTime = 15000;
		this.spawnTime = 60000;
		this.option1 = "Attack";
	}
	
	public void killed (mainPackage.Player pl) {
		int r = mainPackage.MainApplet.getRandom(1, 15);
		
		switch (r) {
			case 1 : 
			case 2 :
			case 3 :
			case 4 : pl.addCoins(mainPackage.MainApplet.getRandom(0, 400));break;
			case 5 : pl.addItem(new items.SlayersCape());break;
			case 6 :
			case 7 :
			case 8 :
			case 9 : pl.addItem(new items.HealthPotion());break;
			case 10 :
			case 11 : pl.addItem(new items.Sword());break;
		}
	}
	
	
	public void use () {
		attack();
	}
}