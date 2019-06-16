package client.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
//import java.util.Calendar;

import javax.swing.JPanel;

import client.App;
import client.Util;
import client.connection.Connector;
import client.items.Item;

public class InventorySlot extends JPanel {
	private static final long serialVersionUID = 1L;

	private Item item;
	private int id;
	private String option1 = "Use";
	private String option2 = "Examine";
	private boolean option1Changed = false;

	private boolean isEquip = false;
	private boolean isEnabled = true;
	private Image NotEnabledImage = Util.getImage("items/back2_cross.jpg");

	private static Image backPic = Util.getImage("items/back.jpg");

	/*
	private long time1;
	private long time2;
	 */

	private InventoryManager manager;

	private Image offScreen;
	
	
	public InventorySlot(int i) {
		this.setBackground(new Color(135, 75, 50));
		this.id = i;
		addMouseListener(new mLis());
		addMouseMotionListener(new mmLis());

		//time1 = Calendar.getInstance().getTimeInMillis();

		//System.out.println("InventorySlot.<init> :: my id = " + id);
	}

	public void setManager(InventoryManager manager) {
		this.manager = manager;
	}

	public int getID() {
		return this.id;
	}
	public Image getBackImage() {
		if (isEnabled) {
			return backPic;
		} else {
			return NotEnabledImage;
		}
	}
	public Image getForeImage() {
		if (isEnabled) {
			return item.getImage();
		} else {
			return new client.items.Empty().getImage();
		}
	}

	public void setItem(Item item) {
		// System.out.println
		// ("InevtnroySlot.setItem :: new item : "+item.getName());
		this.item = item;
		this.item.setSlotID(this.id);
		repaint();
	}

	public Item getItem() {
		return this.item;
	}

	public void setOption1(String str) {
		this.option1 = str;
		this.option1Changed = true;
	}

	public void setIsEquip(boolean bool) {
		this.isEquip = bool;
	}
	public boolean getIsEquip() {
		return this.isEquip;
	}

	public void paint(Graphics g) {
		//System.out.println("InventorySlot.paint :: my id = " + id);
	
		Dimension d = getSize();
		if (offScreen == null)
			offScreen = createImage(d.width, d.height);
		
		offScreen.getGraphics().setColor(getBackground());
		offScreen.getGraphics().fillRect(0, 0, d.width, d.height);
		
		Image pic;
		pic = item.getImage();

		offScreen.getGraphics().drawImage(backPic, 0, 0, this);
		offScreen.getGraphics().drawImage(pic, 0, 0, this);

		g.drawImage(offScreen, 0, 0, null);	
	}

	class mLis extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				boolean isDoubleClick;

				/*
				time2 = e.getWhen();
				if ((time2 - time1) <= 500) {
					isDoubleClick = true;
					// System.out.println
					// ("OP_GB.InventorySlot :: time2 - time1 : "+(time2-time1));
				} else {
					isDoubleClick = false;
				}
				time1 = time2;
				*/

				// <-- Delete after finding another solution
				if (e.isControlDown()) {
					isDoubleClick = true;
				} else {
					isDoubleClick = false;
				}
				//

				// linke Maustaste : Doppelklick
				if (!isDoubleClick) {
					
					System.out.println("Clicked ; trading : "+App.getGamePanel().tradeScreen().isTrading());
					if (App.getGamePanel().tradeScreen().isTrading()) {
						Connector.Send("trs~1~1#");
						
						return;
					}
					
					
					// code to use item
					item.use();

					if (item.getEquipZone() >= 0) {
						if (!isEquip) {
							manager.swapItems(getID(), item.getEquipZone());
						} else {
							manager.ejectItem(getID());
						}
					}
				}

				// linke Maustase : Einzelklick
				else if (isDoubleClick) {
					if (!isEquip) {
						manager.setFlyingItem(getID());
					}
				}
			}
			// rechte Maustaste
			if (e.getButton() == MouseEvent.BUTTON3) {
				App.addInfo(item.getExamineText());
			}

			// System.out.println("First empty slot : "+
			// MainProg.actPlayer.getInventoryManager().getFirstEmptySlot());
			// System.out.println("Is equip : " + getIsEquip());
		}

		public void mouseEntered(MouseEvent e) {
			if (!item.getName().equals("")) {
				String title = "  " + item.getName() + " : ";
				if (option1Changed) {
					title += option1;
				} else {
					title += item.getOption1();
				}
				App.setFlyingInfo(title + " / " + option2);
			}
		}

		public void mouseExited(MouseEvent e) {
			App.getGamePanel().chatbox().input().setText("");
		}
	}
	class mmLis extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {
			App.getGamePanel().invScreen().setLocationOfCursor(e.getX(),
					e.getY(), getID());
		}
	}

	public static Point getPositionFromID(int id) {
		int y = (int) id / 5;

		int x = (y * 5) - 1;
		x = id - x;
		x--;
		return new Point(x, y);
	}

	public void setEnabled(boolean state) {
		this.isEnabled = state;
		repaint();

		// System.out.println ("Setting my enability : "+this.isEnabled);
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

}