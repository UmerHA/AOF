package Monster;


public class Demon extends Monster {
	public Demon (int x,int y,int id) {
		super(x,y,id,"pics/monster/small/demon_30.png");
		this.atack = 120;
		this.defence = 95;
		this.examineText = "Holy sh*t, a real demon! I better start running now ...";
		this.max_hp = 150;
		this.mom_hp = 150;
		this.name = "Demon";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage("pics/monster/small/demon_100.png");
		this.walkingRadius = 3;
		this.sleepingTime = 1250; 
		this.regenHP = 10;
		this.regenTime = 15000;
		this.spawnTime = 30000;
		this.option1 = "Attack";
	}
	
	public void killed (MainPackage.Player pl) {
		int r = MainPackage.MainApplet.getRandom(1, 15);
		
		switch (r) {
			case 1 : 
			case 2 :
			case 3 :
			case 4 : pl.addCoins(MainPackage.MainApplet.getRandom(0, 400));break;
			case 5 : pl.addItem(new Items.SlayersCape());break;
			case 6 :
			case 7 :
			case 8 :
			case 9 : pl.addItem(new Items.HealthPotion());break;
			case 10 :
			case 11 : pl.addItem(new Items.Sword());break;
		}
	}
	
	
	public void use () {
		attack();
	}
}