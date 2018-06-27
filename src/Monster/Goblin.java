package Monster;


public class Goblin extends Monster {
	
	public Goblin (int x,int y,int id) {
		super(x,y,id,"pics/monster/small/goblin_30.gif");
		this.atack = 1;
		this.defence = 0;
		this.examineText = "Green and ugly ...";
		this.max_hp = 15;
		this.mom_hp = 15;
		this.name = "Goblin";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage("pics/monster/small/goblin_100.png");
		this.walkingRadius = 3;
		this.sleepingTime = 1750; // 150
		this.regenHP = 5;
		this.regenTime = 60000;
		this.spawnTime = 25000;
		this.option1 = "Attack";
	}
	
	public void killed (MainPackage.Player pl) {
		//System.out.println ("Monster.Goblin.killed :: player : "+pl.getName());
		int x = MainPackage.MainApplet.getRandom(1, 6); // get random number from 1 to 6
		
		
		if (x == 1)
			pl.addItem (new Items.HealthPotion());
		if (x == 2)
			pl.addItem (new Items.BigHealthPotion_2());
		if (x == 4)
			pl.addItem (new Items.EnergyPotion());
		if (x == 5)
			pl.addItem (new Items.Sword());
	}
	
	
	public void use () {
		attack();
	}
}
