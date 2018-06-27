package Monster;


public class Phoenix extends Monster {

	public Phoenix(int x, int y, int id) {
		super(x, y, id,"pics/monster/small/Phoenix_30.png");
		this.atack = 60;
		this.defence = 40;
		this.examineText = "Reborn from the ash.";
		this.max_hp = 150;
		this.mom_hp = 150;
		this.name = "Phoenix";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"pics/monster/small/Phoenix_100.png");
		this.walkingRadius = 10;
		this.sleepingTime = 1000;
		this.regenHP = 5;
		this.regenTime = 500;
		this.spawnTime = 2500;
		this.option1 = "Attack";
	}
	
	public void killed (MainPackage.Player pl) {
		int r = MainPackage.MainApplet.getRandom(1, 15);
		
		switch (r) {
			case 1 : 
			case 2 :
			case 3 :
			case 4 : pl.addCoins(MainPackage.MainApplet.getRandom(50, 350));break;
			case 5 : pl.addItem(new Items.SlayersHelmet());break;
			case 6 :
			case 7 :
			case 8 :
			case 9 : pl.addItem(new Items.BigHealthPotion_3());break;
			case 10 :
			case 11 : pl.addItem(new Items.WoodShield());break;
		}
	}
	
	
	public void use () {
		attack();
	}
}
