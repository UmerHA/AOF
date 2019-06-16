package unused.animations;

import java.awt.Graphics;
import java.awt.Image;

import client.Util;

public class Arrow extends AbstractAnimation {
	
	public Arrow () {
		length = 60;
		width=60;
		pause = 500;
		RN = 2000;
		
		super.init();
	}
	
	protected void animate (Graphics g) {
		System.out.println("Arrow :: Animating");
		
		if (getLoopX() % 2 == 0) {
			g.drawImage(imgs[0], 0, 0, this);
		} else {
			g.drawImage(imgs[1], 0, 0, this);
		}
			
	}
	protected void loadImages () {
		imgs[0] = Util.getImage("Animations/Arrow/Arrow1.png");
		imgs[1] = Util.getImage("Animations/Arrow/Arrow2.png");
	}

	//just ignore
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false;
	}
}
