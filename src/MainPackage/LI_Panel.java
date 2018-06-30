package MainPackage;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import javax.swing.JLabel;

import Connection.Connector;

public class LI_Panel extends JComponent {
	public static final long serialVersionUID = 1L;
	
	private JButton logInButton;
	private CustomTextField username;
	private JPasswordField password;
	
	private JLabel label;
	private int paintCount;
	private boolean debug = false;

	public LI_Panel () {		
		username = new CustomTextField("Username");
		password = new JPasswordField();
		logInButton = new JButton("Log In");

		setLayout(null);

		username.setBounds(325, 200, 80, 20);
		password.setBounds(325, 225, 80, 20);
		logInButton.setBounds(325, 250, 80, 20);

		add(username);
		add(password);
		add(logInButton);

		// test
		if (debug) {
			label = new JLabel("painting (0)");
			label.setBounds(0, 0, 100, 25);
			add(label);
		}
		
		username.addKeyListener(new keyLis(1));
		password.addKeyListener(new keyLis(2));
		logInButton.addKeyListener(new keyLis(3));
		logInButton.addActionListener(new actLis());
	}

	public void paint(Graphics g) {
		//System.out.println("LI_Panel.paint :: painting");
				
		if (debug) {
			paintCount++;
			label.setText("painting (" + paintCount + ")");
		}

		//System.out.println("LI_Panel :: painting imgae : "+MainApplet.getImage(0));
		//try {
		g.drawImage(MainApplet.getImage(0), 0, 0, this);
		//} catch (NullPointerException e) {
		//System.out.println("LI_Panel.paint :: "+g.toString());
		//System.out.println("LI_Panel.paint :: "+MainApplet.getImage(0).toString());
		//}
		
		username.repaint();
		password.repaint();
		logInButton.repaint();
		
		if (debug)
			label.repaint();
	}

	public void isNowActive () {
		password.setText("");
		username.grabFocus();
	}
	
	class actLis implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			if (!e.getActionCommand().equals("Log In")) 
				return;
			
			String uname = username.getText();
			String pw = new String(password.getPassword());

			Connector.Send("Loin~"+uname+"~"+pw+"#");		
		}
		
	}
	class keyLis extends KeyAdapter {
		private int type;
		public keyLis (int type) {
			super();
			this.type = type;
		}
		
		public void keyPressed (KeyEvent e) {
			//System.out.println("LI_Panel.keyLis :: key pressed : "+e.getKeyCode());
			
			if (type == 1) {
				switch (e.getKeyCode()) {
				case 10 : password.grabFocus();break;
				case 40 : password.grabFocus();break;
				case 38 : logInButton.grabFocus();break;
				}
			}
			if (type == 2) { 
				switch (e.getKeyCode()) {
				case 10 : logInButton.doClick();break;
				case 40 : logInButton.grabFocus();break;
				case 38 : customGrabFocus();break;
				}
			}
			if (type == 3) {
				switch (e.getKeyCode()) {
				case 10 : logInButton.doClick();break;
				case 32 : logInButton.doClick();break;
				case 40 : customGrabFocus();break;
				case 38 : password.grabFocus();break;
				}
			}
			
			if (e.isControlDown()) {
				switch (e.getKeyCode()) {
				case 78 : username.setText("niels");password.setText("jung");logInButton.doClick();break;
				case 85 : username.setText("umer");password.setText("adil");logInButton.doClick();break;
				}
			}
		}
		private void customGrabFocus () {
			if (username.getText().equals(""))
				username.selectAll();
			username.grabFocus();
		}
	}
}
