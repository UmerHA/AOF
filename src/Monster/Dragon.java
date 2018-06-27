package Monster;

public class Dragon extends Monster {

	public Dragon (int x,int y,int id) {
		super(x,y,id,"pics/monster/small/red_dragon_30.jpg");
		this.atack = 125;
		this.defence = 110;
		this.examineText = "One of the most powerful creatures the world has ever seen.";
		this.max_hp = 180;
		this.mom_hp = 180;
		this.name = "Dragon";
		this.Img100 = java.awt.Toolkit.getDefaultToolkit().getImage("pics/monster/small/red_dragon_100.jpg");
		this.walkingRadius = 5;
		this.sleepingTime = 2500; //50
		this.regenHP = 5;
		this.regenTime = 3000;
		this.spawnTime = 90000;
		this.option1 = "Attack";
	}
	
	public void killed (MainPackage.Player pl) {
		int r = MainPackage.MainApplet.getRandom(1, 20);
		
		switch (r) {
			case 1 : 
			case 2 :
			case 3 :
			case 4 : pl.addCoins(MainPackage.MainApplet.getRandom(50, 350));break;
			case 5 : pl.addItem(new Items.SlayersPlatelegs());break;
			case 6 : pl.addItem(new Items.SlayersGloves());
			case 7 :
			case 8 :
			case 9 : pl.addItem(new Items.BigHealthPotion_3());break;
			case 10 :
			case 11 : pl.addItem(new Items.WoodShield());break;
			default : pl.addCoins(250);
		}
	}
	
	
	public void use () {
		attack();
	}
}
