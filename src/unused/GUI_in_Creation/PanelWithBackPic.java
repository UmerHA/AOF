package unused.GUI_in_Creation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PanelWithBackPic extends JPanel {
	private Image backPic;
	private int x = 0;
	private int y = 0;
	private boolean hasBorder = false;
	private Color borderCol = new Color(156,90,60);
	@SuppressWarnings("unused")
	private short borderSize = 3;
	
	public PanelWithBackPic (Image backPic) {
		this.backPic = backPic;
	}
	public void paint (Graphics g) {
		g.drawImage(backPic, x, y, this);
		if (hasBorder) {
			g.setColor(borderCol);
			
			int h = getHeight();
			int w = getWidth();
			
			g.fillRect(0, 0, w, 3);
			g.fillRect(0, h-3, w, 3);
			g.fillRect(0, 0, 3, h);
			g.fillRect(w-3, 0, 3, h);	
		}
	}
	
	public void moveMap(int i, int j) {
		x += i;
		y += j;
		repaint();
	}
	
	public void setHasBorder (boolean bool) {
		this.hasBorder = bool;
	}
	public void setBorderColor (Color col) {
		this.borderCol = col;
	}
}
