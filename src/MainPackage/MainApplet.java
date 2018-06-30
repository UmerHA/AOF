package MainPackage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Animations.AnimationManager;
import Connection.Connector;

public class MainApplet extends JApplet {
	private static final long serialVersionUID = 1L;

	// private static Image[] npcPics; // to be used later
	// private static Image[] fieldPics;// to be used later
	private static final int NUMBER_OF_PICS = 27;
	private static final byte NUMBER_OF_CONTENT_PANELS = 4;
	private static Image[] pics = new Image[100];
	private static JComponent[] panels = new JComponent[NUMBER_OF_CONTENT_PANELS];

	private static CardLayout cardLayout;
	private static JPanel cards;
	private static String panelNames[] = { "LI Panel", "Game Panel", "CC Panel", "CrC Panel" };

	// the map
	public static Map map = new Map();

	// the player
	public static Player actPlayer;
	public static boolean playerValid;

	// for non-static access
	public static MainApplet applet;

	// for chat box
	private static String username;

	public static void setUsername(String uname) {
		username = uname;
		System.out.println("Setting usename to " + username);
	}

	public static String getUsername() {
		System.out.println("Getting usename " + username);
		return username;
	}

	public static void addInfo(String info) {
		((GamePanel) panels[1]).chatbox().output().addInfo(info);
	}

	public static void addRedInfo(String info) {
		((GamePanel) panels[1]).chatbox().output().addRedInfo(info);
	}

	public static void addBlueInfo(String info) {
		((GamePanel) panels[1]).chatbox().output().addBlueInfo(info);
	}

	public static void addGreenInfo(String info) {
		((GamePanel) panels[1]).chatbox().output().addGreenInfo(info);
	}

	// the conversation manager
	private static ConversationManager CVManager;
	// the animations manager
	private static AnimationManager ANIManager;
	// <--

	// for game loop
	private boolean runFlag = true;
	private final short PAINT_SPEED = 250;
	// private final short TICK_SPEED = 250;
	private long /* curP, */ curT, /* prevP, */ prevT; // cur = current; prev =
														// previous ; t = tick ;
														// p = paint

	// for double buffering
	private static Dimension dim;

	public void init() {
		XPLVconverter.init();
		ANIManager = new AnimationManager();
		loadImages();

		applet = this;
		dim = getSize();

		actPlayer = new Player();

		panels[0] = new LI_Panel();
		panels[1] = new GamePanel();
		panels[2] = new CCPanel();
		panels[3] = new CrCPanel();

		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);

		for (int i = 0; i < panelNames.length; i++)
			cards.add(panels[i], panelNames[i]);

		cardLayout.show(cards, panelNames[0]);

		setLayout(new BorderLayout());
		add(cards, "Center");

		try {
			Connector.start();
		} catch (IOException e) {
			System.err.println("Error with the connector");
			e.printStackTrace();
		}

