package client.npc.monster;

import client.npc.Monster;

public class Ankou extends Monster {

	public Ankou(int x, int y, int id) {
		super(x, y, id,"Ankou_30.gif");
		this.atack = 19;
		this.defence = 5;
		this.examineText = "This is a monster we stole from RuneScape, but don't tell anyone !";
		this.max_hp = 80;
		this.mom_hp = 80;
		this.Img100 = getImage100("Ankou_100.gif");
		this.regenHP = 5;
		this.regenTime = 2000;
		this.spawnTime = 5000;
		this.option1 = "Attack";
	}
	
	public void killed (client.Player pl) {
		int r = client.App.getRandom(1, 5);
		if (r==1)
			pl.addItem(new client.items.StrangePotion());
		if (r==2)
			pl.addItem(new client.items.Platebody());
		if (r==3)
			pl.addItem(new client.items.Sword());
		if (r==3)
			pl.addItem(new client.items.Platelegs());
	}
	
	
	public void use () {
		attack();
	}
}
