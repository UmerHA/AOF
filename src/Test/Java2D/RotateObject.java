package test.java2D;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JApplet;


@SuppressWarnings("serial")
public class RotateObject extends JApplet {
	AffineTransform at;
	
	public void init () {
		at = new AffineTransform();
		//at.translate(125, 125);
		new thread().start();
	}
	public void paint (Graphics g) {
			  g.setColor(Color.WHITE);
			  g.fillRect(0, 0, getWidth(), getHeight());
		
		      Graphics2D g2d = (Graphics2D) g; 
		      
		      g2d.setTransform(at);
		      
		      GradientPaint pat2 = new GradientPaint(0,100,Color.BLACK, 50,100,Color.WHITE, true);
		      g2d.setPaint(pat2);
		      Rectangle2D.Float rect = new Rectangle2D.Float(100,100,50,50);
		      g2d.fill(rect);
   }
	
	class thread extends Thread {
		private long cur,prev;
		private long curP, prevP;
		private short pause = 125;
		private short pauseP = 125;
		
		
		public void run () {
			while (true) {
				cur = java.util.Calendar.getInstance().getTimeInMillis();
				if (cur-prev >= pause) {
					at.rotate(Math.toRadians(15), 125, 125);
					prev = cur;
				}
				
				curP = java.util.Calendar.getInstance().getTimeInMillis();
				if (curP-prevP >= pauseP) {
					repaint();
					prevP = curP;
				}
			}
		}
	}
}
