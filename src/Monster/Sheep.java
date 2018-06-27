package Monster;


public class Sheep extends Monster {
	public Sheep (int x,int y,int id) {
		super(x,y,id,"pics/monster/small/sheep_30.png");
		this.atack = 1;
		this.defence = 0;
		this.examineText = "White and sheepy.";
		this.max_hp = 8;
		this.mom_hp = 8;
		this.name = "Sheep";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage("pics/monster/small/sheep_100.png");
		this.walkingRadius = 2;
		this.sleepingTime = 1250; 
		this.regenHP = 1;
		this.regenTime = 15000;
		this.spawnTime = 60000;
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