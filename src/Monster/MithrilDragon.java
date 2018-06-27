package Monster;


public class MithrilDragon extends Monster {

	public MithrilDragon(int x, int y, int id) {
		super(x, y, id,"pics/monster/small/MithrilDragon_30.gif");
		this.atack = 140;
		this.defence = 145;
		this.examineText = "A dragon made out of pure mithril !";
		this.max_hp = 245;
		this.mom_hp = 245;
		this.name = "MithrilDragon";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"pics/monster/small/MithrilDragon_100.gif");
		this.walkingRadius = 6;
		this.sleepingTime = 500;
		this.regenHP = 20;
		this.regenTime = 5000;
		this.spawnTime = 15000;
		this.option1 = "Attack";
	}
	
	public void killed (MainPackage.Player pl) {
		int r = MainPackage.MainApplet.getRandom(1, 12);
		
		switch (r) {
			case 1 : pl.addItem(new Items.SlayersScythe());break;
			case 2 : pl.addItem(new Items.SlayersChain());break;
			case 3 : pl.addItem(new Items.SlayersPlatebody());break;
			case 4 : 
			case 5 : 
			case 6 : pl.addCoins(MainPackage.MainApplet.getRandom(0, 1200));break;
			case 7 :
			case 8 :
			case 9 : pl.addItem(new Items.BigHealthPotion_3());break;
		}
	}
	
	
	public void use () {
		attack();
	}
}
