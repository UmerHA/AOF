package test.nullLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;


public class NLT_PopUp extends NLT_Panel {
	private static final long serialVersionUID = 1L;

	JLabel lb;
	JButton yes,no;
	
	public NLT_PopUp (NLT_TestPanel parent, String message, String yesTxt, String noTxt) {
		lb = new JLabel(message);
		yes = new JButton(yesTxt);
		no = new JButton(noTxt);
		
		JPanel south = new JPanel();
		south.setLayout(new GridLayout(1,2));
		south.add(yes);
		south.add(no);
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		center.add(lb,"North");
		center.add(new JSeparator(),"Center");
		center.add(south,"South");
		center.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(new Color(128,128,255), 2));
		add(center,"Center");
		
		yes.addMouseMotionListener(parent.mmLis());
		no.addMouseMotionListener(parent.mmLis());
		
		yes.addActionListener(new actLis());
		no.addActionListener(new actLis());
		
		yes.setActionCommand("yes");
		no.setActionCommand("no");
	}
	
	public class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			setBounds(-1,-1,0,0);
			
//			String txt = "\""+lb.getText()+"\" : ";
//			if (e.getActionCommand().equals("yes"))
//				txt+=yes.getText();
//			if (e.getActionCommand().equals("no"))
//				txt+=no.getText();
		}
	}
}
