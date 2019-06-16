package client.npc.monster;

import client.npc.Monster;

public class Jad extends Monster {
	  public Jad (int x,int y,int id) {
		  super(x,y,id,"Jad_30.gif") ; 
		  this.atack = 0; 
		  this.defence = 0; 
		  this.examineText = "The guardian of some ancient treasure."; 
		  this.max_hp = 1; 
		  this.mom_hp = 1; 
		  this.Img100 = getImage100("Jad_100.gif");
		  this.regenHP = 3; 
		  this.regenTime = 5000;
		  this.spawnTime = 2500;
		  this.option1 = "Attack";
	  }
	  
		
		public void killed (client.Player pl) {
			if ((!pl.getName().equalsIgnoreCase("niels"))&&(!pl.getName().equalsIgnoreCase("umer"))) {
				client.App.addInfo("I will never let you have my treasures!");
				return;
			}
			
			pl.addItem(new client.items.SlayersCape());
			pl.addItem(new client.items.SlayersChain());
			pl.addItem(new client.items.SlayersGloves());
			pl.addItem(new client.items.SlayersHelmet());
			pl.addItem(new client.items.SlayersPlatebody());
			pl.addItem(new client.items.SlayersPlatelegs());
			pl.addItem(new client.items.SlayersScythe());

			// when r == 3 , player gets nothing
		}
		
		
		public void use () {
			attack();
		}
}
