package test;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HTML_Test {
	public static void main(String[] args) {
		JFrame f = new JFrame("HTML test");
		JLabel lb = new JLabel("<html><h1>h1</h1></html>");
		JLabel lb2 = new JLabel("<html><h6>h6</h6></html>");
		JLabel lb3 = new JLabel("<html><font align=\"center\">center</font></html>");
		JLabel lb4 = new JLabel("<html><font color=green>green</font></html>");
		JLabel lb5 = new JLabel("<html><font color=red>R</font><font color=green>G</font><font color=blue>B</font></html>");
		JLabel lb6 = new JLabel("<html><u>underlined</u></html>");
		JLabel lb7 = new JLabel("<html><div align=center>Centered</div></html>");
		JButton b = new JButton("JButton");
		
		lb7.setHorizontalAlignment(JLabel.CENTER);
		
		f.add(lb);
		f.add(lb2);
		f.add(lb3);
		f.add(lb4);
		f.add(lb5);
		f.add(lb6);
		f.add(lb7);
		f.add(b);
		
		f.setLayout(new GridLayout(8,1));
		f.setSize(200,200);
		f.setVisible(true);
	}
}
