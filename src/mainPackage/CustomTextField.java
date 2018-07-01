package mainPackage;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;


public class CustomTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	private String defaultTXT = "";
	
	public CustomTextField (String txt) {	
		super(txt);
		this.defaultTXT = txt;
		addFocusListener(new fLis());
	}
	public CustomTextField () {
		super();
		addFocusListener(new fLis());
	}
	
	public String getText () {
		if (super.getText().equals(defaultTXT))
		   return "";
		
		return super.getText();
	}

	
	class fLis implements FocusListener {
		public void focusGained(FocusEvent e) {
			if (getText().equals(""))
				setText("");
		}

		public void focusLost(FocusEvent e) {
			if (getText().equals(""))
				setText(defaultTXT);	
		}		
	}
}
