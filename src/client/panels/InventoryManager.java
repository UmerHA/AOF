package client.panels;

import java.io.BufferedWriter;
import java.io.IOException;

import client.App;
import client.connection.Connector;
import client.items.Item;

public class InventoryManager {

	private Item[] items = new Item[30];	// inventory
	private Item[] itemsEq = new Item[10];	// Equip Inventory
	private Item[] itemsTr = new Item[12];	// Trade Inventory
	private Item itemFly = new client.items.Empty();	// Default für jeden Start
	private short itemFlyOrigin = -1;//Default
	
	private boolean isShieldSlotEnabled = true;
	
	public InventoryManager () {
		for (short i=0;i<30;i++)
			items[i] = new client.items.Empty();
		for (short i=0;i<10;i++)
			itemsEq[i] = new client.items.Empty();
		for (short i=0;i<12;i++)
			itemsTr[i] = new client.items.Empty();	
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
		App.getGamePanel().invScreen().repaint();
	}
	public void setItem (String item, int i) {
		//System.out.println ("Setting item :: item : "+item+" | i : "+i);
		this.items[i] = Item.getItemByName(item);
		//System.out.println ("Setting item :: item : "+this.items[i].toString()+" | i : "+i);
	}

	public boolean addItem (Item item) {
		for (short i=0;i<30;i++) {
			if (items[i].getName().equals("")) {
				items[i] = item;
				//MainProg.GameWin.invScreen.repaint();
				App.getGamePanel().invScreen().refresh();
				return true;
			}
		}
		return false;
	}
	public boolean destroyItem (int position) {
		if (items[position].getName().equals("")) // if there isn't any item at that position
			return false;
		
		items[position] = new client.items.Empty();
		App.getGamePanel().invScreen().refresh();
		return true;
	}
	
	// not used anymore
	public void writeInventoryIntoFile (BufferedWriter toFile) {
		for (short i=0;i<30;i++) {
			try {
				try {
					toFile.write( items[i].getInternalName());
					toFile.newLine();
					
				} catch (NullPointerException e) {
					//System.out.println ("NPE in InventoryManager.writeInventoryIntoFile :: i : "+i);
					toFile.write("empty");
					toFile.newLine();
				}
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println (e.toString());
			} 
		}
		for (short i=0;i<10;i++) {
			try {
				try {
					toFile.write( itemsEq[i].getInternalName());
					toFile.newLine();
					
				} catch (NullPointerException e) {
					//System.out.println ("NPE in InventoryManager.writeInventoryIntoFile :: i : "+i);
					toFile.write("empty");
					toFile.newLine();
				}
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println (e.toString());
			} 
		}
	}

	// info
	
