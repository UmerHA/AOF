package Monster;

public class Big_kA extends Monster {

	public Big_kA(int x, int y, int id) {
		super(x,y,id,"pics/monster/small/Big kA_30.png");
		this.atack = 10;
		this.defence = 10;
		this.examineText = "A very big ... kA ?";
		this.max_hp = 8000;
		this.mom_hp = 8000;
		this.name = "Big_kA";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"pics/monster/small/Big kA_100.png");
		this.walkingRadius = 10;
		this.sleepingTime = 1000;
		this.regenHP = 4000;
		this.regenTime = 10000;
		this.spawnTime = 1500;
		this.option1 = "Attack";
	}
	
	public void use () {
		attack();
	}
}
