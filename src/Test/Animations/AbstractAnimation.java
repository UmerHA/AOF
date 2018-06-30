package test.animations;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public abstract class AbstractAnimation implements ImageObserver{
	private boolean active = false;
	private Image img;
	private int posX,posY;//position this animation is played at
	private short loopX = 0;//Number of Recurrences yet; will be reset after each animation
	
	protected Image[] imgs = new Image[100];// an entire maximum of 100 possible (not for each animation!)
	
	//change in child classes:
	protected String name;
	protected int length, width;//length & width of the animation's image
	protected short pause;//Length of Pause between Recurrences
	protected short RN;//Stop animation when this number of recurrences is reached 
	
	private boolean inited = false;
	private boolean picsLoaded = false;
	
	/* Each animations needs to be initialized, because the image's dimensions 
	 * are set individually for each animation. */
	
	public String getName() {return name;}
	public boolean isActive() {return active;}
	public Image getImage() {return img;}//returns image for external use
	public /*protected*/ Image getCurrentImage() {return imgs[loopX];}//returns current image depending on step = loopX (internal use only!) ; may be override by child classes
	public int getX() {return posX;}
	public int getY() {return posY;}	
	public short getLoopX() {return loopX;}
	
	public void init () {
		img = new BufferedImage(length, width, BufferedImage.TYPE_INT_ARGB);
		inited = true;
	}
	
	
	public void play(int x, int y) {
		if (!inited) {
			//No image has been created, so:
			System.err.println("Animation <"+name+"> cant be used ; it hasn been initialized yet");
			return;
		}
		if (!picsLoaded) {
			loadImages();
			System.out.println("AbstractAnimation.play :: pics loaded");
			picsLoaded = true;
		}
		
		active = true;
		posX = x;
		posY = y;
		
		System.out.println("AbstractAnimation.play :: Starting Anim Thread at ("+x+"|"+y+")");
		new AnimationLoopThread().start();
	}

	class AnimationLoopThread extends Thread {
		public void run () {
			System.err.println("AbstractAnimation$AnimationLoopThread :: starting loop");
			
			while (active) {
					System.out.println("AbstractAnimation$thread :: requsting animation step "+loopX);
					
					animate(img.getGraphics());
					
					loopX++;
					if (loopX>=RN) {
						active = false;
					} else {
						try {
							sleep(pause);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
			}
			loopX=0;//reset loop count
		}
	}
	
	protected void animate (Graphics g) {//Override if animation != a series of images (eg if Graphic2D features are used)
		g.drawImage(getCurrentImage(), 0, 0, this);
	}
	
	protected void loadImages () {}
}
