package MapFields; 

public class SpawnField extends MapField { 
	public SpawnField (int x, int y) {
		super (x,y,"pics/fields/grass.jpg");
		this.openForNPC = false; 
	} 
}