package Monster;


public class BigGoblin extends Monster {

	public BigGoblin(int x, int y, int id) {
		super(x, y, id,"pics/monster/small/BigGoblin_30.gif");
		this.atack = 29;
		this.defence = 4;
		this.examineText = "Big, green and ugly ...";
		this.max_hp = 25;
		this.mom_hp = 25;
		this.name = "BigGoblin";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"pics/monster/small/BigGoblin_100.gif");
		this.walkingRadius = 4;
		this.sleepingTime = 1500;
		this.regenHP = 2;
		this.regenTime = 2000;
		this.spawnTime = 10000;
		this.option1 = "Attack";
	}
	
	public void killed (MainPackage.Player pl) {
		int r = MainPackage.MainApplet.getRandom(1, 3);
		
		if (r==1)
			pl.addItem(new Items.MageHat());
		if (r==2)
			pl.addItem(new Items.FistOfZen());
	}
	
	public void use () {
		attack();
	}
}
