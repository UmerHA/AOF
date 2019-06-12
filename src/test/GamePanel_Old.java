package test;

/*
 * Not in use anymore 
 */

import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import mainPackage.MainApplet;
import mainPackage.PanelWithBackPic;
import mainPackage.gamePanel.ChatBox;
import mainPackage.gamePanel.FieldPanel;

public class GamePanel_Old extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private PanelWithBackPic[] panels = new PanelWithBackPic[15];//usually 14, but changed for inventory tests
	private FieldPanel fpanel;
	//private MenuPanel menuPanel;
	ChatBox chatbox;
	private Image bpak; // backPicActionKey
	
	private int x_bpak = 0;
	private int y_bpak;
	
	//private int invX = 0;
	//private int invY = 0;
	
	//MP = MenuPanel
	//private final int MP_HEIGHT = 450; //450+40
	//private final int MP_WIDTH = 250;  //250+20

	public GamePanel_Old () {

		//load image -->
		Image[] pics = new Image[14];
		for (int i = 0; i < 14; i++)
			pics[i] = MainApplet.getImage(i + 1);
		bpak = MainApplet.getImage(16);

		//create sub-panels -->
		for (int i = 0; i < 14; i++)
			panels[i] = new PanelWithBackPic(pics[i]);
		panels[14] = new PanelWithBackPic(MainApplet.getImage(17));//invPanel test
		fpanel = new FieldPanel();
		//menuPanel = new MenuPanel();
		
		//shape the sub-panels -->
		setLayout(null);
		fpanel.setBounds(0, 0, getWidth(), getHeight());
		for (int i = 0; i < 10; i++) {
			panels[i].setBounds(i * 50 + 1, MainApplet.HEIGHT()-45, 48, 40);
		}
		panels[10].setBounds(MainApplet.WIDTH()-100, MainApplet.HEIGHT()-50, 99, 50);
		panels[11].setBounds(MainApplet.WIDTH()-150, 0, 150, 125);
		panels[12].setBounds(MainApplet.WIDTH()-220, 0, 70, 40);
		panels[13].setBounds(MainApplet.WIDTH()-290, 0, 70, 40);
		y_bpak = MainApplet.HEIGHT()-50;
		//menuPanel.setBounds(50, 50, 0, 0);//not visible by default
		
		panels[11].setHasBorder(true);

		//add sub-panels -->
		add(fpanel);
		for (int i = 0; i < 14; i++)
			add(panels[i]);

		/*chatbox = new ChatBox(this);*/ //should be active
		chatbox.setBounds(0, MainApplet.HEIGHT()-175, 400/*225*/, 125);
		add(chatbox);
		//add(menuPanel);

		//add listeners -->
		addKeyListener(new keyLis());
		addMouseListener(new mLis());
	}
	
	public FieldPanel getFieldPanel () {
		return this.fpanel;
	}
	
	public void isNowActive() {
		registerClient();
	}

	public boolean registerClient() {
		try {
			//chatbox.RegisterClient(MainApplet.actPlayer.getName());
		} catch (Exception e) {
			System.err.println("Error " + e);
			return false;
		}
		return true;
	}

	public void paint(Graphics g) {
		fpanel.paint(g);
		g.drawImage(bpak, x_bpak, y_bpak, this);
		for (int i = 0; i < 14; i++)
			panels[i].repaint();
		chatbox.repaint();
	}

	class keyLis extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int x = 0;
			int y = 0;

			switch (e.getKeyCode()) {
			case 37:MainApplet.actPlayer.move((short) 1);break;//left
			case 38:MainApplet.actPlayer.move((short) 2);break;//up
			case 39:MainApplet.actPlayer.move((short) 3);break;//right
			case 40:MainApplet.actPlayer.move((short) 4);break;//down
			
			case 65:x--;break;//a
			case 87:y--;break;//w
			case 68:x++;break;//d
			case 83:y++;break;//s
			}
			
			panels[11].moveMap(x, y);
			
			//System.out.println(e.getKeyCode());
		}
	}
	class mLis extends MouseAdapter {
		public void mousePressed (MouseEvent e) {
			grabFocus();
		}
	}

	//just an extra
	public void repositionComponents () {
		fpanel.setBounds(0, 0, getWidth(), getHeight());
		for (int i = 0; i < 10; i++)
			panels[i].setBounds(i * 50 + 1, MainApplet.HEIGHT()-45, 48, 40);
		
		panels[10].setBounds(MainApplet.WIDTH()-100, MainApplet.HEIGHT()-50, 99, 50);
		panels[11].setBounds(MainApplet.WIDTH()-150, 0, 150, 125);
		panels[12].setBounds(MainApplet.WIDTH()-220, 0, 70, 40);
		panels[13].setBounds(MainApplet.WIDTH()-290, 0, 70, 40);
		//map.setBounds(0, 0, getWidth(), getHeight());
		panels[14].setBounds(0, 0, 250, 450);//invPanelTest - not added due to GUI tests	
		y_bpak = MainApplet.HEIGHT()-50;
		chatbox.setBounds(0, MainApplet.HEIGHT()-175, 400/*225*/, 125);
		//map.rescaleImage();
		validate();
	}

}