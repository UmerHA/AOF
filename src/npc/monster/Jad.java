package npc.monster;

import npc.Monster;

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
	  
		
		public void killed (mainPackage.Player pl) {
			if ((!pl.getName().equalsIgnoreCase("niels"))&&(!pl.getName().equalsIgnoreCase("umer"))) {
				mainPackage.MainApplet.addInfo("I will never let you have my treasures!");
				return;
			}
			
			pl.addItem(new items.SlayersCape());
			pl.addItem(new items.SlayersChain());
			pl.addItem(new items.SlayersGloves());
			pl.addItem(new items.SlayersHelmet());
			pl.addItem(new items.SlayersPlatebody());
			pl.addItem(new items.SlayersPlatelegs());
			pl.addItem(new items.SlayersScythe());

			// when r == 3 , player gets nothing
		}
		
		
		public void use () {
			attack();
		}
}
