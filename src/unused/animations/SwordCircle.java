package unused.animations;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import client.Util;

public class SwordCircle extends AbstractAnimation {
	private AffineTransform at;
	
	public SwordCircle () {
		length = 60;
		width=60;
		pause = 250;
		RN = 36;
		
		at = new AffineTransform();
		
		super.init();
	}
	
	protected void animate (Graphics g) {
		//System.out.println("SwordCircle :: Animating");
		
		Graphics2D g2 = (Graphics2D) g;
		
		at.rotate(Math.toRadians(10), 30, 30);
		g2.drawImage(imgs[0], at, this);
	}
	protected void loadImages () {
		imgs[0] = Util.getImage("Animations/SwordCircle/sword.png");
	}

	//just ignore
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false;
	}
}
