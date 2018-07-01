package mainPackage.trade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import items.Item;
import mainPackage.MainApplet;

public class TradeSlot extends JPanel {
	private static final long serialVersionUID = 1L;

	private Item item;
	private int id;
	private TradePanel manager;
	
	TradePanel getManager () {return manager;}

	private static Image backPic = MainApplet.getImage("items/back.jpg");

	public TradeSlot(int i, TradePanel manager) {
		this.setBackground(new Color(135, 75, 50));
		this.id = i;
		addMouseListener(new mLis());
		this.item = new items.Empty();
		this.manager = manager;
		
		setPreferredSize(new Dimension(50,75));
	}


	public int getID() {
		return this.id;
	}


	public void setItem(Item item) {
		this.item = item;
		this.item.setSlotID(this.id);
		repaint();
	}
	public Item getItem() {
		return this.item;
	}

	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());

		Image pic;
		pic = item.getImage();

		g.drawImage(backPic, 0, 0, this);
		g.drawImage(pic, 0, 0, this);
	}

	class mLis extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.getButton()==MouseEvent.BUTTON1) {
				item.examine();
			} else {
				
			}
		}
		public void mouseEntered(MouseEvent e) {
			if (!item.getName().equals(""))
				MainApplet.getGamePanel().chatbox().input().setText(" "+item.getName()+" { Info / Buy }");
		}
		public void mouseExited(MouseEvent e) {
			MainApplet.getGamePanel().chatbox().input().setText("");
		}
	}
}
