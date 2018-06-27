package MainPackage;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/* This panel is dragable. */

public class CustomPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int oldX,oldY;
	private Point location = new Point(0,0);
	protected JLayeredPane laypane;
	
	protected boolean DragAble = true;
	public void setDragAble (boolean bool) {
		DragAble = bool;
	}
	
	public CustomPanel () {
		addMouseListener(new mLis());
		addMouseMotionListener(new mmLis());
	}
	
	public void setBounds (int x,int y,int w,int h) {
		location.x = x;
		location.y = y;
		super.setBounds(x, y, w, h);
	}
	public void focus () {
		// grab focus
		int top = laypane.highestLayer()+1;
		laypane.setLayer(this,top);
	}
	public void register (JLayeredPane laypane) {
		this.laypane = laypane;
	}
	
	public class mmLis extends MouseMotionAdapter {
		public void mouseDragged (MouseEvent e) {
			if (!DragAble)
				return;
			
			int difX = e.getX()-oldX;
			int difY = e.getY()-oldY;
			
			setBounds(location.x+difX,location.y+difY,getWidth(),getHeight());
		}
	}
	public class mLis extends MouseAdapter {
		public void mousePressed (MouseEvent e) {
			if (!DragAble)
				return;
			
			oldX = e.getX();
			oldY = e.getY();
			
			focus();
		}
	}

}
