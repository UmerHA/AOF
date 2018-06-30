package mainPackage;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class ActionInfoPanelScroll extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JPanel center;
	JPanel east;	
	
	private final byte lbNum = 8; // number of labels ; the higher the number, the higher will the 
	private JLabel[] labels = new JLabel[lbNum];	
	private String[] infos = new String[500];
	private Color[] colors = new Color[500];
	
	private short showFrom = 0;
	private short infosSaved = 0;
	
	private JScrollBar aJSBar;
	
	public ActionInfoPanelScroll () {
		for (short i=0; i<500; i++)
			//infos[i] = " " + String.valueOf(i);
			infos[i] = " ";
		
		center = new JPanel ();
		east   = new JPanel ();
				
		//setBackground (Color.WHITE);			// new Color (136,170,203) 
		center.setLayout (new GridLayout (lbNum,1));	
		for (short i=0; i<lbNum; i++) {
			labels[i] = new JLabel (" ");
			center.add(labels[i]);
		}
		
		
		aJSBar = new JScrollBar(JScrollBar.VERTICAL);
		aJSBar.addAdjustmentListener(new adjLis());
		aJSBar.addMouseWheelListener(new mwLis());
		aJSBar.setMaximum(lbNum+1);
		aJSBar.setEnabled(false);
		
		east.setLayout(new BorderLayout());
		east.add(aJSBar,"Center");
		
		setLayout (new BorderLayout());
		add (center , "Center");
		add (east , "East");
		addMouseWheelListener(new mwLis());
		
		//for (int i=0;i<530;i++)
		//	addInfo(String.valueOf(i));
	}
	


	public void addInfo(String info) {
		addInformation(info,Color.BLACK);
	}
	public void addRedInfo(String info) {
		addInformation(info,Color.RED);
	}
	public void addBlueInfo(String info) {
		addInformation(info,Color.BLUE);
	}	
	public void addGreenInfo(String info) {
		addInformation(info, new Color(115,235,100));
	}
	private void addInformation(String info, Color col) {
		if (infosSaved >= 499-(lbNum-1))
			discardOldestInfo();
		
		infos[infosSaved+(lbNum-1)] = info;
		colors[infosSaved+(lbNum-1)] = col;
		
		refresh();
		
		showFrom ++;
		infosSaved ++;
		
		if (infosSaved > lbNum) {
			aJSBar.setEnabled (true);
			aJSBar.setMaximum(infosSaved);
			aJSBar.setValue(aJSBar.getValue()+1);
		}		
	}
	
	private void refresh () {
		for (short i=0; i<lbNum; i++) {
			labels[i].setForeground(colors[showFrom+i]);
			labels[i].setText(infos[showFrom+i]);		
		}
		//repaint();
	}
	private void discardOldestInfo () {
		for (int i=0;i<499;i++) {
			infos[i] = infos[i+1];
			colors[i] = colors[i+1];
		}			
		infos[499] = "";
		colors[499] = Color.BLACK;
		infosSaved--;
		showFrom--;
	}
	
	class adjLis implements AdjustmentListener {
		private boolean firstTime = true;
		
		public void adjustmentValueChanged (AdjustmentEvent e) {
			if (firstTime) {
				firstTime = false;
				return;
			}
			//System.out.println ("OP_GB.ActionInfoPanelScroll$adjLis.adjustmentValueChanged : Adjustment changed");
			showFrom = (short) (e.getValue() + (lbNum-1));
			refresh();
		}
	}
	class mwLis implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent e) {
			
			if (infosSaved <= lbNum)
				return;
			
			System.out.println ("Mouse Wheel moved : "+e.getWheelRotation());		
			showFrom += (short) e.getWheelRotation();
			
			if (showFrom < (lbNum-1))
				showFrom = (lbNum-1);
			if (showFrom > infosSaved-1)
				showFrom = (short) (infosSaved-1);
			
			aJSBar.setValue(showFrom-(lbNum-1));
			refresh();
		}
	}
	public void discardAllInfo() {
		for (short i=0; i<500; i++)
			infos[i] = " ";
		
		
		showFrom = 0;
		infosSaved = 0;
		
		aJSBar.setEnabled (false);
		refresh();
	}
}
