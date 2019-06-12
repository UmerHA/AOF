package test.nullLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NLT_PanelSwitcher extends NLT_Panel {

	private final short mpn = 5;//maxPanelNum
	
	private JPanel[] panel = new JPanel[mpn];
	private TextPanel[] txtPanel = new TextPanel[mpn];
	private EmptyPanel[] ePanel = new EmptyPanel[4];
	private byte panelAmount;
	private byte activeID;
	
	private JPanel head;
	private JPanel body;
	
	private Font font = new Font("SandSerif",Font.BOLD,15);
	private Color color = new Color(128,128,255);
	private int spaceTop = 10;
	private int spaceBottom = 10;
	private int spaceLeft = 10;
	private int spaceRight = 10;
	
	
	public NLT_PanelSwitcher () {
		setLayout(new BorderLayout());
		
		head = new JPanel();
		head.setLayout(new GridLayout(1,mpn));
		for (int i=0;i<mpn;i++) {
			txtPanel[i] = new TextPanel();
			head.add(txtPanel[i]);
			if (i<4)
				ePanel[i] = new EmptyPanel(i+1);
		}
			
		body = new JPanel();
		body.setLayout(new BorderLayout());
		body.add(ePanel[0],"North");
		body.add(ePanel[1],"South");
		body.add(ePanel[2],"East");
		body.add(ePanel[3],"West");
		
		add(head,"North");
		add(body,"Center");
		
		/* for D&D */
		for (int i=0;i<mpn;i++) {
			txtPanel[i].addMouseMotionListener(getMMLis());
			txtPanel[i].addMouseListener(getMLis());
			if (i<4) {
				ePanel[i].addMouseMotionListener(getMMLis());
				ePanel[i].addMouseListener(getMLis());
			}
		}
	}

	public boolean addPanel (JPanel panel, String name) {
		//There may only be up to **mpn** panels, so :
		if (panelAmount == mpn) {
			System.err.println("PanelSwitcher :: can't add the panel ; the maximum of "+mpn+" is already reached [MainPackage.PanelSwitcher.addPanel]");
			return false;
		}
		
		this.panel[panelAmount] = panel;
		this.txtPanel[panelAmount].setData(name,panelAmount);

		head.add(txtPanel[panelAmount]);
		setActivePanel(panelAmount);
		
		/* for D&D */
		this.panel[panelAmount].addMouseMotionListener(getMMLis());
		this.panel[panelAmount].addMouseListener(getMLis());
		/* - */
		
		panelAmount ++;
		return true;
	}
	public void setActivePanel (byte index) {
		//System.out.println("PanelSwitcher.setActivePanel :: index = "+index);
		if (index > panelAmount-1)
			return;
		
		if (index == activeID)
			return;
		
		for (byte i=0;i<panelAmount;i++) {
			txtPanel[i].setActive(false);
			body.remove(panel[i]);
		}
		
		activeID = index;
		txtPanel[index].setActive(true);
		body.add(panel[index],"Center");
		
		validate();
		repaint();
	}
	public void setActivePanel (int index) {
		setActivePanel ((byte)index);
	}
	public byte getActivePanelID () {
		return this.activeID;
	}
	
	class TextPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private String name;
		private boolean active = false;
		private byte id;
		private byte borderSize = 1;
		private final Dimension size = new Dimension(50,20);
		private boolean created = false;
		
		public TextPanel () {			
			setMinimumSize(size);
			setPreferredSize(size);
			setMaximumSize(size);
		}
		private void setData (String name, byte id) {
			this.name = name;
			this.id = id;
			addMouseListener(new mLis());	
			this.created = true;
		}
		private void setActive (boolean bool) {
			this.active = bool;
		}
		
		public void paint (Graphics g) {
			if (!created)
				return;
			
			//System.out.println("TextPanel is repainting ; id : "+id+" ; isActive : "+active);
			g.setColor(color);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(java.awt.Color.BLACK);
			
			int h = getHeight();
			int w = getWidth();
			
			g.fillRect(0, 0, w, borderSize);
			if (!active)
				g.fillRect(0, h-borderSize, w, borderSize);
			g.fillRect(0, 0, borderSize, h);
			g.fillRect(w-borderSize, 0, borderSize, h);	
			
			g.drawString(name, 2, 13);
		}
		
		class mLis extends MouseAdapter {
			public void mousePressed (MouseEvent e) {
				//System.out.println("TextPanel clicked ; id : "+id);
				setActivePanel(id);
			}
		}
	}

	class EmptyPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private int type;
		public EmptyPanel (int type) {
			this.type = type;
			this.setSize();
		}
		private void setSize () {
			Dimension size = new Dimension(0,0);
			switch (type) {
			case 1 : size.height = spaceTop;break;
			case 2 : size.height = spaceBottom;break;
			case 3 : size.width = spaceLeft;break;
			case 4 : size.width = spaceRight;break;
			default : System.err.println("Error in PanelSwitcher$EmptyPanel.<init> :: type has to be either 1,2,3 or 4 ; it is : "+type);
			}
			
			setMinimumSize(size);
			setPreferredSize(size);
			setMaximumSize(size);
		}
		public void paint (Graphics g) {
			g.setColor(color);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}	
	
	public Font getFont () {
		return this.font;
	}
	public Color getBackground () {
		return this.color;
	}
	public int[] getSpaces () {
		int[] data = new int[4];
		data[0] = spaceTop;
		data[1] = spaceBottom;
		data[2] = spaceLeft;
		data[3] = spaceRight;
		return data;
	}
	
	public void setFont (Font font) {
		this.font = font;
		repaint();
	}
	public void setColor (Color color) {
		this.color = color;
		repaint();
	}
	public void setSpace (int space, String name) {
		name = name.toLowerCase();
		if (name.equals("top")) {
			spaceTop = space;
		} else if (name.equals("bottom")) {
			spaceBottom = space;
		} else if (name.equals("left")) {
			spaceLeft = space;
		} else if (name.equals("right")) {
			spaceRight = space;
		} else {
			System.err.println("PanelSwitcher.setSpace :: invalid name given ; possible names : top,bottom,left,right");
			return;
		}
		resizeEmptyPanels();
	}
	
	public void resizeEmptyPanels() {
		for (int i=0;i<4;i++)
			ePanel[i].setSize();
		repaint();
	}

	/* for D&D */
	public MouseMotionListener getMMLis() {
		return this.getMouseMotionListeners()[0];
	}
	public MouseListener getMLis() {
		return this.getMouseListeners()[0];
	}
}