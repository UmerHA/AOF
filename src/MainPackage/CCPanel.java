package MainPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;//for test
import javax.swing.JLabel;
import javax.swing.JPanel;

import Connection.Connector;

public class CCPanel extends JComponent {//CC = Char Choose
	public static final long serialVersionUID = 1L;
	
	private static final byte  CN = 5;//CN = Max Char Num
	
	private final Color blue = new Color(77,109,243);
	private final Color blue2= new Color(112,154,209);
	private final Color red = new Color(153,0,48);	
	private final Cursor HAND = new Cursor(Cursor.HAND_CURSOR);
	//private final Cursor POINTER = new Cursor(Cursor.DEFAULT_CURSOR);
	
	private JLabel header;
	private JLabel[] name = new JLabel[5];
	private JLabel[] time = new JLabel[5];
	private JLabel[] lv   = new JLabel[5];
	private PanelWithBackPic[] left  = new PanelWithBackPic[5];
	private PanelWithBackPic[] right = new PanelWithBackPic[5];
	private JPanel[] base = new JPanel[5];
	private PanelWithBackPic delete;
	private JButton newChar;
	
	private Image sideDef,leftAct,rightAct,crossR, crossL;
	private Image delDef,delAct,delOff;
	private boolean dpena = false;//dpena = delete panel enabled
	private boolean dpact = false;//dpact = delete panel active
	private byte actID;//for mLis
	
	private boolean[] active = new boolean[5];
	
	//
	private static boolean inApplet = true;//hybriding
	private static int W;//static for hybriding
	private static int H;
	
	public CCPanel() {
		setLayout(null);
		
		if (inApplet) {
			W = MainApplet.WIDTH();
			H = MainApplet.HEIGHT();
			
			sideDef = MainApplet.getImage(20);
			leftAct = MainApplet.getImage(21);
			rightAct= MainApplet.getImage(22);
			
			delDef = MainApplet.getImage(23);
			delAct = MainApplet.getImage(24);
			delOff = MainApplet.getImage(25);
			
			crossR = MainApplet.getImage(26);
			crossL = MainApplet.getImage(27);
		} else {
			sideDef = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CC/SideDefault.png");
			leftAct = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CC/LeftActive.png");
			rightAct= java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CC/RightActive.png");
			
			delDef = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CC/Del Def.jpg");
			delAct = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CC/Del Act.jpg");
			delOff = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CC/Del Off.jpg");
						
			crossR = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CC/cross.png");
			crossL = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CC/cross left.png");
		}
		
		header = new JLabel("Choose Character:");
		header.setBounds(10, 10, W-20, 105);
		header.setHorizontalAlignment(JLabel.CENTER);
		header.setFont(new Font("Sans Serif",Font.ITALIC,30));
		header.setBackground(blue);
		header.setOpaque(true);
		add(header);
		
		newChar = new JButton("Create new Character");
		newChar.setBounds(135,430,300,30);
		newChar.setFocusable(false);
		newChar.addActionListener(new actLis());
		add(newChar);
		
		if (inApplet) {
			delete = new PanelWithBackPic(delOff);
		} else {
			delete = new PanelWithBackPic(delDef);
		}
		delete.setFocusable(false);
		delete.setEnabled(false);
		delete.setBounds(440,430,165,30);
		delete.addMouseListener(new mLis2());
		add(delete);		
		
		for (byte i=0;i<5;i++) {
			name[i] = new JLabel("");
			time[i] = new JLabel("");
			lv  [i] = new JLabel("");
			
			name[i].setBounds(135, 150+i*55, 300, 50);
			time[i].setBounds(440,150+i*55,100,50);
			lv  [i].setBounds(545, 150+i*55, 60, 50);
			
			name[i].setHorizontalAlignment(JLabel.CENTER);
			time[i].setHorizontalAlignment(JLabel.CENTER);
			lv  [i].setHorizontalAlignment(JLabel.CENTER);
			
			name[i].setFont(new Font("Sans Serif",Font.BOLD,20));
			time[i].setFont(new Font("Sans Serif",Font.BOLD,13));
			lv  [i].setFont(new Font("Sans Serif",Font.BOLD,13));
			
			name[i].setBackground(blue2);
			time[i].setBackground(blue2);
			lv  [i].setBackground(blue2);
			
			name[i].setOpaque(true);
			time[i].setOpaque(true);
			lv  [i].setOpaque(true);
			
			add(name[i]);
			add(time[i]);
			add(lv  [i]);
			
			name[i].addMouseListener(new mLis(i));
			time[i].addMouseListener(new mLis(i));
			lv  [i].addMouseListener(new mLis(i));	
			//name[i].addMouseListener(new mLis2(i));
			//time[i].addMouseListener(new mLis2(i));
			//lv  [i].addMouseListener(new mLis2(i));	
			
			/* - */
			
			left [i] = new PanelWithBackPic(sideDef,blue);
			right[i] = new PanelWithBackPic(sideDef,blue);
			
			left [i].setBounds(10, 150+i*55, 120, 50);
			right[i].setBounds(610, 150+i*55, 120, 50);
			
			add(left [i]);
			add(right[i]);
			
			/* - */
			base[i] = new JPanel();
			base[i].setBounds(0, 150+i*55, getWidth(), 50);
		}
		
		
		setDefault();
	}
	public void paint (Graphics g) {
		g.setColor(blue);
		g.fillRect(0, 0, W, H);
		g.setColor(red);
		g.fillRect(0, 0, 10, H);
		g.fillRect(W-10, 0, 10, H);
		g.fillRect(0, 0, W	, 10);
		g.fillRect(0, H-10, W, 10);
		g.fillRect(0, 115, W, 10);
		
		g.setColor(Color.BLACK);
		header.repaint();
		newChar.repaint();
		delete.repaint();
		for (byte i=0;i<5;i++) {
			name[i].repaint();
			time[i].repaint();
			lv  [i].repaint();
			
			left [i].repaint();
			right[i].repaint();
		}
	}
	
