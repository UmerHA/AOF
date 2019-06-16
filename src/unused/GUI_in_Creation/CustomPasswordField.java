package unused.GUI_in_Creation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/*
 * Not finished yet 
 */

public class CustomPasswordField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	private String defaultTXT = "";
	private char[] password = new char[100];
	private byte lenght = 0;
	private byte pointer = 0;

	public CustomPasswordField(String txt) {
		super(txt);
		this.defaultTXT = txt;
		addFocusListener(new fLis());
		addKeyListener(new kLis());
		addActionListener(new actLis());
	}

	public CustomPasswordField() {
		super();
		addFocusListener(new fLis());
		addKeyListener(new kLis());
		addActionListener(new actLis());
	}
	
	public char[] getPassword () {
		char[] pw = new char[lenght];
		for (int i=0;i<lenght;i++)
			pw[i] = password[i];
		
		System.out.println("password"+new String(pw));
		return pw;
	}

	class fLis implements FocusListener {
		public void focusGained(FocusEvent e) {
			if (getText().equals(defaultTXT))
				setText("");
		}

		public void focusLost(FocusEvent e) {
			if (getText().equals(""))
				setText(defaultTXT);
		}

	}
	class kLis extends KeyAdapter {
		public void keyPressed (KeyEvent e) {
			switch (e.getKeyCode()) {
			case 37 : pointer--;break;
			case 39 : pointer++;break;
			}
			if (pointer<0)
				pointer = 0;
			if (pointer>lenght)
				pointer=lenght;
			
			System.out.println(pointer +" / "+lenght+ " / " +password.length);
		}
		
		
		public void keyTyped(KeyEvent e) {
			System.out.println(pointer +" / "+lenght+ " / " +password.length);
			if (e.getKeyCode() == 27) {
				lenght--;
				return;
			}
			
			password[pointer] = (char) e.getKeyChar();
			lenght ++;
			pointer++;
		}
	}
	
	
	//test
	public static void main (String[] args) {
		javax.swing.JFrame f = new javax.swing.JFrame();
		@SuppressWarnings("unused")
		javax.swing.JButton b = new javax.swing.JButton("Show password");
		
		CustomPasswordField c1 = new CustomPasswordField("password");
		CustomPasswordField c2 = new CustomPasswordField("password");
		
		f.setLayout(new java.awt.GridLayout(1,3));
		f.add(c1);
		f.add(new javax.swing.JPanel());
		f.add(c2);
		f.setVisible(true);
		f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		f.setSize(new java.awt.Dimension(200,150));
	}
	
	class actLis implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			/*System.out.println(*/getPassword()/*)*/;
		}
	}
}