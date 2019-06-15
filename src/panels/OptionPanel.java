package panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainPackage.ExternalPlayer;
import mainPackage.MainApplet;

public class OptionPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	JButton logOut;
	JLabel[] info = new JLabel[6];
	JButton clear;
	JButton showMap;
	JButton showShop;
	JButton npu;
	
	public OptionPanel () {
		setLayout(new GridLayout(10,1));
		setMinimumSize(new Dimension(250,450));
		setPreferredSize(new Dimension(250,450));
		
		logOut = new JButton("Log Out");
		info[0] = new JLabel("Hier kommen dann Optionen wie wir");
		info[1] = new JLabel("sie in setting reintuhen wollten");
		info[2] = new JLabel("Play Test Animation [Arrow]");
		info[3] = new JLabel("Play Test Animation [Sword Circle]");
		info[4] = new JLabel("Show trade Panel [debug]");
		npu = new JButton("new trade invitation");
		showMap = new JButton("Show Map [debug]");
		showShop = new JButton("Show Shop [debug]");
		clear = new JButton("Clear chat [debug]");
		
		for (int i=0;i<5;i++)
			add(info[i]);
		add(npu);
		add(showMap);
		add(showShop);
		add(logOut);
		add(clear);
		
		actLis actlis= new actLis();
		npu.addActionListener(actlis);
		logOut.addActionListener(actlis);
		clear.addActionListener(actlis);
		showMap.addActionListener(actlis);
		showShop.addActionListener(actlis);
		
		mLis mlis = new mLis();
		for (byte i=0;i<4;i++)
			info[i].addMouseListener(mlis);
		
		logOut.setFocusable(false);
		clear.setFocusable(false);
		showMap.setFocusable(false);
		showShop.setFocusable(false);
	}

	public Component add (Component comp) {
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout());
		temp.add(comp);
		super.add(temp);
		return comp;
	}
	
	class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("log out")) 
				MainApplet.actPlayer.logOut();
			if (e.getActionCommand().equalsIgnoreCase("clear chat [debug]")) 
				MainApplet.getGamePanel().chatbox().output().discardAllInfo();
			if (e.getActionCommand().equalsIgnoreCase("Show Map [debug]")) 
				MainApplet.getGamePanel().shopScreen().setVisible(false);
			if (e.getActionCommand().equalsIgnoreCase("Show Shop [debug]")) 
				MainApplet.getGamePanel().shopScreen().setVisible(true);
			if (e.getActionCommand().equalsIgnoreCase("new trade invitation")) {
				MainApplet.getGamePanel().invitScreen().setName("The Option Panel");
				MainApplet.getGamePanel().invitScreen().setVisible(true);
			}
		}
	}
	class mLis extends MouseAdapter {
		public void mousePressed (MouseEvent e) {
			String source = ((JLabel) e.getSource()).getText();
			
			System.out.println("OptionPanel$mLis :: source = |"+source+"|");
			
			if (source.equals("Show trade Panel [debug]")) {
				MainApplet.getGamePanel().invitScreen().setName("The OptionPanel", false);
				MainApplet.getGamePanel().tradeScreen().setVisible(true);
			} else if (source.equals("Play Test Animation [Sword Circle]")) {
				System.out.println("OptionPanel$mLis :: sending animation request [Sword Circle]");
				MainApplet.ANIManager().playAnimation(MainApplet.ANIManager().SWORD_CIRCLE, 200, 200);
			} else if (source.equals("Play Test Animation [Arrow]")) {
				System.out.println("OptionPanel$mLis :: sending animation request [Arrow]");
				MainApplet.ANIManager().playAnimation(MainApplet.ANIManager().ARROW, 200, 200);
			}
		}
	}

	public void refresh () {
		ExternalPlayer temp = ExternalPlayer.getPlayerByID(0);
		info[1].setText(temp.getName());
		info[1].setText(" X : " + temp.getMapX() + " - " + MainApplet.map.getMapX() + " = " +(temp.getMapX()-MainApplet.map.getMapX()));
		info[1].setText(" Y : " + temp.getMapY() + " - " + MainApplet.map.getMapY() + " = " +(temp.getMapY()-MainApplet.map.getMapY()));
	}


}
