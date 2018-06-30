package test.java2D;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JApplet;


public class RotateObjectManual extends JApplet {
	private static final long serialVersionUID = 1L;
	AffineTransform at;
	
	public void init () {
		at = new AffineTransform();
		addMouseMotionListener(new mmLis());
		addMouseListener(new mLis());
	}
	public void paint (Graphics g) {
			  g.setColor(Color.WHITE);
			  g.fillRect(0, 0, getWidth(), getHeight());
		
		      Graphics2D g2d = (Graphics2D) g; 
		      
		      g2d.setTransform(at);
		      
		      GradientPaint pat2 = new GradientPaint(0,50,Color.BLACK, 50,100,Color.WHITE, true);
		      g2d.setPaint(pat2);
		      Rectangle2D.Float rect = new Rectangle2D.Float(100,100,100,100);
		      g2d.fill(rect);
   }
	
	private int prevY, curY;
	private int prevX, curX;
	class mmLis extends MouseMotionAdapter {	
		public void mouseDragged (MouseEvent e) {

			if (!e.isControlDown()) {
				curY = e.getY();			
				double dif = curY-prevY;
				
				if (e.getX() < 150)
					dif = -dif;
			
				at.rotate(Math.toRadians(dif), 150, 150);
			} else {
				curX = e.getX();			
				double dif = curX-prevX;
				double radDif = Math.toRadians(dif);
				if (e.isAltDown()) {
					at.shear(radDif, 0);
				} else {
					at.shear(0, radDif);
				}
			}
			
			repaint();
			
			//System.out.println(Math.toRadians(curX-prevX));
			
			prevY = curY;
		}
	}
	class mLis extends MouseAdapter {
		public void mousePressed (MouseEvent e) {
			if (!e.isControlDown()) {
				prevY = e.getY();
			} else {
				prevX = e.getX();
			}
		}
	}
}
