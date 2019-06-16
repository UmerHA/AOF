package client.npc;

import client.App;
import client.ConversationManager;
import client.ShopPanel;

public class TNPC extends NPC {
	protected int talkID;
	protected ConversationManager CVM;
	protected ShopPanel Shop;//this allows talking npcs to open shops
	
	public TNPC(int x, int y, int id, String imageName) {
		super(x, y, id, imageName);
		this.option1 = "Talk to";
		try {
			Shop = App.getGamePanel().shopScreen();
			CVM = App.CVManager();
		} catch (NullPointerException e) {
			//a null pointer exception will occur when using the visual map builder
			//--> just do nothing
		}
	}
	
	public void giveAnswer (int i) {}
}
