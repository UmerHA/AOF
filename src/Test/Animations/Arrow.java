package Test.Animations;

import java.awt.Image;

public class Arrow extends AbstractAnimation {
	
	public Arrow () {
		name = "Arrow";
		length = 60;
		width=60;
		pause = 500;
		RN = 5;
		
		super.init();
	}

	public Image getCurrentImage() {	
		System.err.println("Arrow.getCurrentImage :;");
		if (getLoopX() % 2 == 0) {
			//System.out.println("Arrow.getImage :: returning Image 0");
			return imgs[0];
		} else {
			//System.out.println("Arrow.getImage :: returning Image 1");
			return imgs[1];
		}
	}
	protected void loadImages () {
		System.out.println("Arrow.loadImages :: loading Arrow pics");
		imgs[0] = Applet.getImage("animations/Arrow/Arrow1.png");
		imgs[1] = Applet.getImage("animations/Arrow/Arrow2.png");
	}

	//just ignore
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false;
	}
}
