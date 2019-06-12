package mainPackage;

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
	private Color backCol = null;
	private int x = 0;
	private int y = 0;
	private boolean hasBorder = false;
	private Color borderCol = new Color(156,90,60);
	private short borderSize = 3;
	
	public PanelWithBackPic (Image backPic) {
		this.backPic = backPic;
	}
	public PanelWithBackPic (Color backCol) {
		this.backCol = backCol;
	}
public PanelWithBackPic (Image backPic, Color backCol) {
		this.backCol = backCol;
	}
	public void paint (Graphics g) {
		if (backCol!=null) {
			g.setColor(backCol);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (backPic!=null)
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
		//repaint();
	}
	public Image getImage() {
		return this.backPic;
	}
	public void setColor (Color backCol) {
		this.backCol = backCol;
		//repaint();
	}
	public Color getColor () {
		return this.backCol;
	}
 	
	public void moveMap(int i, int j) {
		x += i;
		y += j;
		//repaint();
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
