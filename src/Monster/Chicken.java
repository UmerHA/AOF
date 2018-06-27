package Monster;

public class Chicken extends Monster {
	public Chicken (int x,int y,int id) {
		super(x,y,id,"pics/monster/small/chicken_30.png");
		this.atack = 1;
		this.defence = 1;
		this.examineText = "Let's see if I can catch this ...";
		this.max_hp = 10;
		this.mom_hp = 10;
		this.name = "Chicken";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage("pics/monster/small/chicken_100.png");
		this.walkingRadius = 15;
		this.sleepingTime = 100; 
		this.regenHP = 1;
		this.regenTime = 10000;
		this.spawnTime = 3000;
		this.option1 = "Attack";
	}
	
	public void use () {
		attack();
	}
}
