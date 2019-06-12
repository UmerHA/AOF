package test.animations;

import java.awt.Image;

public class Arrow_4Point extends AbstractAnimation {
	
	public Arrow_4Point () {
		name = "Arrow 4Point";
		length = 60;
		width=60;
		pause = 500;
		RN = 400;
		
		super.init();
	}

	public Image getCurrentImage() {	
		switch (getLoopX() % 4) {
		case 0 : return imgs[0];
		case 1 : return imgs[1];
		case 2 : return imgs[2];
		case 3 : return imgs[3];
		default : return null;
		}
	}
	protected void loadImages () {
		System.out.println("Arrow.loadImages :: loading Arrow pics");
		imgs[0] = Applet.getImage("animations/Arrow_4Point/Arrow1.png");
		imgs[1] = Applet.getImage("animations/Arrow_4Point/Arrow2.png");
		imgs[2] = Applet.getImage("animations/Arrow_4Point/Arrow3.png");
		imgs[3] = Applet.getImage("animations/Arrow_4Point/Arrow4.png");
	}

	//just ignore
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false;
	}
}
