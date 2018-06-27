package Monster;


public class Jad extends Monster {
	  public Jad (int x,int y,int id) {
		  super(x,y,id,"pics/monster/small/Jad_30.gif") ; 
		  this.atack = 0; 
		  this.defence = 0; 
		  this.examineText = "The guardian of some ancient treasure."; 
		  this.max_hp = 1; 
		  this.mom_hp = 1; 
		  this.name = "Jad"; 
		  this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage("pics/monster/small/Jad_100.gif");
		  this.walkingRadius = 2; 
		  this.sleepingTime = 5000; 
		  this.regenHP = 3; 
		  this.regenTime = 5000;
		  this.spawnTime = 2500;
		  this.option1 = "Attack";
	  }
	  
		
		public void killed (MainPackage.Player pl) {
			if ((!pl.getName().equalsIgnoreCase("niels"))&&(!pl.getName().equalsIgnoreCase("umer"))) {
				MainPackage.MainApplet.addInfo("I will never let you have my treasures!");
				return;
			}
			
			pl.addItem(new Items.SlayersCape());
			pl.addItem(new Items.SlayersChain());
			pl.addItem(new Items.SlayersGloves());
			pl.addItem(new Items.SlayersHelmet());
			pl.addItem(new Items.SlayersPlatebody());
			pl.addItem(new Items.SlayersPlatelegs());
			pl.addItem(new Items.SlayersScythe());

			// when r == 3 , player gets nothing
		}
		
		
		public void use () {
			attack();
		}
}
