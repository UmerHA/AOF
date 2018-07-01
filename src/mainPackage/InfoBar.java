package mainPackage;

import javax.swing.JPanel;
import java.awt.*;

public class InfoBar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	Color colF; // color name foreground
	Color colB; // color name background
	
	int max;
	int mom;
	
	int h; //height
	int w; // width
	int l; // lenght
	
	boolean oppositeDirection = false;
	
	public InfoBar (int mom, int max) {
		this.mom = mom;
		this.max = max;
		
		this.colF = Color.BLACK;
		this.colB = Color.WHITE;
	}
	
	public void paint (Graphics g) {
		refresh();
		
		//System.out.println(" l = "+l);
		
		g.setColor(colB);
		g.fillRect(0, 0, w, h);
		g.setColor(colF);
		if (!this.oppositeDirection) {
			g.fillRect(0,0,(int)l,h);
		} else {
			int temp = w-l;
			g.fillRect(temp,0,w,h);
		}
	}
	

	public void decrease (int number) {
		this.mom -= number;
		if (this.mom >= this.max) 
			this.mom = this.max;
		if (this.mom <= 0) 
			this.mom = 0;
		//repaint();
	}
	public void increase (int number) {
		this.mom += number;
		if (this.mom >= this.max) 
			this.mom = this.max;
		if (this.mom <= 0) 
			this.mom = 0;
		//repaint();
	}
	
	public void refresh () {
		h = getHeight ();
		w = getWidth  ();
		l = getLenght ();	
		//System.out.println ("w="+w+"|l="+l);
	}
	
	public int getLenght () {
			//double p = mom/max; // p = percentage
			/*double p = (max/100)*mom;
			p /= 100;	*/
			
			//System.out.println("mom="+mom+"|max="+max);
			//double d = mom/max;
			//double c = 8/5;
			//System.out.println(c);
		
			return ((mom*w)/max);	// erst mit w malnehmen, sonst kommt insgesamt 0.0 raus (auch wenn ich kA hab wieso)
		
			//if (oppositeDirection)
			//	p = 100-p;
	}

	public void setMom (int mom) {
		this.mom = mom;
		if (this.mom >= this.max) 
			this.mom = this.max;
		if (this.mom <= 0) 
			this.mom = 0;
		refresh();
		//repaint();
	}
	public int getMom () {
		return this.mom;
	}
	public void setMax (int max) {
		this.max = max;
		refresh();
		//repaint();
	}
	public int getMax () {
		return this.max;
	}
	
	public void setForeColor (Color col) {
		this.colF = col;
	}
	public void setBackColor (Color col) {
		this.colB = col;
	}
	public void changeDirection () {
		if (oppositeDirection) { 
			oppositeDirection = false;
		} else {
			oppositeDirection = true;
		}
	}
}
