package test.nullLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class NLT_ColPanelCreator extends NLT_Panel {
	JButton d;
	NLT_TestPanel parent;
	
	public NLT_ColPanelCreator (NLT_TestPanel parent) {
		this.parent = parent;
		
		d = new JButton("Create new col panel");
		add(d);
		setBorder(BorderFactory.createLineBorder(new Color(128,128,255), 2));
		d.addMouseMotionListener(parent.mmLis());
		d.addActionListener(new actLis());
	}
	public class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			parent.addNewColPanel();
		}
	}
}
