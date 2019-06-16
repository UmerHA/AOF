package client.shops;

import client.App;
import client.ShopPanel;
import client.items.Item;

public class Shop {
	private ShopPanel panel;
	private Item[] item = new Item[36];
	private int[] price = new int[36];
	
	public Shop(String name) {
		panel = App.getGamePanel().shopScreen();
		panel.setName(name);
		
		for (int i=0;i<36;i++)
			item[i] = new client.items.Empty();
	}
	
	public Item getItem(int i) {
		return item[i];
	}
	public int getPrice (int id) {
		if (id<0 || id>35) {
			System.err.println("Shop.getPrice :: id must be greater than -1 and lower than 35. It is "+id);
			return 0;
		}
		return price[id];
	}

	//sub classes will call this
	protected void fillSlot(int slotID, Item item, int price) {
		this.item[slotID] = item;
		this.price[slotID] = price;
	}
}