		new thread().start();
	}

	public void stop() {
		actPlayer.logOut();
		Connector.Send("logout~#");
	}

	// Game (rather Paint) Loop
	class thread extends Thread {
		public void run() {
			System.err.println("GameLoop started");
			while (runFlag) {
				curT = java.util.Calendar.getInstance().getTimeInMillis();
				if (curT - prevT >= PAINT_SPEED) {
					repaint();

					prevT = curT;
				}
			}
			System.exit(0);
		}
	}

	private void loadImages() {
		/*
		 * Images 2 - 17 are currently not in use.
		 */

		pics[0] = getImage(getCodeBase(), "pics/SUBackPic.png");
		pics[1] = getImage(getCodeBase(), "pics/CrC/CrCBackPic.png");
		pics[2] = getImage(getCodeBase(), "pics/GUI/1.png");
		pics[3] = getImage(getCodeBase(), "pics/GUI/2.png");
		pics[4] = getImage(getCodeBase(), "pics/GUI/3.png");
		pics[5] = getImage(getCodeBase(), "pics/GUI/4.png");
		pics[6] = getImage(getCodeBase(), "pics/GUI/5.png");
		pics[7] = getImage(getCodeBase(), "pics/GUI/6.png");
		pics[8] = getImage(getCodeBase(), "pics/GUI/7.png");
		pics[9] = getImage(getCodeBase(), "pics/GUI/8.png");
		pics[10] = getImage(getCodeBase(), "pics/GUI/9.png");
		pics[11] = getImage(getCodeBase(), "pics/GUI/0.png");
		pics[12] = getImage(getCodeBase(), "pics/GUI/AOF Logo.png");
		pics[13] = getImage(getCodeBase(), "pics/GUI/miniMap.png");
		pics[14] = getImage(getCodeBase(), "pics/GUI/settings.png");
		pics[15] = getImage(getCodeBase(), "pics/GUI/menu.png");
		pics[16] = getImage(getCodeBase(), "pics/GUI/gameArea.png");
		pics[17] = getImage(getCodeBase(), "pics/GUI/bpak.png");
		pics[18] = getImage(getCodeBase(), "pics/CrC/male.png");
		pics[19] = getImage(getCodeBase(), "pics/CrC/female.png");
		pics[20] = getImage(getCodeBase(), "pics/CC/SideDefault.png");
		pics[21] = getImage(getCodeBase(), "pics/CC/LeftActive.png");
		pics[22] = getImage(getCodeBase(), "pics/CC/RightActive.png");
		pics[23] = getImage(getCodeBase(), "pics/CC/Del Def.jpg");
		pics[24] = getImage(getCodeBase(), "pics/CC/Del Act.jpg");
		pics[25] = getImage(getCodeBase(), "pics/CC/Del Off.jpg");
		pics[26] = getImage(getCodeBase(), "pics/CC/cross.png");
		pics[27] = getImage(getCodeBase(), "pics/CC/cross left.png");
		// System.out.println("MainApplet :: images loaded");
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void setContentPanel(int i) {
		if (i < 0 || i >= NUMBER_OF_CONTENT_PANELS)
			return;

		switch (i) {
		case 0:
			((LI_Panel) panels[0]).isNowActive();
			break;
		case 1:
			((GamePanel) panels[1]).isNowActive();
			((GamePanel) panels[1]).menuPanel().setActivePanel((byte) 2);
			break;
		case 2:

			break;
		case 3:
			((CrCPanel) panels[3]).isNowActive();
			break;
		}

		System.err.println("Showing panel: " + panelNames[i]);
		cardLayout.show(cards, panelNames[i]);
		
		//activePanel = (byte) i;
		//setContentPane(panels[i]);
		//invalidate();
		//validate();
	}

	public static void setFlyingInfo(String info) {
		getGamePanel().chatbox().input().setText(info);
	}

	/*
	 * Images :
	 * 
	 * 0 : Background image of LogInPanel 1 - 10 : ActionKeys' Images 11 : AOF
	 * logo 12 : miniMap 13 : settings 14 : menu 15 : gameArea 16 : background
	 * image of the action keys 17 : image of inventory (for test) 18 : Male Pic
	 * (CrC) 19 : Female Pic (CrC) 20-22 : CC
	 * 
	 */
	public static Image getImage(int id) {
		if (id < 0)
			return null;
		if (id > NUMBER_OF_PICS)
			return null;

		try {
			return pics[id];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static File getFile(String directory) {
		URL url = null;
		try {
			url = new URL(MainApplet.applet.getCodeBase(), directory);
		} catch (MalformedURLException e) {
			// System.err.println("MainApplet.getFile : Error ("+e.toString()+")
			// <-- but doesnt matter");
		}
		URI uri = null;
		try {
			uri = url.toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return new File(uri);
	}

	// get Size
	public static int HEIGHT() {
		return dim.height;
	}

	public static int WIDTH() {
		return dim.width;
	}

	public static GamePanel getGamePanel() {
		return (GamePanel) panels[1];
	}

	public static LI_Panel getLIPanel() {
		return (LI_Panel) panels[0];
	}

	public static CCPanel getCCPanel() {
		return (CCPanel) panels[2];
	}

	public static CrCPanel getCrCPanel() {
		return (CrCPanel) panels[3];
	}

	public static ConversationManager CVManager() {
		if (CVManager == null)
			CVManager = new ConversationManager();
		return CVManager;
	}

	public static AnimationManager ANIManager() {
		return ANIManager;
	}

	// aiding methods
	public static String parseString(short x) {
		return String.valueOf((int) x);
	}

	public void alert(String message) {
		JOptionPane.showMessageDialog(this, message, "AOF", JOptionPane.PLAIN_MESSAGE);
	}

	public boolean confirm(String question) {
		Object[] options = { "Yes", "No" };

		int n = JOptionPane.showOptionDialog(this, question, "AOF", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		switch (n) {
		case 0:
			return true;
		case 1:
			return false;
		default:
			return false;
		}
	}

	public int yesnocancel(String question) {
		int result = JOptionPane.showConfirmDialog(this, question, "AOF", JOptionPane.YES_NO_CANCEL_OPTION);
		return result;
	}

	public int yesnocancel(String question, String title) {
		int result = JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_CANCEL_OPTION);
		return result;
	}

	public static int getRandom(int min, int max) { // get random number from
		// min to max
		int difference = (max - min) + 1;
		int r = (int) (Math.random() * difference);

		return (r + min);
	}

	public static int toMapCoord(int x) {
		return (int) Math.floor(x / 30) + 1;
	}

	public static Image getImage(String picPath) {
		return applet.getImage(applet.getCodeBase(), "pics/" + picPath);
	}
}
