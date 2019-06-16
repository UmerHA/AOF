package unused.test;

/*
 *
 * This is the old version of the CC Panel.
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;//for test
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.connection.Connector;

public class CCPanel extends JComponent {//CC = Char Choose
	public static final long serialVersionUID = 1L;
	
	private static final byte  CN = 5;//CN = Max Char Num
	
	private JLabel title = new JLabel("Choose Character:");
	private JLabel[] name = new JLabel[CN];
	private JLabel[] lv   = new JLabel[CN];
	private JLabel[] id   = new JLabel[CN]; 
	private boolean[] active = new boolean[CN];
	
	private JButton newChar;
	
	private final Cursor POINTER = new Cursor(Cursor.DEFAULT_CURSOR);
	private final Cursor HAND    = new Cursor(Cursor.HAND_CURSOR);
	
	public CCPanel() {
		JPanel charchoose = new JPanel();
		charchoose.setLayout(new GridLayout((CN+1),1));
		charchoose.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		JPanel[] chars = new JPanel[CN+1];
		for (byte i=0;i<(CN+1);i++) {	
			chars[i] = new JPanel();
			chars[i].setLayout(new BorderLayout());
			
			if (i<(CN)) {
				name[i] = new JLabel();
				lv[i] = new JLabel();
				id[i] = new JLabel();
			
				name[i].setBackground(Color.CYAN);
				lv[i].setBackground(Color.CYAN);
				id[i].setBackground(Color.CYAN);
				
				name[i].setOpaque(true);
				lv  [i].setOpaque(true);
				id  [i].setOpaque(true);
			
				name[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				lv  [i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				id  [i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			
				chars[i].add(name[i],"Center");
				chars[i].add(lv  [i],"East");
				chars[i].add(id  [i],"West");

				chars[i].addMouseListener(new mLis(i));
			} else {
				newChar = new JButton("Create new Character");
				newChar.addActionListener(new actLis());
				chars[i].add(newChar);
			}
			charchoose.add(chars[i]);
		}		
		setDefault();
		
		title.setBorder(BorderFactory.createEmptyBorder(25, 50, 50, 0));
		
		setLayout(new BorderLayout());
		add(charchoose,"Center");
		add(title,"North");
	}
	
	public void setData (String[] dat) {
		if (dat.length == 2) {
			Connector.Send("Regic~Hornliu#");
			Connector.Send("Regic~Raupi#");
			return;
		}
			
		byte charAmount = (byte) ((dat.length-1)/2);
		//System.out.println(charAmount);
		
		for (byte i=0;i<charAmount;i++) {
			name[i].setText(dat[i*2+1]);
			lv[i].setText(dat[i*2+2]);
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
			lv[i].setText("");
			id[i].setText("("+(i+1)+")");
			active[i] = false;
		}
	}
	
	class mLis extends MouseAdapter {
		private byte i;
		public mLis (byte i) {this.i = i;}
		
		public void mouseEntered(MouseEvent e) {
			for (byte x=0;x<5;x++) {
				name[x].setBackground(Color.CYAN);
				lv[x].setBackground(Color.CYAN);
				id[x].setBackground(Color.CYAN);
				
				name[x].setCursor(POINTER);
				lv[x].setCursor(POINTER);
				id[x].setCursor(POINTER);
			}
			
			if (active[i]) {
				name[i].setBackground(Color.YELLOW);
				lv[i].setBackground(Color.YELLOW);
				id[i].setBackground(Color.YELLOW);
				
				name[i].setCursor(HAND);
				lv[i].setCursor(HAND);
				id[i].setCursor(HAND);
			}
		}
		public void mousePressed(MouseEvent e) {
			if (!active[i])
				return;
			Connector.Send("Loinc~"+name[i].getText()+"#");
			//MainApplet.actPlayer.setName(name[i].getText());
		}
	}
	class actLis implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//MainApplet.applet.setContentPanel(3);
		}
	}
	
	//for test
	public static void main (String[] args) {
		JFrame f = new JFrame("Char Choose Panel Test");
		f.setSize(750, 490);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
