package Tests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/*
 * This class is currently not in use.
 */

public class PanelWithBackPic extends JPanel {
	private static final long serialVersionUID = 1L;

	private Image backPic;
	private int x = 0;
	private int y = 0;
	private boolean hasBorder = false;
	private Color borderCol = new Color(156,90,60);
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
			
			g.fillRect(0, 0, w, borderSize);
			g.fillRect(0, h-borderSize, w, borderSize);
			g.fillRect(0, 0, borderSize, h);
			g.fillRect(w-borderSize, 0, borderSize, h);		
		}
	}
	
	public void setImage (Image backPic) {
		this.backPic = backPic;
		repaint();
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
	public void setBorderSize (short borderSize) {
		this.borderSize = borderSize;
	}
}
