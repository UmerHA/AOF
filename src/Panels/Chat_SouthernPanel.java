package Panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class Chat_SouthernPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton addFriend, delFriend;
	private JTextField txtField;
	private JLabel txt;
	private JPanel buttonP,inputP;
	
	private final String buttonPName = "Button Panel";
	private final String inputPName = "Input Panel";
	private CardLayout cardLayout;
	
	private byte action = 0;//0 = nothing; 1 = adding; 2 = deleting
	private ChatPanel parent;
	
	public Chat_SouthernPanel (ChatPanel parent) {
		this.parent = parent;		
		
		//create buttonPanel
		addFriend = new JButton("Add Friend");
		delFriend = new JButton("Remove Friend");
		addFriend.setFocusable(false);
		delFriend.setFocusable(false);
		buttonP = new JPanel();
		buttonP.setLayout(new GridLayout(1,2));
		buttonP.add(addFriend);
		buttonP.add(delFriend);
		
		//create inputPanel
		txt = new JLabel("Enter name : ");
		txtField = new JTextField();
		txtField.setActionCommand("Input Field");
		inputP = new JPanel();
		inputP.setLayout(new BorderLayout());
		inputP.add(txt,"West");
		inputP.add(txtField,"Center");
		
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		add(buttonP,buttonPName);
		add(inputP,inputPName);
		
		addFriend.addActionListener(new actLis());
		delFriend.addActionListener(new actLis());
		txtField.addActionListener(new actLis());
		txtField.addKeyListener(new keyLis());
	}
	public void askName (int actionType) {
		cardLayout.show(this, inputPName);
		txtField.grabFocus();
		action = (byte) actionType;
	}
	public void performAction () {
		if (action == 1)
			parent.addFriend(txtField.getText());
		if (action == 2)
			parent.removeFriend(txtField.getText());
		
		txtField.setText("");
		cardLayout.show(this, buttonPName);
	}
	private void showButtons () {
		txtField.setText("");
		cardLayout.show(this, buttonPName);
	}
	
	class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			String acmd = e.getActionCommand();
			
			System.out.println("ChatPanel$actLis :: acmd = "+acmd);
			
			if (acmd.equalsIgnoreCase("Add Friend"))
				askName(1);
			if (acmd.equalsIgnoreCase("Remove Friend"))
				askName(2);
				
			if (acmd.equalsIgnoreCase("Input Field"))
				performAction();
		}
	}
	class keyLis extends KeyAdapter {
		public void keyPressed (KeyEvent e) {
			if (e.getKeyCode() == 27) 
				showButtons();
		}
	}
}