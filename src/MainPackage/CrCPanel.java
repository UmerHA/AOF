package mainPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import connection.Connector;

public class CrCPanel extends JComponent {
	private static final long serialVersionUID = 1L;

	private PanelWithBackPic pic;
	
	private JLabel lb_name;
	private JTextField tx_name;
	private JLabel lb_gender;
	private JComboBox<String> cb_gender;
	private JButton create;
	private JTextArea info;
	private JButton cancel;
	
	private final Image imgMale;
	private final Image imgFemale;
	private final String strMale   = "Male";
	private final String strFemale = "Female";
	private final String infMale = "Men are usually stronger\nbut have less endurance.";
	private final String infFemale = "Women are usually weaker\nbut have a greater\nendurance.";
	
	private final Color red   = new Color(153,0,48);
	private final Color blue = new Color(112,154,209);
	
	private final byte IB = 2;//INFO BODER
	
	//DEBUG
	private static boolean inApplet = true;
	private final boolean showPaintNum = true;
	private JLabel lb_paint = new JLabel("paint num : 0");
	
	public CrCPanel() {
		if (inApplet) {
			imgMale = MainApplet.getImage(18);
			imgFemale = MainApplet.getImage(19);
			//imgBack = MainApplet.getImage(1);
		} else {
			imgMale = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CrC/male.png");
			imgFemale = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CrC/female.png");
			//imgBack = java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/CrC/CrCBackPic.png");
		}
		
		pic = new PanelWithBackPic(imgMale);
		pic.setBounds(210, 20, 350, 450);
	
		info = new JTextArea();
		info.setEditable(false);
		info.setBounds(580+IB, 55+IB, 150-2*IB, 415-2*IB);
		
		lb_name = new JLabel("Name:");
		lb_name.setBounds(30, 30, 130, 25);
		tx_name = new JTextField("");
		tx_name.setBounds(30, 60, 130, 25);
		
		lb_gender = new JLabel("Gender:");
		lb_gender.setBounds(30, 100, 130, 25);
		
		String[] dat = new String[2];
		dat[0] = strMale;
		dat[1] = strFemale;
		cb_gender = new JComboBox<String>(dat);
		cb_gender.setBounds(30, 130, 130, 25);
		cb_gender.addActionListener(new actLis());
		
		create = new JButton("Create");
		create.setBounds(30, 425, 150, 30);	
		create.addActionListener(new actLis());
		
		cancel = new JButton("Cancel");
		cancel.setBounds(580,20,150,25);
		cancel.addActionListener(new actLis());
		
		lb_paint.setBounds(0,20,50,20);
		
		setLayout(null);
		add(pic);
		add(info);
		add(lb_name);
		add(tx_name);
		add(lb_gender);
		add(cb_gender);
		add(create);
		add(cancel);
		add(lb_paint);
		
		info.setText(infMale);
	}
	
	private long paintNum = 0;
	public void paint (Graphics g) {
		if (showPaintNum) {
			paintNum++;
			lb_paint.setText("paint num : "+paintNum);
			lb_paint.repaint();
		}

		g.setColor(blue);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(red);
		g.fillRect(20, 20, 170, getHeight()-40);
		g.setColor(Color.WHITE);
		g.fillRect(info.getBounds().x-IB, info.getBounds().y-IB, info.getBounds().width+2*IB, info.getBounds().height+2*IB);
		
		pic.repaint();
		lb_name.repaint();
		tx_name.repaint();
		lb_gender.repaint();
		cb_gender.repaint();
		create.repaint();
		cancel.repaint();
		info.repaint();
		lb_paint.repaint();
	}
	public void update (Graphics g) {
		paint(g);
	}
	
	class actLis implements ActionListener {
		
	    @SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e) {
	    	if (e.getActionCommand().equals("comboBoxChanged")) {
	    		JComboBox<String> cb = (JComboBox<String>)e.getSource();
	    		String temp = (String)cb.getSelectedItem();
	    		System.out.println(temp);
		    		if (temp.equals(strMale)) {
		    			pic.setImage(imgMale);
		    			info.setText(infMale);
		    		}
		    		if (temp.equals(strFemale)) {
		    			pic.setImage(imgFemale);
		    			info.setText(infFemale);
		    		}
	    	} else if (e.getActionCommand().equals(create.getActionCommand())){
	    		if (tx_name.getText().trim().equals("")) {
	    			tx_name.setText("Enter character name here");
	    			tx_name.grabFocus();
	    		} else if (tx_name.getText().trim().equals("Enter character name here")) {
	    			return;
	    		} else {
	    			if (!inApplet)
	    				return;
	    			Connector.Send("Regic~"+tx_name.getText().trim()+"#");
	    			Connector.Send("getcl#");
	    			MainApplet.applet.setContentPanel(2);
	    		}
	    	} else if (e.getActionCommand().equals("Cancel")) {
    			MainApplet.applet.setContentPanel(2);
	    	}
	    }
	}
	
	public void isNowActive () {
		goToDefault();
	}
	private void goToDefault() {
		tx_name.setText("");
		cb_gender.setSelectedIndex(0);
	}
	//for test
	public static void main (String[] args) {
		inApplet = false;
		
		JFrame f = new JFrame("Char Create Choose Panel Test");
		f.setSize(760, 520);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.setLayout(new BorderLayout());
		f.add(new CrCPanel(),"Center");
		f.setVisible(true);
	}
}
