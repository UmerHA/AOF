package unused.test;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class AGT_Applet extends JApplet {
	AGT_Panel panel = new AGT_Panel();
	Image pic;
	static AGT_Applet applet;
	
	private static int invX = 0;
	private static int invY = 0;
	
	//MP = MenuPanel
	private static final int MP_HEIGHT = 450; //450+40
	private static final int MP_WIDTH = 250;  //250+20
	
	//Double-Buffering
	private Graphics offGraphics;
	private Image offImage;
	
	public void init () {
		applet = this;
		
		setLayout(null);
		pic = getImage(getCodeBase(),"pics/Water_Scale.jpg");
		System.out.println("Base : "+getCodeBase());
		
		panel.setBounds(0,0,250,450);
		add(panel);
		
		offImage = createImage(getWidth(), getHeight());
		offGraphics = offImage.getGraphics();
	}
	public void paint (Graphics g) {
		g.drawImage(pic, 0, 0, this);
		panel.repaint();
	}
	public void update (Graphics g) {
		paint(offGraphics);
		getGraphics().drawImage(offImage, 0, 0, this);
	}
	
	/*D+D*/
	private static void repositionPanel () {
		applet.panel.setBounds(invX, invY, MP_WIDTH, MP_HEIGHT);
		applet.invalidate();
		applet.validate();
		applet.repaint();
		//System.out.println("GamePanel.repostionPanel");
	}
	public static void changePosition (int x, int y) {
		invX += x;
		invY += y;
		repositionPanel();
		//System.out.println("GamePanel.changePosition");
	}
}
