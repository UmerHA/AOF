package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import client.items.Item;

public class ShopSlot extends JPanel {
	private static final long serialVersionUID = 1L;

	private Item item;
	private int id;
	private ShopPanel manager;

	private static Image backPic = Util.getImage("items/back.jpg");

	public ShopSlot(int i, ShopPanel manager) {
		this.setBackground(new Color(135, 75, 50));
		this.id = i;
		addMouseListener(new mLis());
		this.item = new client.items.Empty();
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
		//System.out.println("ShopSlot : repainting");
		
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
				String info = "\""+item.getExamineText()+"\" - This item will cost you "+manager.getPrice(id)+" coins.";
				App.addInfo(info);
			} else {
				int price = manager.getPrice(id);
				int firstEmptySlot = App.actPlayer.getInventoryManager().getFirstEmptySlot();
				
				if (App.actPlayer.getCoins()<price) {
					App.addInfo("You don't have enough money to buy that.");
					return;
				}
				if (firstEmptySlot == -1) {
					App.addInfo("You don't have enough space in your inventory to buy that.");
					return;
				}
				
				App.actPlayer.addCoins(-1*price);
				App.actPlayer.addItem(item);
			}
		}
		public void mouseEntered(MouseEvent e) {
			if (!item.getName().equals(""))
				App.getGamePanel().chatbox().input().setText(" "+item.getName()+" { Info / Buy }");
		}
		public void mouseExited(MouseEvent e) {
			App.getGamePanel().chatbox().input().setText("");
		}
	}
}
