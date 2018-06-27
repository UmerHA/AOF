package MainPackage;

import java.awt.BorderLayout;
import java.awt.Graphics;
//import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;

import Panels.ChatPanel;
import Panels.InfoPanel;
import Panels.InventoryEquipPanel;
import Panels.InventoryPanel;
import Panels.OptionPanel;

public class GamePanel extends CustomPanel {
	private static final long serialVersionUID = 1L;

	private JLayeredPane laypane;
	
	private ChatBox chatbox;
	private FieldPanel fieldScreen;
	private PanelSwitcher menuPanel;

	private InventoryPanel invScreen;
	private InventoryEquipPanel equipScreen;
	private InfoPanel infoScreen;
	private OptionPanel optScreen;
	private ChatPanel chatScreen;
	
	// Pop-Ups
	private TradePanel tradeScreen;
	private TradeInvitation invitScreen;
	private ShopPanel shopScreen;
	
	// double buffering
	//private Graphics offGraphics;
	//private Image offImage;

	public GamePanel() {
		laypane = new JLayeredPane();
		laypane.setOpaque(true);
		
		// create sub-panels -->
		menuPanel = new PanelSwitcher();
		invScreen = new InventoryPanel(MainApplet.actPlayer
				.getInventoryManager());
		equipScreen = new InventoryEquipPanel(MainApplet.actPlayer
				.getInventoryManager());
		infoScreen = new InfoPanel();
		optScreen = new OptionPanel();
		fieldScreen = new FieldPanel();
		chatScreen = new ChatPanel();
		chatbox = new ChatBox();
		tradeScreen = new TradePanel();
		invitScreen = new TradeInvitation();
		shopScreen = new ShopPanel();
		
		// shape the sub-panels and add them to laypane -->
		chatbox.setBounds();
		menuPanel.setBounds();
		fieldScreen.setBounds();
		tradeScreen.setBounds();
		invitScreen.setBounds();
		shopScreen.setBounds();
		
		laypane.add(fieldScreen,0);
		laypane.add(chatbox,1);
		laypane.add(menuPanel,2);
		laypane.add(tradeScreen);
		laypane.add(invitScreen);
		laypane.add(shopScreen);
		
		menuPanel.register(laypane);
		chatbox.register(laypane);
		tradeScreen.register(laypane);
		invitScreen.register(laypane);
		shopScreen.register(laypane);
		
		menuPanel.focus();
		chatbox.focus();
		tradeScreen.focus();
		shopScreen.focus();
		
		// hide the wanna-be pop ups
		menuPanel.setVisible(false);
		tradeScreen.setVisible(false);
		shopScreen.setVisible(false);
		
		// fill the menu panel
		menuPanel.addPanel(invScreen, "Inventory");
		menuPanel.addPanel(equipScreen, "Equipment");
		menuPanel.addPanel(infoScreen, "Stats");
		menuPanel.addPanel(optScreen, "Options");
		menuPanel.addPanel(chatScreen, "Chat");
		
		// add laypane
		setLayout(new BorderLayout());
		add(laypane,"Center");

		// add listeners -->
		addMouseListener(new mLis());
	}

	public JLayeredPane layPane () {
		return this.laypane;
	}
	public FieldPanel fieldScreen() {
		return this.fieldScreen;
	}
	public InventoryPanel invScreen() {
		return this.invScreen;
	}
	public InventoryEquipPanel equipScreen() {
		return this.equipScreen;
	}
	public OptionPanel optScreen() {
		return this.optScreen;
	}
	public ChatPanel chatScreen() {
		return this.chatScreen;
	}
	public PanelSwitcher menuPanel() {
		return this.menuPanel;
	}	
	public ChatBox chatbox() {
		return this.chatbox;
	}
	public InfoPanel infoScreen() {
		return this.infoScreen;
	}
	public ShopPanel shopScreen() {
		return shopScreen;
	}
	public TradePanel tradeScreen() {
		return tradeScreen;
	}
	public TradeInvitation invitScreen() {
		return invitScreen;
	}
	
	public void isNowActive() {
		MainApplet.map.recreateMap();
	}

	class mLis extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			grabFocus();
		}
	}
	
	public void paint(Graphics g) {
		/*if (offGraphics == null) {
			offImage = createImage(getWidth(),getHeight());
			offGraphics = offImage.getGraphics();
		}
		
		System.out.println("GamePanel :: paintnig");
		
		super.paint(offGraphics);
		g.drawImage(offImage, 0, 0, this);*/
		laypane.repaint();
	}
	public void update (Graphics g) {
		paint(g);
	}

	/*
	public void addNewTradeInvitation (String name) {
		TradeInvitation temp = new TradeInvitation(name);
		temp.setBounds(250,30,250,60);
		laypane.add(temp,new Integer(0));
		temp.register(laypane);
		temp.focus();
	}
	*/
}