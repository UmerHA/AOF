package Monster;

public class kA extends Monster {

	public kA(int x, int y, int id) {
		super(x, y, id,"pics/monster/small/kA_30.png");
		this.atack = 1;
		this.defence = 0;
		this.examineText = "What the hell is that supposed to be ?";
		this.max_hp = 6;
		this.mom_hp = 6;
		this.name = "kA";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"pics/monster/small/kA_100.png");
		this.walkingRadius = 2;
		this.sleepingTime = 5000;
		this.sleepingTime = 2500; // delete this line after test
		this.regenHP = 3;
		this.regenTime = 5000;
		this.spawnTime = 2500;
		this.option1 = "Attack";
	}

	public void killed(MainPackage.Player pl) {
		int r = MainPackage.MainApplet.getRandom(1, 3);

		if (r == 1)
			pl.addCoins(2);
		if (r == 2)
			pl.addCoins(4);

		// when r == 3 , player gets nothing
	}

	public void use() {
		attack();
	}
}