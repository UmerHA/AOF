package localServer;

class NPCMover extends Thread  {
	boolean active = false;
	
	public void run() {
		while (active) {
			moveNPCs();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	void moveNPCs() {
		
	}
}
