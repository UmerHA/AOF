package Tests;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NLT_PopUpCreator extends NLT_Panel {
	JTextField a,b,c;
	JButton d;
	NLT_TestPanel parent;
	
	public NLT_PopUpCreator (NLT_TestPanel parent) {
		this.parent = parent;
		
		a = new JTextField("Are you a watermelon?");
		b = new JTextField("Yes");
		c = new JTextField("No");
		d = new JButton("Create new pop up");
		
		setLayout(new GridLayout(7,1));
		add(a);
		add(new JPanel());
		add(b);
		add(new JPanel());
		add(c);
		add(new JPanel());
		add(d);
		
		setBorder(BorderFactory.createLineBorder(new Color(128,128,255), 2));
		
		a.addMouseMotionListener(parent.mmLis());
		b.addMouseMotionListener(parent.mmLis());
		c.addMouseMotionListener(parent.mmLis());
		d.addMouseMotionListener(parent.mmLis());
		
		d.addActionListener(new actLis());
	}
	public class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			parent.addNewPopUp(a.getText(), b.getText(), c.getText());
		}
	}
}
