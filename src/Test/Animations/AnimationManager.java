package test.animations;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/*
 *  The only function of this class is to "sum up" all animation classes
 */


public class AnimationManager implements  ImageObserver {
	private AbstractAnimation[] anim = new AbstractAnimation[100];// up to 100 animations currently possible 
	private byte animCount;
	
	/* delete afterwards - only for testing purposes*/
	public AbstractAnimation[] anim() { return anim;}
	public byte getAnimCount() { return animCount;}
	/**/
	
	/* -- > */
	public byte SWORD_CIRCLE = 0;
	public byte ARROW = 1;
	public byte ARROW_4P = 2;
	/* < -- */
	
	
	public AnimationManager () {
		anim[0] = new SwordCircle();
		anim[1] = new Arrow();
		anim[2] = new Arrow_4Point();
		
		animCount=3;
	}
	
	public boolean playAnimation (byte animation, int x, int y) {
		if (animation<0 || animation>animCount) {
			System.err.println("Animation ID not known");
			return false;
		}
		
		try {
			anim[animation].play(x, y);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	boolean b = true;
	public void paintAnimImages (Graphics g) {
		//g.drawImage(anim[1].getImage(), 100, 100, this);
		
		for(byte i=1;i<animCount;i++)
			if (anim[i].isActive()) {
				g.drawImage(anim[i].getImage(), anim[i].getX(), anim[i].getY(), this);
				//System.out.println("AnimationManager.paintAnimImages :: painted "+i+" at ("+anim[i].getX()+"|"+anim[i].getY()+")");
			}
		//System.out.println("AnimationManager :: painting");
	}


	// just ignore:
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false;
	}
}
