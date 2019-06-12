package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class JTabbedPaneTest extends JTabbedPane {
	int i = 2;
	
	public JTabbedPaneTest () {
		JFrame f = new JFrame();
		myPanel[] panel = new myPanel[3];
		
		JPanel p[] = new JPanel[3];
		for (int i=0;i<3;i++) {
			panel[i] = new myPanel();
			p[i] = new JPanel();
			p[i].setLayout(new BorderLayout());
			p[i].add(panel[i],"Center");
			addTab(String.valueOf(i+1), p[i]);
		}
		
		
		
		
		
		f.add(this);
		f.setSize(200, 200);
		f.setVisible(true);
	}
		
	public static void main (String[] args) {
		new JTabbedPaneTest();
	}
	
	class myPanel extends JPanel {
		public void paint (Graphics g) {
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
			g.fillRect(50, 50, 100, 50);
		}
	}
}
