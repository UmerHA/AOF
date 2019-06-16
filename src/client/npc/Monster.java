package client.npc;

import java.awt.Image;

import client.App;
import client.CombatWindow;
import client.Util;

public class Monster extends NPC {
	protected bThread regenThread;
	protected cThread birthThread;

	protected short max_hp;
	protected short mom_hp;

	protected short defence;
	protected short atack;

	protected int regenTime;
	protected int regenHP;

	protected boolean isDead = false;
	protected int spawnTime;

	protected CombatWindow cbWin;

	public Monster(int x, int y, int id, String imageName) {
		super(x, y, id, imageName);
		this.regenThread = new bThread();
	}

	protected static Image getImage100(String imageName) {
		return Util.getImage("monster/small/" + imageName);
	}

	class bThread extends Thread {
		public void run() {
			try {
				sleep(delay);
			} catch (InterruptedException e) {
			}

			while (true) { // Endlos-Schleife
				try {
					sleep(regenTime);
				} catch (InterruptedException e) {
				}
				if (!isDead) {
					changeHP(regenHP);
					if (cbWin != null)
						cbWin.refresh();
					// System.out.println(this.getName() + "says : regenerating
					// : " + regenHP + " HP gained");
				}
			}
		}
	}

	class cThread extends Thread {
		public void run() {
			// keine Endlos-Schleife!
			System.out.println("getting ready for a new life ...");

			try {
				sleep(spawnTime);
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
			mom_hp = max_hp;
			mapX = spawnX;
			mapY = spawnY;
			posX = spawnX + App.map.getMapX();
			posY = spawnY + App.map.getMapY();
			System.out.println("starting a new life");
			App.map.repaint();

			setIsDead(false);
		}
	}

	public void setActive(int delay) {
		// super.setActive(delay);
		regenThread.start();
	}

	public int getAtk() {
		return this.atack;
	}

	public int getDef() {
		return this.defence;
	}

	public int getCobmatLV() {
		return (max_hp + defence + atack) / 2;
	}

	public int getMomHP() {
		return (int) this.mom_hp;
	}

	public int getMaxHP() {
		return (int) this.max_hp;
	}

	public void changeHP(int difference) {
		this.mom_hp += difference;

		if (this.mom_hp >= this.max_hp)
			this.mom_hp = this.max_hp;
		if (this.mom_hp <= 0) {
			// System.out.println (this.getName() + " ( " + this.id + " ) : I
			// died");
			this.mom_hp = 0;
			this.cbWin.setVisible(false);
		}
	}

	public void setCBWin(CombatWindow cbWin) {
		this.cbWin = cbWin;
	}

	public void destroyCbWin() {
		this.cbWin = null;
	}

	public void kill(Object sender) {
		String cos = sender.getClass().toString(); // cos = class of sender
		cos = cos.substring(6, cos.length());
		// System.out.println("NPC.kill : cos = " + cos);
		// Sender wird überprüft, damit nicht nicht jede Klasse den NPC killen
		// kann, sonst wärs ja zu easy für Hacker (dich,Niels)
		if (cos.equals("MainPackage.CombatWindow$actLis"))
			;
		setIsDead(true);
	}

	private void setIsDead(boolean state) {
		isDead = state;
		if (isDead) {
			App.map.fields[this.mapX][this.mapY].free(); // besetztes
																// Feld
																// freigeben ...
			System.out.println("NPC.setIsDead : died ...");
			App.map.repaint();
			birthThread = new cThread();
			birthThread.start();
		} else {
			birthThread = null;
		}
	}

	protected void attack() {
		App.actPlayer.startFightWith(this);
	}

	public void killed(client.Player pl) {
	}

	public Image getImage30() {
		if (isDead)
			return deadImage;
		return this.Img30;
	}
}
