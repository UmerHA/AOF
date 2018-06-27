package Tests;
import java.awt.Image;

import javax.swing.JFrame;

public class PicTestFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private PanelWithBackPic pane1,pane2,pane3;
	private Image img1,img2,img3;
	
	public PicTestFrame() {
		img1 = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CrC/male.png");
		img2 = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CrC/female.png");
		img3 = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CrC/CrCBackPic.png");
		pane1 = new PanelWithBackPic(img1);
		pane2 = new PanelWithBackPic(img2);
		pane3 = new PanelWithBackPic(img3);
		pane1.setBounds(0, 0, 350, 450);
		pane2.setBounds(400, 0, 350, 450);
		pane3.setBounds(750, 0, 100, 450);
		
		setSize(800, 520);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(null);
		add(pane1);
		add(pane2);
		add(pane3);
		setVisible(true);
	}
	
	
	public static void main (String[] args) {
		new PicTestFrame();
	}
}