	public int getItemAmount (boolean countEquipInventory) {
		int amount = 0;
		for (short i=0;i<30;i++)
			if (!items[i].getName().equals(""))
				amount++;
		if (countEquipInventory)
			for (short i=0;i<10;i++)
				if (!itemsEq[i].getName().equals(""))
					amount++;
		
		return amount;
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
	public int getTotalAttackBonus () {
		int attackBonus = 0;
		for (short i=0;i<10;i++)
			attackBonus += itemsEq[i].getAttackBonus();
		
		return attackBonus;
	}
	public int getTotalDefenceBonus () {
		int defenceBonus = 0;
		for (short i=0;i<10;i++)
			defenceBonus += itemsEq[i].getDefenceBonus();
		
		return defenceBonus;
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
	
		if (itemFlyOrigin != -1)
			Connector.Send("invs~"+id+"~"+itemFlyOrigin+"~#");
		
		setItem(temp2, id);
		itemFly = temp1;
		itemFlyOrigin = (short) id;
		
		App.getGamePanel().invScreen().refresh();
	}	
	public void releaseFlyingItem () {
		int id = this.getFirstEmptySlot();
		
		if (id == -1)	//no free inventory slot, so there can't be any flying item
			return;
			
		Item temp1 = Item.getItemByName(items[id].getName());
		Item temp2 = Item.getItemByName(itemFly.getName());
	
		setItem(temp2, id);
		itemFly = temp1;
		
		App.getGamePanel().invScreen().refresh();		
	}
	public Item getFlyingItem () {
		return this.itemFly;
	}

	// equipping + unequipping items :
	
	public void swapItems (int positionInv, int positionEquip) {	
		// for 2-handed weapons :
		if (getItem(positionInv).isTwoHanded) {
			if (positionEquip == 3) {
				if (!getItemEq(5).getName().equals("")) {
					if (getFirstEmptySlot() == -1) {
						App.addInfo("You don't have enough space to equip this weapon.");
						return;
					}
				}
			}
		}
		
		// for shields  : 
		if (positionEquip == 5) {
			if (itemsEq[3].isTwoHanded) {
				App.addInfo("You have a two-handed weapon equipped");
				return;
			}
		}
		
		
		Item temp1 = Item.getItemByName(getItem(positionInv).getName());	// kompliziert ... <-- nicht mehr ^^
		Item temp2 = Item.getItemByName(getItemEq(positionEquip).getName());

		//System.out.println ("Original : "+getItem(positionInv).toString()+" | Copy : "+temp1.toString());
		//System.out.println ("Original : "+getItemEq(positionEquip).toString()+" | Copy : "+temp2.toString());
		
		// unequip shield if weapon is 2-handed
		if (getItem(positionInv).isTwoHanded) {
			if (positionEquip == 3) {
				if (!getItemEq(5).getName().equals("")) {
					if (getFirstEmptySlot() != -1) {
						ejectItem (5);
					}
				}
			}
		}
		
		setItemEq(temp1,positionEquip);
		setItem(temp2, positionInv);
		
		//System.out.println ("\nInventory : "+getItem(positionInv).toString() + " in position "+positionInv);
		//System.out.println ("EquipInv  : "+getItemEq(positionEquip).toString() + " in position "+positionEquip);		
		//System.out.println ("temp1:"+temp1.getName()+" temp2:"+temp2.getName());
		//System.out.println ("inv:"+slots[positionEquip].getItem().getName()+" equip:"+slots[positionEquip].getItem().getName());
			
		App.getGamePanel().equipScreen().repaint();
		App.getGamePanel().invScreen().repaint();
		
		Connector.Send("equis~"+positionInv+"~"+positionEquip+"#");
	}
	public void ejectItem (int positionEquip) {
		int positionInv = App.actPlayer.getInventoryManager().getFirstEmptySlot();
		if (positionInv == -1) {
			App.addInfo("You have no space in your inventory.");
			return;
		}
		
		Connector.Send("equis~"+positionInv+"~"+"~"+positionEquip+"~#");
		
		Item temp1 = Item.getItemByName(getItem(positionInv).getName());	// kompliziert ... <-- nicht mehr ^^
		Item temp2 = Item.getItemByName(getItemEq(positionEquip).getName());
		
		setItemEq(temp1,positionEquip);
		setItem(temp2, positionInv);
			
		App.getGamePanel().equipScreen().repaint();
		App.getGamePanel().invScreen().repaint();
	}
	
	// equipping + unequipping items into/from the TradeInventory
	
	public void addItemsTr (int positionInv) {
		if (getFirstEmptySlotTr() < 0) {
			App.addInfo("You can't trade more than 12 items at once.");
			return;
		}
		
		itemsTr[getFirstEmptySlotTr()] = items[positionInv];
	}
	public void ejectItemTr (int positionTr) {
		int positionInv = App.actPlayer.getInventoryManager().getFirstEmptySlot();
		
		Item temp1 = Item.getItemByName(getItem(positionInv).getName());
		Item temp2 = Item.getItemByName(getItemTr(positionTr).getName());
		
		setItemTr(temp1,positionTr);
		setItem(temp2, positionInv);
			
		App.getGamePanel().tradeScreen().repaint();
		App.getGamePanel().invScreen().repaint();
	}
	public void ejectAllTr () {
		for (int i = 0;i<12; i++)
			ejectItemTr(i);
	}
	
	
	// search inventory for an item
	public int searchItem(String itemName) {
		for (int i=0;i<30;i++){
			if (items[i].getName().equalsIgnoreCase(itemName))
				return i;
		}
		return -1;
	}
	
	
	// Equip Inventory 
	
	public Item getItemEq (int i) {
		return this.itemsEq[i];
	}
	public void setItemEq (Item item, int position) {	
		if (item.getEquipZone() != position && !item.getName().equals("")) {
			System.err.println("InventoryManager.setEquipItem : Can't equip "+item.getName()+" into zone "+position+".");
			return;
		}
		
		this.itemsEq[position] = item;
		if (item.isTwoHanded) {
			isShieldSlotEnabled = false;
		} else {
			isShieldSlotEnabled = true;
		}
	}
	public void setItemEq (String str, int position) {
		setItemEq (Item.getItemByName(str),position);
	}


	// Trade Inventory
	
	public Item getItemTr (int i) {
		return this.itemsTr[i];
	}
	public void setItemTr (Item item, int position) {	
		if (!item.isTradeable()) {
			App.addInfo("This item is not tradeable.");
			return;
		}
		
		this.itemsTr[position] = item;
	}
	public byte getFirstEmptySlotTr () {
		for (byte i=0;i<12;i++)
			if (itemsTr[i].getName().equals(""))
				return i;
		
		return -1;
	}
}
