package localServer;

public class NPC {
	protected int walkingRadius;
	protected int sleepingTime;
	
	public int posX;
	public int posY;
	protected int spawnX;
	protected int spawnY;
	protected int delay = 0;
	protected float cow = 0.8F; //chance of walking
	
	public boolean inAction = false; // fighting for example
	public boolean active = false;
	
	public int id;
	
	public short dir = 0; // dirs : 0=not_moving;1=left;2=up;3=right;4=down
	

	public int getPosX () {return this.posX;}
	public int getPosY () {return this.posY;}
	public void setInAction (boolean state) {
		this.inAction = state;
	}
	public boolean isInAction () {
		return this.inAction;
	}

	public NPC (int x,int y, int id) {
		this.id = id;
		this.spawnX = x;
		this.spawnY = y;
		this.posX = x;
		this.posY = y;
	}
	public void setRadius (int i) {
		this.walkingRadius = i;
	}
	public void setSpeed (int pause) {
		this.sleepingTime = pause;
	}
	public void setDelay (int delay) {
		this.delay = delay;
	}	


	public static void main(String[] args) {
		try {
			Class<?> cls = Class.forName("Monster.Chicken");
			
			Util.log(cls.toGenericString());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
