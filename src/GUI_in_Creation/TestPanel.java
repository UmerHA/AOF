package GUI_in_Creation;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/*
 * This class is used in the PanelSwitcher test.
 */

@SuppressWarnings("serial")
public class TestPanel extends JPanel {
	int type;
	public TestPanel (int type) {
		this.type = type;
		this.setBackground(new Color(120,95,5));
	}
	public void paint(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		
		switch (type) {
		case 1 : g.fillRect (105,105,100,100);break;
		case 2 : g.fillRect (5,5,100,100);break;
		case 3 :
		case 4 : g.fillOval (50,50,50,50);break;
		}
	}
}
