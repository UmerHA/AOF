package mainPackage.Trade;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import connection.Connector;
import items.Item;
import mainPackage.CustomPanel;
import mainPackage.MainApplet;
import panels.InventoryManager;

public class TradePanel extends CustomPanel {
	private static final long serialVersionUID = 1L;
	
	private InventoryManager manager;
	private TradeSlot[] slots = new TradeSlot[24];// 12 per trading partner
	
	private JButton accept,decline;
	private JLabel label;
	private Color color = new Color(128,128,255);
	private final static String lbTxt = "Trading with : ";
	
	private boolean isTrading = false;
	
	public InventoryManager getManager () {
		return manager;
	}
	
	public TradePanel () {
		this.manager = MainApplet.actPlayer.getInventoryManager();
		
		setLayout(null);
		
		setDragAble(false);
		setOpaque(true);
		setBackground(color);
		
		accept = new JButton("Accept");
		decline = new JButton("Decline");
		label = new JLabel(lbTxt);
		for (int i=0;i<24;i++) {
			slots[i] = new TradeSlot(i, this);
			add(slots[i]);
		}
			
		byte count = 0;
		for (byte i=0;i<4;i++) {
			for (byte j=0;j<3;j++) { 
				slots[count].setBounds(i*50+10, j*75+35, 50, 75);
				count++;
			}
		}
			
		for (byte i=0;i<4;i++) { 
			for (byte j=0;j<3;j++) {
				slots[count].setBounds(i*50+225, j*75+35, 50, 75);
				count++;
			}
		}
		
		accept.setBounds(10, 270, 200, 25);
		decline.setBounds(225, 270, 200, 25);
		label.setBounds(10,12,415,14);
		add(accept);
		add(decline);
		add(label);
		
		label.setOpaque(true);
		label.setBackground(new Color(180,180,180));
		
		actLis actlis = new actLis();
		accept.addActionListener(actlis);
		decline.addActionListener(actlis);
		
		accept.setActionCommand("Accept");
		decline.setActionCommand("Decline");
	}
	public void setBounds () {
		setBounds(0, 0, 435,305);
	}

	public void setVisible (boolean bool, String name) {
		setVisible(bool);
		
		if (name.equals("a player"))
			name = MainApplet.getGamePanel().invitScreen().getName();
		
		label.setText(lbTxt+name);
	}
	public void setVisible(boolean bool) {
		super.setVisible(bool);
		MainApplet.actPlayer.setInAction(bool);
		isTrading = bool;
		
		try {
			MainApplet.getGamePanel().menuPanel().setFixShowing(bool);
		} catch (NullPointerException npe) {//this will ocur once while initing the GamePanel
			//just ognore the npe
		}
	}
	public boolean isTrading () {
		return isTrading;
	}

	public Item getOwnItem (int id) {
		return slots[id].getItem();
	}
	public Item getOtherItem (int id) {
		return slots[id+12].getItem();
	}
	
	
	class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if (e.getActionCommand().equals("Accept")) {
				Connector.Send("trinva~#");
				accept.setText("Waiting for other player");
			} else if (e.getActionCommand().equals("Decline")) {
				Connector.Send("trinvd~#");
				setVisible(false);
			}
		}
	}
	
	/* - called by server - */
	public void updateOpponentInv (Item item) {
		
	} 
}