	public void setData (String[] dat) {
		setDefault();
		if (dat.length == 2) {
			return;
		}
			
		byte charAmount = (byte) ((dat.length-1)/2);
		//System.out.println(charAmount);
		dpena = true;
		delete.setImage(delDef);
		
		for (byte i=0;i<charAmount;i++) {
			name[i].setText(dat[i*2+1]);
			time[i].setText("xx h played");
			lv[i].setText("lv "+dat[i*2+2]);
			
			name[i].setCursor(HAND);
			time[i].setCursor(HAND);
			lv  [i].setCursor(HAND);
			active[i] = true;
		}
		
		if (charAmount>4) {
			newChar.setVisible(false);
			newChar.setEnabled(false);
		}
	}
	private void setDefault() {
		for (byte i=0;i<CN;i++) {
			name[i].setText("-");
			time[i].setText("-");
			lv[i].setText("-");
			active[i] = false;
			
			dpena = false;
			delete.setImage(delOff);
		}
	}
	
	
	class mLis extends MouseAdapter {
		private byte i;
		public mLis (byte i) {this.i = i;}
		
		public void mouseEntered(MouseEvent e) {
			left [actID].setImage(sideDef);
			right[actID].setImage(sideDef);
			
			if ((inApplet && active[i])||(!inApplet && !active[i])) {
				if (!dpact) {
					left [i].setImage(leftAct);
					right[i].setImage(rightAct);
				} else {
					left [i].setImage(crossL);
					right[i].setImage(crossR);
				}
				actID = i;
			}
			
		}
		public void mouseExited(MouseEvent e) {
			if (i==actID) {
				left [actID].setImage(sideDef);
				right[actID].setImage(sideDef);
			}
		}
		public void mousePressed(MouseEvent e) {
			if (!inApplet ||!active[i])
				return;
			if (!dpact) {
				Connector.Send("Loinc~"+name[i].getText()+"#");
				MainApplet.actPlayer.setName(name[i].getText());
			} else {
				dpact=false;
				delete.setImage(delDef);
				
				Connector.Send("delc~"+name[i].getText()+"#");
				Connector.Send("getcl#");
			}
		}
		
	}
	class mLis2 extends MouseAdapter {
		public void mouseClicked (MouseEvent e) {
			if (!dpena && inApplet)
				return;
			dpact = !dpact;
			if (dpact) {
				delete.setImage(delAct);
			} else {
				delete.setImage(delDef);
			}
		}
	}
	class actLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!inApplet)
				return;
			MainApplet.applet.setContentPanel(3);
		}
	}
	
	//for test
	public static void main (String[] args) {
		JFrame f = new JFrame("Char Choose Panel Test");
		f.setSize(750, 490);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		inApplet = false;		
		W = f.getBounds().width;
		H = f.getBounds().height;
		
		f.setSize(f.getSize().width+20, f.getSize().height+40);
		
		CCPanel ccp = new CCPanel();
		String[] dat = new String[7];
		dat[0] = "egal";
		dat[1] = "Pikachu";
		dat[2] = "13";
		dat[3] = "Glumanda";
		dat[4] = "7";
		dat[5] = "Mewtwo";
		dat[6] = "99";
		
		
		ccp.setData(dat);
		
		f.setLayout(new BorderLayout());
		f.add(new CCPanel(),"Center");
		f.setVisible(true);
	}
}
