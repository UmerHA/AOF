package Tests;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AGT_Slot extends JPanel {
	int id;
	
	public AGT_Slot(int num) {
		id = num;
	}
	
	public void paint (Graphics g) {
		g.setColor(new Color(255-id*4,255-id*3,255-id*5));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(id,id*4,id*6));
		g.fillOval(15, 15, 20, 20);
	}

}

