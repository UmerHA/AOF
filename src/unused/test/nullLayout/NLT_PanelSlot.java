package unused.test.nullLayout;

import java.awt.Graphics;
import java.awt.Image;

import unused.test.AGT_Slot;

public class NLT_PanelSlot extends NLT_Panel {
	private static final long serialVersionUID = 1L;
	
	//double buffering
	private Image offImage;
	private Graphics offGraphics;

	private AGT_Slot[] invSlots = new AGT_Slot[30];

	public NLT_PanelSlot() {
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
		if (offGraphics == null) {
			offImage = createImage(getWidth(),getHeight());
			offGraphics = offImage.getGraphics();
		}
		
		super.paint(offGraphics);
		g.drawImage(offImage, 0, 0, this);
	}
	
	public void update (Graphics g) {
		paint(g);
	}
}
