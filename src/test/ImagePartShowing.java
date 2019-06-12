package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ImagePartShowing extends JPanel {
	Image pic = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/joker santa.jpg");//GUI/miniMap.png");;
	JFrame frame;
	int x = 0;
	int y = 0;
	int speed = 1;
	byte border = 5;
	
	public static void main (String[] args) {
		new ImagePartShowing();
	}
	
	public ImagePartShowing() {
		frame = new JFrame();
		frame.add(this);
		frame.setTitle("0 | 0");
		frame.setSize(300,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new keyLis());
		frame.addKeyListener(new keyLis());
	}
	
	public void paint (Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(pic, x, y, this);
		g.setColor(Color.BLACK);
		for (byte i=0;i<border;i++)
			g.drawRect(i, i, getWidth()-i, getHeight()-i);
	}
	
	
	class keyLis extends KeyAdapter {
		public void keyPressed (KeyEvent e) {
			switch(e.getKeyCode()) {
			case 37 : x+=speed;break;
			case 38 : y+=speed;break;
			case 39 : x-=speed;break;
			case 40 : y-=speed;break;
			case 27 : System.exit(0);
			case 107 : speed++;break;
			case 109 : speed--;break;
			}
			
			frame.setTitle(x + " | " + y);
			repaint();
			
			//System.out.println(e.getKeyCode());
		}
	}
}
