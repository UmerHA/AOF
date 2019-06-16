package unused.test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MouseOverTest extends JFrame {
	private static final long serialVersionUID = 1L;
	private static byte type = 1;
	
	public MouseOverTest () {
		JPanel c;
		JButton a,b;
		
		a = new JButton("A");
		b = new JButton("B");
		a.addMouseListener(new mLis(1));
		b.addMouseListener(new mLis(2));
		
		c = new JPanel();
		if (type==1) {
			c.setLayout(new GridLayout(1,2));
			c.add(a);
			c.add(b);
		} else if (type == 2) {
			c.setLayout(new BorderLayout());
			c.add(a,"West");
			c.add(b,"East");
		}
		c.addMouseListener(new mLis(0));
		
		setSize(300,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		setLayout(new GridLayout(1,1));
		add(c);
	}
	
	
	/* - nestes classes - */
	class mLis extends MouseAdapter {
		private int id;
		
		public mLis (int id) {
			this.id = id;
		}
		
		public void mouseEntered (MouseEvent e) {
			System.out.println("["+id+"] : Entered");
		}
		public void mouseClicked (MouseEvent e) {
			System.out.println("["+id+"] : Clicked");
		}
	}
	
	/* - main method - */
	public static void main (String[] args) {
		new MouseOverTest();
	}
}
