package npc.monster;

import npc.Monster;

public class kA extends Monster {

	public kA(int x, int y, int id) {
		super(x, y, id, "kA_30.png");
		this.atack = 1;
		this.defence = 0;
		this.examineText = "What the hell is that supposed to be ?";
		this.max_hp = 6;
		this.mom_hp = 6;
		this.Img100 = getImage100("kA_100.png");
		this.regenHP = 3;
		this.regenTime = 5000;
		this.spawnTime = 2500;
		this.option1 = "Attack";
	}

	public void killed(mainPackage.Player pl) {
		int r = mainPackage.MainApplet.getRandom(1, 3);

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