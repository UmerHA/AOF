package mainPackage;

import javax.swing.JPanel;
import java.awt.*;

/*	This class is just a panel which has a back- and a foreground image.
 *  Each time this panel is repainted, first the background, then the foreground
 *  image is drawn.
 *  
 *  Usually "pics/cbWin/back.jpg" is the background image. But it can be changed for all
 *  PicPanels by using the setStaticBackImage()-method, or for each PicPanel individually
 *  by using setBackImage(). 
 *  
 *  The foreground image needs to be set individually by using setImage().
 */

public class PicPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Image pic;
	private static Image background = Toolkit.getDefaultToolkit().getImage("pics/cbWin/back.jpg");
	private boolean bgChanged = false;
	private Image bgPic; 
	
	public void paint (Graphics g) {
		if (bgChanged) {
			g.drawImage(bgPic, 0, 0, this);
		} else {
			g.drawImage(background, 0, 0, this);
		}
		g.drawImage(pic,0,0,this);
	}
	public void setImage (String picPath) {
		this.pic = Toolkit.getDefaultToolkit().getImage(picPath);
	}
	public void setImage (Image pic) {
		this.pic = pic;
	}
	public void setBackImage (String picPath) {
		this.bgPic = Toolkit.getDefaultToolkit().getImage(picPath);
		this.bgChanged = true;
	}
	public void setBackImage (Image pic) {
		this.bgPic = pic;
		this.bgChanged = true;
	}
	public void setStaticBackImage (Image pic) {
		background = pic;
	}
	public void setStaticBackImage (String picPath) {
		background = Toolkit.getDefaultToolkit().getImage(picPath);
	}
}
