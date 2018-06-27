package Test.Java2D;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class GradientPaintTest extends TestFrame {
	GradientPaintTest (int w, int h, String title) {
		super(w,h,title);
	}
	GradientPaintTest() {
		super();
	}
	
	public void paintContent (Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Paint p1 = new GradientPaint(new Point2D.Float(50F,50F), Color.GREEN, new Point2D.Float(250F, 250F), Color.BLUE, false);
		Paint p2 = new GradientPaint(new Point2D.Float(450F,50F), Color.GREEN, new Point2D.Float(250F, 250F), Color.BLUE, false);
		Paint p3 = new GradientPaint(new Point2D.Float(50F,450F), Color.GREEN, new Point2D.Float(250F, 250F), Color.BLUE, false);
		Paint p4 = new GradientPaint(new Point2D.Float(450F,450F), Color.GREEN, new Point2D.Float(250F, 250F), Color.BLUE, false);
		
		Rectangle2D.Float rect1 = new Rectangle2D.Float(0,0,getWidth()/2, getHeight()/2);
		g2d.setPaint(p1);
		g2d.fill(rect1);
		
		Rectangle2D.Float rect2 = new Rectangle2D.Float(getWidth()/2,0,getWidth()/2, getHeight()/2);
		g2d.setPaint(p2);
		g2d.fill(rect2);
		
		Rectangle2D.Float rect3 = new Rectangle2D.Float(0,getWidth()/2,getWidth()/2, getHeight()/2);
		g2d.setPaint(p3);
		g2d.fill(rect3);
		
		Rectangle2D.Float rect4 = new Rectangle2D.Float(getWidth()/2,getWidth()/2,getWidth()/2, getHeight()/2);
		g2d.setPaint(p4);
		g2d.fill(rect4);
	}
	
	public static void main (String[] args) {
		new GradientPaintTest();
	}
}
