package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import shops.Shop;

public class ShopPanel extends CustomPanel {
	private static final long serialVersionUID = 1L;
	private ShopSlot[] slots = new ShopSlot[36];
	private final int spaceTop = 30;
	private final int spaceLeft = 10;
	private JLabel nameLabel;
	private Shop shop;
	private JButton close;
	
	//double buffering
	private Image offImage;
	private Graphics offGraphics;
	
	private Color color = new Color(128,128,255);
	
	public ShopPanel() {
		setLayout(null);
		int num = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				slots[num] = new ShopSlot(num,this);
				slots[num].setBounds((j * 50)+spaceLeft, (i * 75)+spaceTop, 50,
						75);
			
				add(slots[num]);

				// System.out.println("MainPackage.ShopPanel.<init> : num = "+num);
				num++;
			}
		}
		int w=45;//width of button
		int h=20;//height of label and button
		
		nameLabel = new JLabel("<html><center>[Enter shop's name here]</center></html>");
		nameLabel.setOpaque(true);
		nameLabel.setBounds(spaceLeft, spaceTop-h, 9*50-w, h);
		add(nameLabel);
		
		close = new JButton("X");
		close.setOpaque(true);
		close.setBounds(spaceLeft+9*50-w, spaceTop-h, w, h);
		close.addActionListener(new actLis());
		add(close);
		
		setOpaque(true);
		setBackground(color);
		
		setDragAble(false);
		//System.out.println("ShopPanel : isOpaque = "+this.isOpaque());
	}
	public void setBounds () {
		setBounds(140,10, 470,340);
	}
	
	public void paint (Graphics g) {
		if (offImage == null) {
			offImage = createImage(getWidth(), getHeight());
			offGraphics = offImage.getGraphics();
		}
		super.paint(offGraphics);
		g.drawImage(offImage, 0, 0, this);
	}

	class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			closeShop();
		}
	}
	class mLis extends MouseAdapter {
		public void mouseEntered (MouseEvent e) {
			MainApplet.setFlyingInfo("Close shop");
		}
		public void mouseExited (MouseEvent e) {
			MainApplet.setFlyingInfo("");
		}
	}

	//-->
	public void openShop (Shop shop) {
		this.shop = shop;
		refresh();
		repaint();
		setVisible(true);
		
		MainApplet.actPlayer.setInAction(true);
	}
	public void closeShop() {
		nameLabel.setText("");
		shop = null;
		setVisible(false);
		MainApplet.getGamePanel().fieldScreen().grabFocus();
		MainApplet.actPlayer.setInAction(false);
	}
	public void refresh () {
		for (int i=0;i<36;i++) {
			try {
				slots[i].setItem(shop.getItem(i));
			} catch (NullPointerException e) {
				slots[i].setItem(new items.Empty());
			}
		}
	}

	public void setName (String name) {
		nameLabel.setText(name);
	}
	public int getPrice (int index) {
		return shop.getPrice(index);
	}
}
