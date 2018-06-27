package Tests;

/*
 * This class is not in use anymore. The information that was  shown in this class
 * has been added to the menu panel. This class' successor is  MainPackage.InfoPanel .
 */

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import MainPackage.MainApplet;

public class InfoPanel_Old extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JLabel name;
	JLabel lv;
	JLabel points;
	JLabel money;
	
	JLabel str;
	JLabel def;
	JLabel hp;
	JLabel space;
	
	public InfoPanel_Old () {
		super();
		setLayout(new GridLayout(2,4));
		
		
		/*boolean isPlayerNull = false;
		if (MainProg.actPlayer == null) {
			isPlayerNull = true;
		}
		
		MainProg.alert ("is player null : " + isPlayerNull);*/
		
		name   = new JLabel ("Name : " + MainApplet.actPlayer.getName());  // name won't change at all
		lv     = new JLabel ("");
		hp 	   = new JLabel ("");
		str    = new JLabel ("");
		def    = new JLabel ("");
		points = new JLabel ("");
		money  = new JLabel ("");
		
		space  = new JLabel ("");
		
		add(name);
		add(hp);
		add(str);
		add(points);
		add(lv);
		add(space);
		add(def);
		add(money);
	}
	
	public void refresh () {
		//System.out.println ("refreshing...");
		lv     .setText ("LV   : " + MainApplet.parseString(MainApplet.actPlayer.getLV()));
		hp 	   .setText ("HP : " + MainApplet.parseString(MainApplet.actPlayer.getMomHP()) + "/" + MainApplet.parseString(MainApplet.actPlayer.getMaxHP()));
		str    .setText ("Strength : " + MainApplet.parseString(MainApplet.actPlayer.getAtk()));
		def    .setText ("Defence  : " + MainApplet.parseString(MainApplet.actPlayer.getDef()));
		points .setText ("Points : " + MainApplet.actPlayer.getPoints());
		money  .setText ("Money  : " + MainApplet.actPlayer.getCoins());		
	}
	
	public void paint (Graphics g) {
		refresh ();
		super.paint(g);
	}
}
