package Tests;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class AGT_Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	//double buffering
	private Image offImage;
	private Graphics offGraphics;

	private AGT_Slot[] invSlots = new AGT_Slot[30];
	/*D+D*/	private int x1,x2,y1,y2;

	public AGT_Panel() {
		setLayout(null);
		int num = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				invSlots[num] = new AGT_Slot(num);
				invSlots[num].setBounds((j * 50), (i * 75), 50, 75);

				add(invSlots[num]);

				// System.out.println("OP_GB.InventoryPanel.construct : num = "+num);
				num++;
			}
		}
		
		addMouseMotionListener(new mmLis());
		addMouseListener(new mLis());
	}

	public void paint(Graphics g) {
		for (int i = 0; i < 30; i++)
			invSlots[i].repaint();
	}
	
	public void update (Graphics g) {
		
		if (offGraphics == null) {
			offImage = createImage(getWidth(),getHeight());
			offGraphics = offImage.getGraphics();
		}
		paint(offGraphics);
		g.drawImage(offImage, 0, 0, this);
		
	}
	
	//for D&D -->
	class mmLis implements MouseMotionListener {
		public void mouseDragged(MouseEvent e) {
			x2 = e.getXOnScreen();
			y2 = e.getYOnScreen();
			AGT_Applet.changePosition((x2-x1), (y2-y1));
			x1 = x2;
			y1 = y2;
			x2 = y2 = 0;
		}

		public void mouseMoved(MouseEvent e) {
		}
	}
	class mLis extends MouseAdapter {		
		public void mousePressed(MouseEvent e) {
			//System.out.println("Mouse pressed");
			x1 = e.getXOnScreen();
			y1 = e.getYOnScreen();
		}

		public void mouseReleased(MouseEvent e) {
			//System.out.println("Mouse released");
			x1 = x2 = y1 = y2 = 0;
		}
		
	}
}
