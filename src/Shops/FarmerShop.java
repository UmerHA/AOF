package Shops;

import Items.*;

public class FarmerShop extends Shop {

	public FarmerShop() {
		super("Farmer Dave's Shop");
		fillSlot(0, new BigHealthPotion_2(),20);
		fillSlot(1, new MageHat(),30);
		fillSlot(2, new Platebody(),60);
		fillSlot(3, new Platelegs(),40);
		fillSlot(4, new HalfCoconut(),10);
		fillSlot(5, new WoodShield(),25);
	}

}
