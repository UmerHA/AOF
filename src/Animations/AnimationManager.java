package Animations;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/*
 *  The only function of this class is to "sum up" all animation classes
 */


public class AnimationManager implements  ImageObserver {
	private AbstractAnimation[] anim = new AbstractAnimation[100];// up to 100 animations currently possible 
	private byte animCount;
	
	/* -- > */
	public byte SWORD_CIRCLE = 0;
	public byte ARROW = 1;
	/* < -- */
	
	
	public AnimationManager () {
		anim[0] = new SwordCircle();
		anim[1] = new Arrow();
		
		animCount=2;
	}
	
	public boolean playAnimation (byte animation, int x, int y) {
		try {
			anim[animation].play(x, y);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public void paintAnimImages (Graphics g) {
		for(byte i=0;i<animCount;i++)
			if (anim[i].isActive()) {
				g.drawImage(anim[i].getImage(), anim[i].getX(), anim[i].getY(), this);
				System.out.println("AnimationManager.paintAnimImages :: painted "+i+" at ("+anim[i].getX()+"|"+anim[2].getY()+")");
			}
				
		//System.out.println("AnimationManager :: painting");
	}


	// just ignore:
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false;
	}
}
