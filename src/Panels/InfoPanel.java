package Panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

import MainPackage.MainApplet;


public class InfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JLabel name;
	JLabel lv;
	JLabel points;
	JLabel money;
	
	JLabel str;
	JLabel def;
	JLabel hp;
	
	public InfoPanel () {
		super();
		setLayout(new GridLayout(7,1));

		setMinimumSize(new Dimension(250,450));
		setPreferredSize(new Dimension(250,450));
		
		/*boolean isPlayerNull = false;
		if (MainProg.actPlayer == null) {
			isPlayerNull = true;
		}
		
		MainProg.alert ("is player null : " + isPlayerNull);*/
		
		name   = new JLabel ();  // even the name will change - so trust nobody
		lv     = new JLabel ("");
		hp 	   = new JLabel ("");
		str    = new JLabel ("");
		def    = new JLabel ("");
		points = new JLabel ("");
		money  = new JLabel ("");
		
		add(name);
		add(hp);
		add(str);
		add(points);
		add(lv);
		add(def);
		add(money);
	}
	
	public void refresh () {
		//System.out.println ("refreshing...");
		name   .setText ("Name : " + MainApplet.actPlayer.getName());
		lv     .setText ("LV   : " + MainApplet.parseString(MainApplet.actPlayer.getLV()));
		hp 	   .setText ("HP : " + MainApplet.parseString(MainApplet.actPlayer.getMomHP()) + "/" + MainApplet.parseString(MainApplet.actPlayer.getMaxHP()));
		str    .setText ("Strength : " + MainApplet.parseString(MainApplet.actPlayer.getAtk()));
		def    .setText ("Defence  : " + MainApplet.parseString(MainApplet.actPlayer.getDef()));
		points .setText ("Points : " + MainApplet.actPlayer.getPoints());
		money  .setText ("Money  : " + MainApplet.actPlayer.getCoins());		
	}
	public JLabel getNameLabel () {
		return name;
	}
	
	public void paint (Graphics g) {
		refresh ();
		super.paint(g);
	}
}
