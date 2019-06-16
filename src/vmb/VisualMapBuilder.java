package vmb;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class VisualMapBuilder extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Components
	private static JMenuBar menuBar;
	private static JPanel west;
	private static JPanel wMain;
	private static SeparatorPanel sepPanel;
	private static TopMapPanel center;
	private static TreePanel wSouth;
	private static MapLevelPanel lvPanel;
	private static MapLevelFrame lvWin;
	private static MapCoordPanel mcPanel;
	private static PanelSwitcher pSwitcher;
	private static OptionFrame optWin;
	
	private static byte showingIndex;
	private static GraphicsDevice gDevice;
	private static boolean isFullScreen;
	
	//for static access to non-static methods
	private static VisualMapBuilder vmb;
	
	public VisualMapBuilder () {
		setLayout(new BorderLayout());
		setSize(700, 700);
		
		createMenu();
		createPanels();

		setJMenuBar(menuBar);		
		setTitle("Visual Map Builder V2");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//windows
		lvWin = new MapLevelFrame();
		optWin = new OptionFrame();
		
		pSwitcher.setActivePanel((byte)1);
		vmb = this;
		
		/*for key binding <--*/
		center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK),"openBaseTab");
		center.getActionMap().put("openBaseTab",
		                             new clickOnBaseTab());
		center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK),"openFieldTab");
		center.getActionMap().put("openFieldTab",
		                             new clickOnFieldTab());
		center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK),"openNpcTab");
		center.getActionMap().put("openNpcTab",
		                             new clickOnNpcTab());
		center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0),"fullScreen");
		center.getActionMap().put("fullScreen",
		                             new changeToFullScreen());
		center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK),"saveLevel");
		center.getActionMap().put("saveLevel",
		                             new saveLevel());
		center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK),"saveLayer");
		center.getActionMap().put("saveLayer",
		                             new saveLayer());
		center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK),"openOptionWin");
		center.getActionMap().put("openOptionWin",
		                             new openOptionWin());
		center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0),"showHelp");
		center.getActionMap().put("showHelp",
									new showHelp());
		center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK),"showInfo");
		center.getActionMap().put("showInfo",
		                             new showInfo());
		/*-->*/

		GraphicsEnvironment ge = GraphicsEnvironment.
		getLocalGraphicsEnvironment();
		gDevice = ge.getDefaultScreenDevice();
		
		/*-->*/

		addWindowListener(new winLis());
	}
	
	public void createMenu () {
		int mia = 8;//menu item amount
		int ma = 5;//menu amount
		int cia = 2;//check item amount
		
		JMenu[] menu = new JMenu[ma];
		JMenuItem[] menuItem = new JMenuItem[mia];
		JCheckBoxMenuItem[] checkItem = new JCheckBoxMenuItem[cia];
		
		menuBar = new JMenuBar();
		menu[0] = new JMenu("VMB");
		menu[1] = new JMenu("Options");
		menu[2] = new JMenu("Map");
		menu[3] = new JMenu("Drawing");
		menu[4] = new JMenu("?");
		
		menuItem[0] = new JMenuItem("Info");
		menuItem[1] = new JMenuItem("Close");
		menuItem[2] = new JMenuItem("Map Options");
		menuItem[3] = new JMenuItem("Options");
		menuItem[4] = new JMenuItem("Reload Map LV");
		menuItem[5] = new JMenuItem("Save Current LV");
		menuItem[6] = new JMenuItem("Save Current Layer");
		menuItem[7] = new JMenuItem("Help");
		checkItem[0] = new JCheckBoxMenuItem("Create Fences");
		checkItem[1] = new JCheckBoxMenuItem("Show Grid");
		
		menu[0].add(menuItem[0]);
		menu[0].add(menuItem[1]);
		menu[1].add(menuItem[2]);
		menu[1].add(menuItem[3]);
		menu[2].add(menuItem[4]);
		menu[2].addSeparator();
		menu[2].add(menuItem[5]);
		menu[2].add(menuItem[6]);
		menu[3].add(checkItem[0]);
		menu[3].addSeparator();
		menu[3].add(checkItem[1]);
		menu[4].add(menuItem[7]);
		menuBar.add(menu[0]);
		menuBar.add(menu[1]);
		menuBar.add(menu[2]);
		menuBar.add(menu[3]);
		menuBar.add(menu[4]);
		
		actLis actlis = new actLis();
		itemLis itemlis = new itemLis();
		for(int i=0;i<mia;i++)
			menuItem[i].addActionListener(actlis);
		for(int i=0;i<cia;i++)
			checkItem[i].addItemListener(itemlis);
	}
	public void createPanels () {
		west = new JPanel();
		center = new TopMapPanel();
		wMain = new JPanel();
		lvPanel = new MapLevelPanel();
		wSouth = new TreePanel();
		sepPanel = new SeparatorPanel();
		mcPanel = new MapCoordPanel();
		JPanel wNorth = new JPanel();
		pSwitcher = new PanelSwitcher();
		
		west.setLayout(new BorderLayout());
		west.add(wMain,"Center");
		west.add(sepPanel,"East");
		
		wNorth.setLayout(new BorderLayout());
		wNorth.add(lvPanel,"North");
		wNorth.add(mcPanel,"South");
		
		wMain.setLayout(new BorderLayout());
		wMain.add(wNorth,"North");
		wMain.add(wSouth,"South");
		
		
		pSwitcher.addPanel(center, "Map Bases");
		pSwitcher.addPanel(center, "Map Fields");
		pSwitcher.addPanel(center, "NPCs");
		
		add(west,"West");
		add(pSwitcher,"Center");
		
		west.setPreferredSize(new Dimension(150,getHeight()));
	}
	
	public static TreePanel treePanel () {
		return wSouth;
	}
	public static TreePanelBase treeBase () {
		return wSouth.treeBase();
	}
	public static TreePanelField treeField () {
		return wSouth.treeField();
	}
	public static TreePanelNPC treeNPC () {
		return wSouth.treeNPC();
	}
	public static TopMapPanel topMapPanel () {
		return center;
	}
	public static VisualMapBuilder vmb() {
		return vmb;
	}
	public static JPanel wMain() {
		return wMain;
	}
	public static JPanel west() {
		return west;
	}
	public static MapLevelPanel lvPanel () {
		return lvPanel;
	}
	public static MapLevelFrame lvWin () {
		return lvWin;
	}
	public static MapCoordPanel mapCoordPanel () {
		return mcPanel;
	}
	public static OptionFrame optionWin () {
		return optWin;
	}
  	public static PanelSwitcher panelSwitcher () {
  		return pSwitcher;
  	}
	
	class actLis implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			//System.out.println(e.getActionCommand());
			String acmd = e.getActionCommand();			

			if (acmd.equalsIgnoreCase("info"))
				showInfo();
			if (acmd.equalsIgnoreCase("close"))
				ending();
			if (acmd.equalsIgnoreCase("reload map LV"))
				center.changeLevelBy((byte)0);//only reload
			if (acmd.equalsIgnoreCase("save current LV"))
				center.saveCurrentMapLV();
			if (acmd.equalsIgnoreCase("save current layer"))
				center.saveCurrentLayer();
			if (acmd.equalsIgnoreCase("map options"))
				lvWin.setVisible(true);
			if (acmd.equalsIgnoreCase("options"))
				optWin.setVisible(true);
			if (acmd.equalsIgnoreCase("help"))
				showHelp();
		}
	}
	class winLis extends WindowAdapter {
		public void windowClosing (WindowEvent e) {
			if (checkChanges())
				ending();
		}
	}
	class itemLis implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			String acmd = ((JCheckBoxMenuItem)e.getSource()).getText();
			JCheckBoxMenuItem src = (JCheckBoxMenuItem) e.getSource();
			
			if (acmd.equalsIgnoreCase("create fences")) {
				if (src.isSelected()) {
					center.setDrawFences(true);
				} else {
					center.setDrawFences(false);
				}
			}
			if (acmd.equalsIgnoreCase("show grid")) {
				if (src.isSelected()) {
					center.setDrawGrid(true);
				} else {
					center.setDrawGrid(false);
				}
			}
		}
	}
	
	
	private static void ending() {
		optWin.saveData();
		System.out.println("I'm ending =(");
		System.exit(0);
	}
	
	
	public static void alert(String message) {
		JOptionPane.showMessageDialog(vmb, message, "Visual Map Builder V2",
				JOptionPane.PLAIN_MESSAGE);
	}

	/*<-- main method */
	public static void main (String[] args) {
		new VisualMapBuilder();
	}
	/*-->*/
	
	public static void panelSwitchedTo(byte index) {
		//System.out.println("VisualMapBuilder.panelSwitchedTo : index = "+index);
		if (index < 0 || index > 2) {
			System.err.println("VisualMapBuilder.panelSwitchedTo : index is invalid , it my only be 0,1 or 2 ; it is : "+index);
			return;
		}
		
		switch (index) {
		case 0 : center.showBases();wSouth.showBase();break;
		case 1 : center.showFields();wSouth.showField();break;
		case 2 : center.showNPCs();wSouth.showNPC();break;
		}	
		showingIndex = index;
	}
	public static byte getShowingIndex () {
		return showingIndex;
	}

	private void showInfo () {
		ImageIcon icon = new ImageIcon("bin/pics/VMB.png",
        "VMB - Logo");
		
		JOptionPane.showMessageDialog(this,
			    "Visual Map Builder\nVersion 2.0\nby AOF (C) 2009-2010",
			    "Visual Map Builder V2",
			    JOptionPane.INFORMATION_MESSAGE,icon);
	} 
	public static boolean checkChanges() {
		if (center.baseChanged() || center.fieldsChanged() || center.npcsChanged()) {
			String layers = "changed Layers :\n";
			if (center.baseChanged()) {
				layers += "	    - Base\n";
			}
			if (center.fieldsChanged()) {
				layers += "	    - Fields\n";
			}
			if (center.npcsChanged()) {
				layers += "	    - NPCs\n";
			}
				
			
			Object[] options = {"Save","Don't save","Cancel"};
			int n = JOptionPane.showOptionDialog(vmb,
					"Do you want to save the changes?\n"+layers,
					"Visual Map Builder V2",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[2]);
			
			switch (n) {
			case 0:center.saveCurrentMapLV();return true;
			case 1:return true;
			case 2:return false;
			}
		}
		return true;
	}
	public void showHelp() {
		JOptionPane.showMessageDialog(this,
			    "It's learning my doing - just explore the VMB a bit.",
			    "Visual Map Builder V2",
			    JOptionPane.INFORMATION_MESSAGE,null);
	}
	
	
	//for key binding
	class clickOnBaseTab extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
	       pSwitcher.setActivePanel((byte)0);
	    }
	}
	class clickOnFieldTab extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
	       pSwitcher.setActivePanel((byte)1);
	    }
	}
	class clickOnNpcTab extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
	       pSwitcher.setActivePanel((byte)2);
	    }
	}
	class saveLevel extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
	       center.saveCurrentMapLV();
	    }
	}
	class saveLayer extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
	       center.saveCurrentLayer();
	    }
	}
	class changeToFullScreen extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			if (isFullScreen) {
				gDevice.setFullScreenWindow(null);
			} else {
				try {	
					gDevice.setFullScreenWindow(vmb);
				} finally {}
			}
			isFullScreen = !isFullScreen;
	    }
	}
	class openOptionWin extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			optWin.setVisible(true);
			optWin.requestFocus();
	    }
	}
	class showHelp extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			showHelp();
	    }
	}
	class showInfo extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			showInfo();
		}
	}
}
