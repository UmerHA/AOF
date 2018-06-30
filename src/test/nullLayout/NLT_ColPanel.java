package test.nullLayout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class NLT_ColPanel extends NLT_Panel {
	private Color col;
	
	public NLT_ColPanel () {
		//get 3 random numbers between 0 and 255
		short r = (short) (Math.floor(Math.random()*255));
		short g = (short) (Math.floor(Math.random()*255));
		short b = (short) (Math.floor(Math.random()*255));
		
		col = new Color(r,g,b);
		addMouseListener(new mLis());
	}
	
	public void paint (Graphics g) {
		g.setColor(col);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	public class mLis extends MouseAdapter {
		public void mousePressed (MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3)
				if (DragAble)
					setBounds(0,0,0,0);
		}
	}
}
