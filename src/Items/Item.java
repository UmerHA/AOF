package Items;

import java.awt.Image;

import MainPackage.myObject;

public abstract class Item extends myObject {
	public final static short maxNameLenght = 20;

	protected String internalName;
	protected double weight;
	protected boolean tradeable = true;
	protected String option1;
	public Image pic;
	protected int ID;

	protected int slotID = -1;// -1 : currently not in the inventory
	
	public Image getImage() {
		return this.pic;
	}
	public String getOption1() {
		return this.option1;
	}
	public double getWeight() {
		return this.weight;
	}
	public int getSlotID () {
		return this.slotID;
	}
	public void setSlotID (int ID) {
		this.slotID = ID;
	}
	public String getInternalName () {
		return this.internalName;
	}
	public boolean isTradeable() {
		return this.tradeable;
	}
	
	public Item(String picSrc) {
		this.pic = MainPackage.MainApplet.applet.getImage(MainPackage.MainApplet.applet.getCodeBase(),picSrc);
	}

	public static Item getItemByName(String str) {
		if (str.equalsIgnoreCase("Sword"))
			return new Sword();
		if (str.equalsIgnoreCase("Health Potion"))
			return new HealthPotion();
		if (str.equalsIgnoreCase("Big Health Potion (3)"))
			return new BigHealthPotion_3();
		if (str.equalsIgnoreCase("Big Health Potion (2)"))
			return new BigHealthPotion_2();
		if (str.equalsIgnoreCase("Big Health Potion (1)"))
			return new BigHealthPotion_1();
		if (str.equalsIgnoreCase("Energy Potion"))
			return new EnergyPotion();
		if (str.equalsIgnoreCase("Platebody"))
			return new Platebody();
		if (str.equalsIgnoreCase("Platelegs"))
			return new Platelegs();
		if (str.equalsIgnoreCase("Platelegs 2"))
			return new Platelegs2();
		if (str.equalsIgnoreCase("Mage Hat"))
			return new MageHat();
		if (str.equalsIgnoreCase("Strange Potion"))
			return new StrangePotion();
		if (str.equalsIgnoreCase("Fist Of Zen"))
			return new FistOfZen();
		if (str.equalsIgnoreCase("Slayer's Platebody"))
			return new SlayersPlatebody();
		if (str.equalsIgnoreCase("Slayer's Platelegs"))
			return new SlayersPlatelegs();
		if (str.equalsIgnoreCase("Slayer's Scythe"))
			return new SlayersScythe();
		if (str.equalsIgnoreCase("Slayer's Helmet"))
			return new SlayersHelmet();
		if (str.equalsIgnoreCase("Wood Shield"))
			return new WoodShield();
		if (str.equalsIgnoreCase("Slayer's Cape"))
			return new SlayersCape();
		if (str.equalsIgnoreCase("Slayer's Chain"))
			return new SlayersChain();
		if (str.equalsIgnoreCase("Slayer's Gloves"))
			return new SlayersGloves();
		if (str.equalsIgnoreCase("Slayer's Ring"))
			return new SlayersRing();
		if (str.equalsIgnoreCase("Slayer's Boots"))
			return new SlayersBoots();
		if (str.equalsIgnoreCase("Coconut"))
			return new Coconut();
		if (str.equalsIgnoreCase("Half Coconut"))
			return new HalfCoconut();
		// new Item

		return new Items.Empty();
	}

	public void use() {	}


	// equipable items :
	protected int equipZone = -1; // -1 : not equipable

	public boolean isTwoHanded = false; // only for weapons
	
	protected int atkBonus = 0;
	protected int defBonus = 0;

	public int getAttackBonus() {
		return this.atkBonus;
	}
	public int getDefenceBonus() {
		return this.defBonus;
	}
	public int getEquipZone() {
		return this.equipZone;
	}
	public int getID () {
		return this.ID;
	}
	
	/*  0xxx - helmets
	 *  1xxx - capes
	 *  2xxx - bodies
	 *  3xxx - legs
	 *  4xxx - shields
	 *  5xxx - chains
	 *  6xxx - rings
	 *  7xxx - gloves
	 *  8xxx - boots
	 *  9xxx - weapons
	 * 10xxx - food (cooked)
	 * 11xxx - food (raw)
	 * 12xxx - potions
	 * 13xxx - other
	 */
	public static Item getItemByID (int ID) {
		switch (ID) {
		/* - helmets - */
		case  0001 : return new SlayersHelmet();
		case  0002 : return new MageHat();
		/* - capes - */
		case  1001 : return new SlayersCape();
		/* - bodies - */
		case  2001 : return new SlayersPlatebody();
		case  2002 : return new Platebody();
		/* - legs - */
		case  3001 : return new SlayersPlatelegs();
		case  3002 : return new Platelegs();
		case  3003 : return new Platelegs2();
		/* - shields - */
		case  4001 : return new WoodShield();
		/* - chains - */
		case  5001 : return new SlayersChain();
		/* - rings - */
		case  6001 : return new SlayersRing();
		/* - gloves - */
		case  7001 : return new SlayersGloves();
		/* - boots - */
		case  8001 : return new SlayersBoots();
		/* - weapons - */
		case  9001 : return new SlayersScythe();
		case  9002 : return new Sword();
		case  9003 : return new FistOfZen();
		/* - food (cooked) - */
		/* - food (raw) - */
		/* - potions - */
		case 12001 : return new BigHealthPotion_1();
		case 12002 : return new BigHealthPotion_2();
		case 12003 : return new BigHealthPotion_3();
		case 12004 : return new HealthPotion();
		case 12005 : return new EnergyPotion();
		case 12006 : return new StrangePotion();
		/* - other - */
		case 13001 : return new Coconut();
		case 13002 : return new HalfCoconut();
		/* -- */
		default : return new Empty();
		}
	}
}
