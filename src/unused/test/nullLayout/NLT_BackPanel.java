package unused.test.nullLayout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import java.security.AccessControlException;

@SuppressWarnings("serial")
public class NLT_BackPanel extends JPanel {
	private Image backPic;
	
	public NLT_BackPanel () {
		try {
		backPic = java.awt.Toolkit.getDefaultToolkit().getImage("Landscape.jpg");
		} catch (AccessControlException ace){
			//do nothing
		}
	}
	public void paint (Graphics g) {
		try {
		g.drawImage(backPic, 0, 0, this);
		} catch (NullPointerException npe) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
