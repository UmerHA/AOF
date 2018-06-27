package Monster;

public class Ankou extends Monster {

	public Ankou(int x, int y, int id) {
		super(x, y, id,"pics/monster/small/Ankou_30.gif");
		this.atack = 19;
		this.defence = 5;
		this.examineText = "This is a monster we stole from RuneScape, but don't tell anyone !";
		this.max_hp = 80;
		this.mom_hp = 80;
		this.name = "Ankou";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage(
				"pics/monster/small/Ankou_100.gif");
		this.walkingRadius = 7;
		this.sleepingTime = 2000;
		this.regenHP = 5;
		this.regenTime = 2000;
		this.spawnTime = 5000;
		this.option1 = "Attack";
	}
	
	public void killed (MainPackage.Player pl) {
		int r = MainPackage.MainApplet.getRandom(1, 5);
		if (r==1)
			pl.addItem(new Items.StrangePotion());
		if (r==2)
			pl.addItem(new Items.Platebody());
		if (r==3)
			pl.addItem(new Items.Sword());
		if (r==3)
			pl.addItem(new Items.Platelegs());
	}
	
	
	public void use () {
		attack();
	}
}
