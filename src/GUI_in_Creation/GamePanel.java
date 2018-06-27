package GUI_in_Creation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * This class was designed to test the new layout.
 */

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	PanelWithBackPic[] panels = new PanelWithBackPic[14];
	Image backPic;
	Image bpak; //backPicActionKey
	
	public GamePanel () {
		Image[] pics = new Image[14];
		pics[0] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/1.png");
		pics[1] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/2.png");
		pics[2] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/3.png");
		pics[3] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/4.png");
		pics[4] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/5.png");
		pics[5] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/6.png");
		pics[6] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/7.png");
		pics[7] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/8.png");
		pics[8] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/9.png");
		pics[9] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/0.png");
		pics[10] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/AOF Logo.png");
		pics[11] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/miniMap.png");
		pics[12] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/settings.png");
		pics[13] = java.awt.Toolkit.getDefaultToolkit().getImage("pics/menu.png");
		backPic = java.awt.Toolkit.getDefaultToolkit().getImage("pics/gameArea.png");
		bpak = java.awt.Toolkit.getDefaultToolkit().getImage("pics/bpak.png");
		
		//setLayout(new GridLayout(4,4));
		
		for (int i=0;i<14;i++) {
			panels[i] = new PanelWithBackPic(pics[i]);
			//add(panels[i]);		
		}	
		
		
		setLayout(null);
		for (int i=0;i<10;i++) {
			panels[i].setBounds(i*50+1,355,48,40);
		}
		
		panels[10].setBounds(500,350,99,50);
		panels[11].setBounds(450,0,150,125);
		panels[12].setBounds(380,0,70,40);
		panels[13].setBounds(310,0,70,40);
		
		panels[11].setHasBorder(true);
		
		for (int i=0;i<14;i++)
			add(panels[i]);
		
		
		setBackground(Color.WHITE);
		addKeyListener(new keyLis());
		
		JFrame f = new JFrame();
		f.setSize(800,600);
		f.setLayout(null);
		this.setBounds(50,50,600,400);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.addKeyListener(new keyLis());
	}
	
	public void paint (Graphics g) {
		g.drawImage(backPic, 0, 0, this);
		g.drawImage(bpak, 0, 350, this);
		for (int i=0;i<14;i++)
			panels[i].repaint();
	}
	
	
	public static void main (String[] patrick) {
		new GamePanel();
	}

	class keyLis extends KeyAdapter {
		public void keyPressed (KeyEvent e) {
			int x = 0;
			int y = 0;
			
			switch (e.getKeyCode()) {
			case 37 : x--;break;
			case 38 : y++;break;
			case 39 : x++;break;
			case 40 : y--;break;
			}
			
			panels[11].moveMap(x, y);
			
			System.out.println("keyPressed");
		}
	}
}
