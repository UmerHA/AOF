package client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import client.npc.Monster;


public class CombatWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private PicPanel ppp;
	private PicPanel ppe;

	private InfoBar plInfo;
	private InfoBar monInfo;

	//private JButton btn1;
	//private JButton btn2;

	// private JButton attack;
	// private JButton flee;

	private JLabel plLabel;
	private JLabel monLabel;

	private Player pl;
	private Monster mon;

	private PicButton attack;
	private PicButton flee;
	
	private actLis myActLis = new actLis ();

	public CombatWindow(Player pl, Monster mon) {
		this.pl = pl;
		this.mon = mon;

		this.mon.setInAction(true);
		this.mon.setCBWin(this);

		// modify window + show window :
		setBackground(Color.WHITE);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(700, 450);

		ppp = new PicPanel();
		ppe = new PicPanel();
		plInfo = new InfoBar(pl.getMomHP(), pl.getMaxHP());
		monInfo = new InfoBar(mon.getMomHP(), mon.getMaxHP());
		//btn1 = new JButton("damage player");
		//btn2 = new JButton("damage monster");
		plLabel = new JLabel(pl.getMomHP() + " / " + pl.getMaxHP());
		monLabel = new JLabel(mon.getMomHP() + " / " + mon.getMaxHP());
		// attack = new JButton("Atack");
		// flee = new JButton("Flee");

		ppp.setBounds(25, 25, 100, 100);
		ppe.setBounds(575, 25, 100, 100);
		plInfo.setBounds(140, 25, 175, 25);
		monInfo.setBounds(375, 25, 175, 25);
		//btn1.setBounds(100, 200, 250, 20);
		//btn2.setBounds(400, 200, 250, 20);
		plLabel.setBounds(140, 60, 100, 20);
		monLabel.setBounds(375, 60, 100, 20);
		// attack.setBounds(300, 300, 100, 100);
		// flee.setBounds(25,350,150,50);

		//btn1.setFocusable(false);
		//btn2.setFocusable(false);
		// attack.setFocusable(false);
		// flee.setFocusable(false);

		//btn1.addActionListener(new actLis());
		//btn2.addActionListener(new actLis());
		// attack.addActionListener(new actLis());
		// flee.addActionListener(new actLis());

		monInfo.changeDirection();

		add(ppp);
		add(ppe);
		add(plInfo);
		add(monInfo);
		//add(btn1);
		//add(btn2);
		add(plLabel);
		add(monLabel);
		// add(atack);
		// add(flee);

		addWindowListener(new winLis());

		// --> Atttack PicButton :
		PicButton.setDirectory("pics/cbWin/");
		attack = new PicButton("in.jpg", "inClick.jpg", "sword.png");
		attack.setBounds(300, 300, 100, 100);
		attack.setFont(new Font("SansSerif", Font.BOLD, 18));
		attack.setText("Attack", 25, 22);
		attack.setActionCommand("Attack");
		attack.addActionListener(myActLis);
		add(attack);
		// <--
		// --> Flee PicButton ;
		PicButton.setDirectory("pics/cbWin/");
		flee = new PicButton("fleeIn.jpg", "fleeInClick.jpg");
		flee.setBounds(25, 350, 150, 50);
		flee.setFont(new Font("SansSerif", Font.BOLD, 35));
		flee.setText("Flee", 40, 35);
		flee.setActionCommand("Flee");
		flee.addActionListener(myActLis);
		add(flee);
		// <--

		setTitle ("AOF : Project 1");
		setVisible(true);
		
		
		plInfo.setMom(pl.getMomHP());
		plInfo.setMax(pl.getMaxHP());
		monInfo.setMom(mon.getMomHP());
		monInfo.setMax(mon.getMaxHP());
		ppp.setImage(pl.getImage());
		ppe.setImage(mon.getImage100());
	}

	public void refresh() {
		plInfo.setMom(pl.getMomHP());
		plLabel.setText(pl.getMomHP() + " / " + pl.getMaxHP());
		monInfo.setMom(mon.getMomHP());
		monLabel.setText(mon.getMomHP() + " / " + mon.getMaxHP());
	}


	class actLis implements ActionListener {
		private boolean fighting = false;
		private WaitThread waitThread;
		
		public void actionPerformed(ActionEvent e) {
			String acmd = e.getActionCommand();
			// System.out.println("Button pressed...");
			/*
			 * Code for using the "damage monster" and "damage player" buttons which may not be used now :
			 * 
			 * if (acmd.equals("damage player")) { pl.changeHP(-5); if
			 * (pl.getMomHP() <= 0) { setVisible(false); pl.kill(this); } } else
			 * if (acmd.equals("damage monster")) { mon.changeHP(-5);
			 * //System.out.println("hp of mon:"+mon.getMomHP()); if
			 * (mon.getMomHP() <= 0) { setVisible(false); mon.kill(this); } }
			 */

			if (acmd.equals("Attack")) {
				if (fighting) {
					System.out.println("CombatWindow$actLis.actionPerformed : I'm still fighting");
				}
				fighting = true;
				
				// Monster angreifen :
				attackMonster();
				
				// etwas warten und dann player angreifen : 
				waitThread = new WaitThread();
				waitThread.start();				
			}

			if (acmd.equals("Flee")) {
				pl.addPoints(-150);	// Running away decreases points
				setVisible(false);
			}

			//refresh();
		}
	
		void attackMonster () {
			int dmg = 0 - getDamage(pl.getTotalAtk(), mon.getDef());
			mon.changeHP(dmg);
			pl.addXP("Attack",(0-dmg)* 2);
			//System.out.println ("CombatWindow$actLis.attackMonster : you deal " + (0-dmg) + " damage");
			if (mon.getMomHP() <= 0) {
				//System.out.println ("CombatWindow$actLis.attackMonster : pts = " + mon.getCombatLV());
				pl.addPoints(mon.getCobmatLV());	// Winning a battle earns points
				setVisible(false);
				mon.kill(this);
				mon.killed(pl);
			}
			refresh();
		}
		void attackPlayer () {
			int dmg;
			dmg = 0 - getDamage(mon.getAtk(), pl.getTotalDef());
			pl.changeHP_noCheck(dmg, this);
			//System.out.println ("CombatWindow$actLis.actionPerformed : you loose " + (0-dmg) + " HP");
			if (pl.getMomHP() <= 0) {
				pl.addPoints(-50); 	// by being killed you also loose points, but not as much as trough fleeing
									// Cause death in a battle is more honourable than running away (SPARTA !) ^^
				setVisible(false);
				pl.kill(this);
			}
			refresh();
		}

		void destroyWaitThread () {
			waitThread = null;
			fighting = false;
		}
	}

	class winLis extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			mon.setInAction(false);
			pl.destroyCbWin();
		}
	}

	public void setVisible(boolean state) {
		if (!state) {
			//System.out.println ("CombatWindow.setVisible : state = false");
			//System.out.println ("CombatWindow.setVisible : this.pl.getPoints() = " + this.pl.getPoints());
			this.mon.destroyCbWin();
			this.mon.setInAction(false);
			this.pl.destroyCbWin();
			this.pl.setInAction(false);
			this.myActLis.destroyWaitThread();
		} else {
		}
		
		super.setVisible(state);
	}

	private int getDamage(int a, int b) {
		// a = ATK of attacker
		// b = DEF of defender
		int r = (int) (Math.random() * 150);
		int power = (a * r) / 100;
		int damage = power - b;
		if (damage < 0)
			damage = 0;

		// System.out.println ("power = " + a + " * " + r + " / 100 = " + (a*r)
		// + " / 100 = " + power);
		// System.out.println ("damage = " + power + " - " + b + " = " +
		// damage);
		// System.out.println ();

		return damage;
	}

	class WaitThread extends Thread {		
		public void run() {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
			}
			myActLis.attackPlayer();
			myActLis.destroyWaitThread ();
		}
	}
}
