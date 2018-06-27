package MainPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import Connection.Connector;


public class TradeInvitation extends CustomPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lb;
	private JButton yes,no;
	private String name;
	
	// debug
	private boolean debugInvit = true;
	
	public TradeInvitation () {		
		lb = new JLabel(" wants to trade with you.");
		yes = new JButton("Accept");
		no = new JButton("Decline");
		
		JPanel south = new JPanel();
		south.setLayout(new GridLayout(1,2));
		south.add(yes);
		south.add(no);
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		center.add(lb,"North");
		center.add(new JSeparator(),"Center");
		center.add(south,"South");
		center.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(new Color(128,128,255), 2));
		add(center,"Center");
		
		yes.addActionListener(new actLis());
		no.addActionListener(new actLis());
		
		yes.setActionCommand("yes");
		no.setActionCommand("no");
		
		yes.setFocusable(false);
		no.setFocusable(false);
	}
	public void setBounds() {
		super.setBounds(250,30,250,60);
	}
	
	public void setName (String name, boolean sentByServer) {
		this.name = name;
		if (!sentByServer)
			return;
		lb.setText(name+" wants to trade with you.");
		if (debugInvit)
			System.out.println("TradeInvitation.setName :: name set to "+this.name);
	}
	public String getName () {
		return this.name;
	}
	
	public class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			setVisible(false);
			
			if (e.getActionCommand().equals("yes")) {
				Connector.Send("tinva~#");
				MainApplet.getGamePanel().tradeScreen().setVisible(true,name);
			}
			if (e.getActionCommand().equals("no"))
				Connector.Send("trinvd~#");
		}
	}
	
	public void setVisible(boolean bool) {
		if (debugInvit) {
			System.out.println("TradeInvit from :"+name);
			System.out.println("Setting visibility to "+bool);
			java.awt.Rectangle r = getBounds();
			System.out.println("Bounds : ("+r.x+" | "+r.y+" | "+r.width+" | "+r.height+")");
		}
		super.setVisible(bool);
		if (bool)
			focus();
	}
 	
}
