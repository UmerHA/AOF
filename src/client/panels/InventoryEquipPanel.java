package client.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import client.App;
import client.connection.Connector;

public class InventoryEquipPanel extends JPanel {
	private static final long serialVersionUID = 1L;
		
	private InventorySlot[] slots = new InventorySlot[10];
	/* 0 : helmet
	 * 1 : cape
	 * 2 : chain
	 * 3 : weapon
	 * 4 : body
	 * 5 : shield
	 * 6 : ring
	 * 7 : legs
	 * 8 : gloves
	 * 9 : boots
	 */
	
	private int[] slotX = new int[10];
	private int[] slotY = new int[10];
	
	private InventoryManager manager;
	
	private final int spcX = 0/*MainProg.SPACE_FOR_PANEL_X*/;	// space X
	private final int spcY = 0/*MainProg.SPACE_FOR_PANEL_Y*/;	// space Y
	
	private boolean painted = false;
	
	public void construct () {
		setMinimumSize(new Dimension(250,450));
		setPreferredSize(new Dimension(250,450));
		
		// <-- Do not change !! :
		slotX[0] = 100;
		slotX[1] =  25;
		slotX[2] = 100;
		slotX[3] =  25;
		slotX[4] = 100;
		slotX[5] = 175;
		slotX[6] =  25;
		slotX[7] = 100;
		slotX[8] = 175;
		slotX[9] = 100;
		
		slotY[0] =  12;
		slotY[1] = 100;
		slotY[2] = 100;
		slotY[3] = 187;
		slotY[4] = 187;
		slotY[5] = 187;
		slotY[6] = 275;
		slotY[7] = 275;
		slotY[8] = 275;
		slotY[9] = 362;
		// -->
		
		setLayout (null);
		
		for (short i=0;i<10;i++) {
			slots[i] = new InventorySlot(i);
			slots[i].setItem(new client.items.Empty());
			slots[i].setOption1("Unequip");
			slots[i].setIsEquip(true);
			slots[i].setBounds(slotX[i]+spcX,slotY[i]+spcY,50,75);
			slots[i].setManager(manager);
			add(slots[i]);
		}		
		
		slots[5].setEnabled(manager.isShieldSlotEnabled());
		
		addMouseListener (new mLis());
	}

	public InventoryEquipPanel () {
		construct ();
	}	
	public InventoryEquipPanel (InventoryManager manager) {
		this.manager = manager;
		construct ();
	}
	
	public void setManager (InventoryManager manager) {
		this.manager = manager;
		for (short i=0;i<10;i++)
			slots[i].setManager(manager);
		refresh();
	}
	
	
	public void paint (Graphics g) {
		if (!painted) {
			Connector.Send("equilg~#");
			painted = true;
		}
		
		refresh();
		
		//g.drawImage(backImage, 10, 10, this);
		g.setColor(new Color (229,170,122));
		g.fillRect(spcX, spcY, 249, 449);
		g.setColor(new Color(151,92,48));
		g.drawRect(spcX, spcY, 249, 449);
		
		Image backPic = java.awt.Toolkit.getDefaultToolkit().getImage("pics/items/back2.jpg");
		Image backPicC = java.awt.Toolkit.getDefaultToolkit().getImage("pics/items/back2_cross.jpg");
		
		for (short i=0;i<10;i++) {
			if (i == 5) {
				if (manager.isShieldSlotEnabled()) {
					g.drawImage(backPic, slotX[i]+spcX, slotY[i]+spcY, this);
					g.drawImage(slots[i].getForeImage(), slotX[i]+spcX, slotY[i]+spcY, this);
				} else {
					g.drawImage(backPicC, slotX[i]+spcX, slotY[i]+spcY, this);
				}
			} else {
				g.drawImage(backPic, slotX[i]+spcX, slotY[i]+spcY, this);
				g.drawImage(slots[i].getForeImage(), slotX[i]+spcX, slotY[i]+spcY, this);
			}
		}
		
		// <-- lines between the slots :
		g.fillRect(124+spcX, 87+spcY, 2, 13);
		g.fillRect(124+spcX,175+spcY, 2, 12);
		g.fillRect(124+spcX,262+spcY, 2, 13);
		g.fillRect(124+spcX,350+spcY, 2, 12);
		
		g.fillRect( 49+spcX,262+spcY, 2, 13);
		g.fillRect(199+spcX,262+spcY, 2, 13);
		
		g.fillRect( 75+spcX,137+spcY, 25, 2);
		g.fillRect( 75+spcX,224+spcY, 25, 2);
		g.fillRect(150+spcX,224+spcY, 25, 2);
		// -->
	}

	class mLis extends MouseAdapter {
		public void mouseClicked (MouseEvent e) {
			System.out.println ("Total ATK Bonus : "+manager.getTotalAttackBonus()
					+"   |   "
					+"Total DEF Bonus : "+manager.getTotalDefenceBonus()
					+"   |   "
					+"Total weight : "+manager.getTotalWeight()
					+"   |   "
					+"is shield slot enabled : "+manager.isShieldSlotEnabled()
			);
		}
	}

	public void setEnabled (boolean state, int position) {
		slots[position].setEnabled(state);
	}

	public void refresh() {
		for (short i=0;i<10;i++)
			slots[i].setItem(App.actPlayer.getInventoryManager().getItemEq(i));
	}
 }
