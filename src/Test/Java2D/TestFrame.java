package Test.Java2D;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TestFrame extends JFrame {
	
	public TestFrame() {
		construct(500,500,"Test Frame");
	}
	public TestFrame(int width, int height, String title) {
		construct(width, height, title);
	}
	private void construct(int width, int height, String title) {
		setSize(width,height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setUndecorated(true);
		
		Panel panel = new Panel();
		panel.setBounds(0,0,width, height);
		add(panel);
		
		setVisible(true);
		
		addKeyListener(new keyLis());
	}
	
	class keyLis extends KeyAdapter {
		public void keyPressed (KeyEvent e) {
			if (e.getKeyCode()==27)
				System.exit(0);
		} 
	}
	class Panel extends JPanel {
		public void paintComponent (Graphics g) {
			paintContent(g);
		}
	}
	
	// to be overridden
	public void paintContent (Graphics g) {}
}
