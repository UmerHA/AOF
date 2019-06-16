package unused.test.animations;

/* This class was supposed to demonstrate the usage
 * of Java2D in animations. Unfortunately, Java2D can't be used at the moment;
 * instead images will be used  
 */

//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.geom.AffineTransform;
import java.awt.Image;

public class SwordCircle extends AbstractAnimation {
	//private AffineTransform at;
	
	public SwordCircle () {
		name = "SwordCircle";
		length = 60;
		width=60;
		pause = 250;
		RN = 39; 
		
		//at = new AffineTransform();
		
		super.init();
	}
	
	/*protected void animate (Graphics g) {
		//System.out.println("SwordCircle :: Animating");
		
		Graphics2D g2 = (Graphics2D) g;
		
		at.rotate(Math.toRadians(10), 30, 30);
		g2.drawImage(imgs[0], at, this);
		
		System.out.println("Animating SC");
	}*/
	public Image getCurrentImage() {	
		switch (getLoopX() % 8) {
		case 0 : return imgs[0];
		case 1 : return imgs[1];
		case 2 : return imgs[2];
		case 3 : return imgs[3];
		case 4 : return imgs[4];
		case 5 : return imgs[5];
		case 6 : return imgs[6];
		case 7 : return imgs[7];
		default : return null;
		}
	}
	protected void loadImages () {
		imgs[0] = Applet.getImage("animations/SwordCircle/West.png");
		imgs[1] = Applet.getImage("animations/SwordCircle/NorthWest.png");
		imgs[2] = Applet.getImage("animations/SwordCircle/North.png");
		imgs[3] = Applet.getImage("animations/SwordCircle/NorthEast.png");
		imgs[4] = Applet.getImage("animations/SwordCircle/East.png");
		imgs[5] = Applet.getImage("animations/SwordCircle/SouthEast.png");
		imgs[6] = Applet.getImage("animations/SwordCircle/South.png");
		imgs[7] = Applet.getImage("animations/SwordCircle/SouthWest.png");
	}

	//just ignore
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false;
	}
}
