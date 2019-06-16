package unused.animations;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public abstract class AbstractAnimation implements ImageObserver{
	private static boolean active;
	private static Image img;
	private static int posX,posY;//position this animation is played at
	private static short loopX;
	
	protected static Image[] imgs = new Image[100];// a maximum of 100 pics per animation is possible
	
	//these variables need to be in subclasses
	protected String name;
	protected int length, width;
	protected short pause;//Length of Pause between Recurrences
	protected short RN;// Number of Recurrences
	
	private boolean inited = false;
	private boolean picsLoaded = false;
	
	public boolean isActive() {return active;}
	public Image getImage() {return img;}
	public int getX() {return posX;}
	public int getY() {return posY;}	
	public short getLoopX() {return loopX;}
	
	public void init () {
		img = new BufferedImage(length, width, BufferedImage.TYPE_INT_ARGB);
		inited = true;
	}
	
	
	public void play(int x, int y) {
		if (!inited) {
			System.err.println("Animation <"+name+"> cant be used ; it hasn been initialized yet");
			return;
		}
		if (!picsLoaded) {
			loadImages();
			picsLoaded = true;
		}
		
		active = true;
		posX = x;
		posY = y;
		
		System.out.println("AbstractAnimation.play :: Starting Anim Thread at ("+x+"|"+y+")");
		new thread().start();
	}

	class thread extends Thread {		
		public void run () {
			System.out.println("AbstractAnimation$thread :: starting loop");
			
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			loopX=0;
		}
	}
	
	// These methods should be overridden if the animations doesn't consist of images
	protected void animate (Graphics g) {}
	protected void loadImages () {}
}
