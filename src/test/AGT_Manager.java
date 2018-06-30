package test;

import items.*;

public class AGT_Manager {

		private Item[] items = new Item[30];	// inventory
		private Item[] itemsEq = new Item[10];	// Equip Inventory
		private Item itemFly = new items.Empty();	// Default für jeden Start
		
		private boolean isShieldSlotEnabled = true;
		
		private int itemAmount = 0;				// in inventory   <-- not used yet
		
		public AGT_Manager () {
			for (short i=0;i<30;i++)
				items[i] = null;
		}
		
		public boolean isShieldSlotEnabled () {
			return this.isShieldSlotEnabled;
		}
		
		public Item getItem (int i) {
			return this.items[i];
		}
		public Item getItem (short i) {
			return getItem ((int) i);
		}
		public void setItem (Item item, int i) {
			this.items[i] = item;
			System.out.println("Look at Manager.setItem (item,int)");
			//MainApplet.getGamePanel().invScreen().repaint();
		}
		
		public void setItem (String item, int i) {
			//System.out.println ("Setting item :: item : "+item+" | i : "+i);
			this.items[i] = Item.getItemByName(item);
			
			if (!this.items[i].getName().equals("")) 	// if item is not Item.Empty
				changeItemAmount(1);
			//System.out.println ("Setting item :: item : "+this.items[i].toString()+" | i : "+i);
		}

		public boolean addItem (Item item) {
			for (short i=0;i<30;i++) {
				if (items[i].getName().equals("")) {
					items[i] = item;
					//MainProg.GameWin.invScreen.repaint();
					System.out.println("Look at Manager.addItem(item)");
					//MainApplet.getGamePanel().invScreen().refresh();
					changeItemAmount(1);
					return true;
				}
			}
			return false;
		}
		
		public boolean destroyItem (int position) {
			if (items[position].getName().equals("")) // if there isn't any item at that position
				return false;
			
			items[position] = new items.Empty();
			System.out.println("Look at Manager.dedstroyItem(int)");
			//MainApplet.getGamePanel().invScreen().refresh();
			changeItemAmount(-1);
			return true;
		}
		
		private void changeItemAmount (int i) {
			itemAmount += i;
			//System.out.println ("InventoryManager.changeItemAmount :: itemAmount : "+itemAmount);
		}
		public int getItemAmount () {
			return this.itemAmount;
		}
		
		public int getFirstEmptySlot () {
			for (short i=0;i<30;i++)
				if (items[i].getName().equals(""))
					return i;
			
			return -1;
		}

		public double getTotalWeight () {
			return (getInventoryWeight() + getEquipWeight());
		}

		public double getInventoryWeight () {
			double r = 0.0;
			for (short i=0;i<30;i++)
				r += items[i].getWeight();
			
			return formatDouble(r);
		}
		public double getEquipWeight () {
			double r = 0.0;
			for (short i=0;i<10;i++)
				r += itemsEq[i].getWeight();
			
			return formatDouble(r);
		}
		
		public double formatDouble (double d) {
			int temp = (int) (d*100);
			double r = temp/100;
			return r;
		}
		
		// flying Item : 
		
		public void setFlyingItem (int id) {
			//System.out.println ("InvManager.setitemFly :: items[id].name : "+items[id].getName());
			//System.out.println ("InvManager.setitemFly :: item.name : "+itemFly.getName());
			
			Item temp1 = Item.getItemByName(items[id].getName());
			Item temp2 = Item.getItemByName(itemFly.getName());
		
			setItem(temp2, id);
			itemFly = temp1;
			
			System.out.println("Look at Manager.setFlyingItem(id)");
			//MainApplet.getGamePanel().invScreen().refresh();
		}
		
		public void releaseFlyingItem () {
			int id = this.getFirstEmptySlot();
			
			if (id == -1)	//no free inventory slot, so there can't be any flying item
				return;
				
			Item temp1 = Item.getItemByName(items[id].getName());
			Item temp2 = Item.getItemByName(itemFly.getName());
		
			setItem(temp2, id);
			itemFly = temp1;
			System.out.println("Look at Manager.releaseFlyingItem()");
			//MainApplet.getGamePanel().invScreen().refresh();		
		}
		
		public Item getFlyingItem () {
			return this.itemFly;
		}
}