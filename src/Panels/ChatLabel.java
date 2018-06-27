package Panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import MainPackage.MainApplet;

public class ChatLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	public final Color backCol  = new Color(180,165,215);
	public final Color foreColH = new Color(252,242,65);//fore color if active and hovered
	public final Color foreCol  = new Color(0,128,0);		//fore color if active
	public final Cursor hand  = new Cursor(Cursor.HAND_CURSOR);
	public final Cursor mouse = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private boolean active = false;
	
	public ChatLabel (String txt) {
		super(txt);
		
		setPreferredSize(new Dimension(200,25));
		setMinimumSize(new Dimension(200,25));		
		setOpaque(true);
		setBackground(backCol);
		
		if (!txt.trim().equals(""))
			setActive(true);
		
		refresh();
		addMouseListener(new mLis());
	}
	
	public void setActive (boolean bool) {
		this.active = bool;
		if (active) {
			setCursor(hand);
			this.setForeground(foreCol);
		} else {
			setCursor(mouse);
			this.setForeground(Color.BLACK);
		}
	}	
	public void setActive (String str) {
		if (str.trim().equals("0"))
			setActive(false);
		if (str.trim().equals("1"))
			setActive(true);
	}
	
	
	class mLis extends MouseAdapter {
		public void mouseEntered (MouseEvent e) {
			if (active)
				setForeground(foreColH);
			repaint();
		}
		public void mouseExited (MouseEvent e) {
			if (active) {
				setForeground(foreCol);
			} else {
				setForeground(Color.BLACK);
			}
			repaint();
		}
		public void mousePressed (MouseEvent e) {
			if (!active)
				return;
			
			MainApplet.getGamePanel().chatbox().input().setText("/w "+getText()+" ");
			MainApplet.getGamePanel().chatbox().input().grabFocus();
		}
	}
	public void refresh () {
		setActive(this.active);
	}
}
