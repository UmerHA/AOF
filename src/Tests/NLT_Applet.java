package Tests;

import java.awt.BorderLayout;

import javax.swing.JApplet;

/* NLT = null layout test */

public class NLT_Applet extends JApplet {
	private static final long serialVersionUID = 1L;

	public void init () {
		NLT_TestPanel p = new NLT_TestPanel();
		setLayout(new BorderLayout());
		add(p,"Center");
	}
	public void start () {}
	public void stop () {}
	public void destroy () {} 
}
