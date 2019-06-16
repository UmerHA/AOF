package unused.test;

/*
 * This class is not in use anymore. The information that was  shown in this class
 * has been added to the menu panel. This class' successor is  MainPackage.InfoPanel .
 */

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import client.App;

import javax.swing.JLabel;

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
		
		name   = new JLabel ("Name : " + App.actPlayer.getName());  // name won't change at all
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
		lv     .setText ("LV   : " + App.parseString(App.actPlayer.getLV()));
		hp 	   .setText ("HP : " + App.parseString(App.actPlayer.getMomHP()) + "/" + App.parseString(App.actPlayer.getMaxHP()));
		str    .setText ("Strength : " + App.parseString(App.actPlayer.getAtk()));
		def    .setText ("Defence  : " + App.parseString(App.actPlayer.getDef()));
		points .setText ("Points : " + App.actPlayer.getPoints());
		money  .setText ("Money  : " + App.actPlayer.getCoins());		
	}
	
	public void paint (Graphics g) {
		refresh ();
		super.paint(g);
	}
}
