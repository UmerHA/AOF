package Panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import Connection.Connector;
import Items.Item;
import MainPackage.MainApplet;

public class InventoryPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private InventoryManager manager;
	private InventorySlot[] invSlots = new InventorySlot[30];

	private int curX;
	private int curY;

	private final int spcX = /*MainProg.SPACE_FOR_PANEL_X*/0; // space X
	private final int spcY = /*MainProg.SPACE_FOR_PANEL_Y*/0; // space Y

	//double buffering
	private Image offImage;
	private Graphics offGraphics;
	
	private boolean painted = false;
	
	
	private void construct() {
		setMinimumSize(new Dimension(250,450));
		setPreferredSize(new Dimension(250,450));
		
		//try {
		this.manager = MainApplet.actPlayer.getInventoryManager();
		/*} catch (NullPointerException e) {
			System.out.println(MainApplet.class);
			System.out.println(MainApplet.actPlayer);
			System.out.println(MainApplet.actPlayer.getInventoryManager());
			System.exit(1);
		}*/
		setLayout(null);
		int num = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				invSlots[num] = new InventorySlot(num);
				invSlots[num].setBounds((j * 50) + spcX, (i * 75) + spcY, 50,
						75);
			
				invSlots[num].setItem(manager.getItem(num));
				invSlots[num].setManager(manager);				
				
				add(invSlots[num]);

				// System.out.println("OP_GB.InventoryPanel.construct : num = "+num);
				num++;
			}
		}
		//invSlots[0].setPic ("tems/sample.jpg");
		//invSlots[1].setPic ("items/sword.jpg");
		
		curX = 0;
		curY = 0;

		addMouseListener(new mLis());
		addMouseMotionListener(new mmLis());
	}

	public InventoryPanel() {
		construct();
	}
	public InventoryPanel(InventoryManager manager) {
		this.manager = manager;
		construct();
	}

	public void setManager(InventoryManager manager) {
		this.manager = manager;
		for (int i = 0; i < 30; i++)
			invSlots[i].setManager(manager);
		refresh();
		repaint();
	}

	public void setItem(Item item, int position) {
		// System.out.println
		// ("InventoryPanel.setItem :: item : "+item.getName()+" | position : "+position);
		invSlots[position].setItem(item);
	}

	public void paint(Graphics g) {
		if (!painted) {
			Connector.Send("invlg~#");
			painted = true;
		}
		
		
		refresh();		
		if (offGraphics == null) {
			offImage = createImage(getWidth(),getHeight());
			offGraphics = offImage.getGraphics();
		}
		
		super.paint(offGraphics);
		offGraphics.drawImage(manager.getFlyingItem().getImage(), curX + spcX, curY
				+ spcY, this);
		
		g.drawImage(offImage, 0, 0, this);
	}

	public void refresh() {
		for (int i = 0; i < 30; i++)
			invSlots[i].setItem(manager.getItem(i));
	}

	public void setLocationOfCursor(int x, int y, int id) {
		Point pt = InventorySlot.getPositionFromID(id);

		// System.out.println ("id : "+id+"  :: x:"+pt.x+"  y:"+pt.y);

		curX = (pt.x * 50) + x + spcX;
		curY = (pt.y * 75) + y + spcY;

		// System.out.println ("x : "+curX+"  :: y : "+curY);

		if ((curX < spcX) || (curY < spcY) || (curX > 250 + spcX)
				|| (curY > 450 + spcY))
			manager.releaseFlyingItem();

		repaint();
	}

	public void setLocationOfCursor(int x, int y) {
		setLocationOfCursor(x - spcX, y - spcY, 0);
	}

	class mLis extends MouseAdapter {
		public void mouseEntered(MouseEvent e) {
			System.out.println("InvPanel.mLis :: mouse entered");
		}

		public void mouseExited(MouseEvent e) {
			System.out.println("InvPanel.mLis :: mouse exited");
		}
	}

	class mmLis extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {
			setLocationOfCursor(e.getX(), e.getY());
		}
	}
}
