package unused.test.animations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Applet extends javax.swing.JApplet {
	private static final long serialVersionUID = 1L;
	private static Applet applet;
	private AnimationManager ANIManager;
	private boolean runFlag = true;
	private long prevT, curT;
	private short PAINT_SPEED = 250;
	
	private JButton btn, btn2, btn3;
	
	private Image testPic;
	
	public void init() {
		applet = this;
		ANIManager = new AnimationManager();
		
		btn = new JButton("Arrow 2");
		btn.addActionListener(new actLis());
		btn2 = new JButton("Arrow 4");
		btn2.addActionListener(new actLis());
		btn3 = new JButton("Sword");
		btn3.addActionListener(new actLis());
		
		setLayout(null);
		btn.setBounds(320,410,80,30);
		btn2.setBounds(405,410,80,30);
		btn3.setBounds(235,410,80,30);
		add(btn);
		add(btn2);
		add(btn3);
		
		System.out.println("<Code Base: "+getCodeBase()+">");
		testPic = getImage("animations/Arrow/Test.png");
	}
 	public void start() {
		new GameLoop().start();
	}
	public void stop() {
		runFlag = false;
	}
	

	//private long paintCount = 0;
	public void paint (Graphics g) {
		g.setColor(Color.CYAN);		//- nur "deko"
		g.fillRect(0, 0, getWidth(), getHeight());
		
		btn.repaint();
		btn2.repaint();
		btn3.repaint();
		
		g.setColor(Color.black);
		
		//ANIManager.paintAnimImages(g);
		  
		for(byte i=0;i<ANIManager.getAnimCount();i++)
			if (ANIManager.anim()[i].isActive())
				g.drawImage(ANIManager.anim()[i].getCurrentImage(), ANIManager.anim()[i].getX(), ANIManager.anim()[i].getY(), this);
			
		
		
		g.drawImage(testPic, 0, 200, this);
		
		//System.err.println("Biatch, i painted the "+(++paintCount)+"th time");
	}
	

	// Game (rather Paint) Loop
	class GameLoop extends Thread {
		public void run () {
			System.err.println("GameLoop started");
			while (runFlag) {
				curT = java.util.Calendar.getInstance().getTimeInMillis();
				if (curT-prevT >= PAINT_SPEED) {
					repaint();
					prevT = curT;
				}
			}
			System.exit(0);
		}
	}
	class actLis implements ActionListener {
		public void actionPerformed (ActionEvent ae) {
			if (ae.getActionCommand().equals("Arrow 2"))
				ANIManager.playAnimation(ANIManager.ARROW, 400, 200);
			if (ae.getActionCommand().equals("Arrow 4"))
				ANIManager.playAnimation(ANIManager.ARROW_4P, 450, 200);
			if (ae.getActionCommand().equals("Sword")) 
					ANIManager.playAnimation(ANIManager.SWORD_CIRCLE, 300, 200);
			
		}
	}
	
	public static Image getImage (String picPath) {
		System.out.println("Applet.getImage : loading 'pics/"+picPath+"'");
		return applet.getImage(applet.getCodeBase(), "pics/"+picPath);
	}	
}
