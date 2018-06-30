package mainPackage.GamePanel;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import connection.Connector;
import mainPackage.ActionInfoPanelScroll;
import mainPackage.CustomPanel;
import mainPackage.MainApplet;

public class ChatBox extends CustomPanel {
	private static final long serialVersionUID = 1L;

	private ActionInfoPanelScroll output;
	private  JTextField input;
	
	private JTextField CVM_Field;
	private boolean CVM_PanelSize_Set;
	private boolean inCV = false;
	
	
	
	public ChatBox () {		
		output = new ActionInfoPanelScroll();
		input = new JTextField();
		CVM_Field = new JTextField("I'm the CVM_Field");
		
		setLayout(new BorderLayout());
		add(output,"Center");
		add(input,"South");
		
		/* falls auf input geklickt wird, holt es sich automatisch den focus --> kein mLis nötig
		 * output kann den focus nicht haben --> kein keyLis nötig
		 */
		output.addMouseListener(new mLis());
		input.addActionListener(new actLis());
		input.addKeyListener(new keyLis());
		
		Dimension d = new Dimension(400,150);
		setMinimumSize(d);
		setPreferredSize(d);
	}
	public void setBounds () {
		setBounds(0, MainApplet.HEIGHT()-125, 400, 125);
	}
	
	public JTextField input() {
		return input;
	}
	public ActionInfoPanelScroll output() {
		return output;
	}
	public JTextField CVM_Field() {
		return this.CVM_Field;
	}
	public void setInConversation (boolean bool) {
		if (bool) {
			remove(input);
			add(CVM_Field,"South");
			inCV = true;
		} else {
			input.setText("");
			remove(CVM_Field);
			add(input,"South");
			inCV = false;
		}
		validate();
		//repaint();
	}
	public boolean inConversation() {
		return this.inCV;
	}
	
	public void paint(Graphics g) {
		if (!CVM_PanelSize_Set) {
			// damit das normale JTextField und CVM-JTextField auch die gleiche Größe haben
			CVM_Field.setPreferredSize(input.getSize());
			CVM_PanelSize_Set = true;
		}
		
		output.repaint();
		input.repaint();
		CVM_Field.repaint();
	}
	public void addInfo (String txt) {
		output.addInfo(txt);
	}
	public void addRedInfo (String txt) {
		output.addRedInfo(txt);
	}
	public void addBlueInfo (String txt) {
		output.addBlueInfo(txt);
	}
	
	class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			if(!(input.getText().trim().equals(""))) {		
				SendMessage(input.getText());
			}
		}
	}
	class mLis extends MouseAdapter {
		public void mouseClicked (MouseEvent e) {
			if (inCV) {
				CVM_Field.grabFocus();
			} else {
				input.grabFocus();
			}
		}
	}
	class keyLis extends KeyAdapter {
		public void keyPressed (KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_F1)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 0);
			if (e.getKeyCode() == KeyEvent.VK_F2)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 1);
			if (e.getKeyCode() == KeyEvent.VK_F3)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 2);
			if (e.getKeyCode() == KeyEvent.VK_F4)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 3);
			if (e.getKeyCode() == KeyEvent.VK_F5)
				MainApplet.getGamePanel().menuPanel().setActivePanel((byte) 4);
		}
	}

	// Server-Client-Communication -->	
	private void SendMessage(String message) {
		if (message.length()>198)
			output.addInfo("Your messsge is too long.");
		if (message.length()<1)
			return;//just do nothing
			
		try {
			String txt = input.getText();
			String[] data = txt.split(" ");
			
			if (data[0].equals("/w")) {
				String name = data[1];
				int start = 4+data[1].length();
				String msg = txt.substring(start,txt.length());
				
				//System.out.println("whisper to " + name + " : \""+msg+"\"");
				Connector.Send("msw~"+name+"~"+msg+"#");
				output.addGreenInfo("To "+name+" : "+msg);
			} else {
				Connector.Send("mss~"+message+"#");
				output.addInfo(MainApplet.actPlayer.getName()+" : "+message);
			}
			input.setText("");
		} catch (NullPointerException e) {
			output.addInfo("You are not connected to the server.");
		}
	}
	//<--
}
