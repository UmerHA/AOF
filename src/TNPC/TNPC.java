package TNPC;

import MainPackage.ConversationManager;
import MainPackage.MainApplet;
import MainPackage.NPC;
import MainPackage.ShopPanel;

public class TNPC extends NPC {
	protected int talkID;
	protected ConversationManager CVM;
	protected ShopPanel Shop;//this allows talking npcs to open shops
	
	public TNPC(int x, int y, int id, String picPath) {
		super(x, y, id, picPath);
		this.option1 = "Talk to";
		try {
			Shop = MainApplet.getGamePanel().shopScreen();
			CVM = MainApplet.CVManager();
		} catch (NullPointerException e) {
			//a null pointer exception will occur when using the visual map builder
			//--> just do nothing
		}
	}
	
	public void giveAnswer (int i) {}
}
