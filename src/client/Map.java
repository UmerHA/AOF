package client;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.util.logging.Logger;

import client.map.MapBase;
import client.map.MapField;
import client.map.mapBases.*;
import client.map.mapFields.*;
import client.map.mapObjects.*;
import client.npc.NPC;

/* Map :
 *
 * The main game takes place inside the map (till now...), excluding the fighting.
 * The map draw everything in different "layers" : Bases,MapFields+MapObjects,NPCs,Player
 * The map also manages all the monsters and MapObjects
 */

public class Map {
    private static final Logger LOGGER = Util.getLogger();

	public static final int mapSize = 150;

	private DefaultHashMap<IntPair, MapBase> bases;
	public DefaultHashMap<IntPair, MapField> fields;

	private int mapX = 0;
	private int mapY = 0;
	private byte mapLV = 0;

	private int maxFieldsX;
	private int maxFieldsY;

	private MapField currentField;

	private NPC[] npcs = new NPC[mapSize * mapSize];
	private int npcNum = 0;
	
	/* - Create the map */
	public void create(String X, String Y, String Z) {
		int x = Integer.parseInt(X);
		int y = Integer.parseInt(Y);
		mapLV = Byte.parseByte(Z);

		mapX = (short) App.actPlayer.mapXofMap;
		mapY = (short) App.actPlayer.mapYofMap;

		Util.timedLog("MapPanel.create :: creating Base");
		LOGGER.info("MapPanel.create :: creating Base");
		createBaseFromFile();
		
		Util.timedLog("MapPanel.create :: creating Fields");
		LOGGER.info("MapPanel.create :: creating Fields");
		createMapFromFile();
		
		Util.timedLog("MapPanel.create :: creating NPCs");
		LOGGER.info("MapPanel.create :: creating NPCs");
		createMonFromFile();
		
		System.out.println("MapPanel.create :: created all map items");
		LOGGER.info("MapPanel.create :: created all map items");
		currentField = fields.get(new IntPair(x,y)) ;
		// repaint();
	}

	public void initMaxFields() {
		int w = App.WIDTH();
		int h = App.HEIGHT() - 10;

		maxFieldsX = (int) Math.floor(w / 30);
		maxFieldsY = (int) Math.floor(h / 30);

		// System.out.println (maxFieldsX + " " + maxFieldsY);
	}

	private void createBaseFromFile() {
		String[] data = new String[mapSize * mapSize + 1];
		int lines = 0;

		try {
			BufferedReader fromFile = Util.loadDataFileAsBufferedReader("BaseData" + mapLV);

			// Read File Line By Line
			String strLine;
			while ((strLine = fromFile.readLine()) != null) {
				if (!strLine.equals("")) {
					data[lines] = strLine;
					lines++;
					Util.timedLog("Map.createBaseFromFile |" + data[lines - 1] + "|");
					LOGGER.finer("Map.createBaseFromFile |" + data[lines - 1] + "|");
				}
			}

			fromFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			LOGGER.severe("Error: " + e.getMessage());
		}

		String mapBase = (String) data[0].subSequence(1, data[0].length() - 1);
		
		switch (mapBase) {
		case "Grass":
			bases = new DefaultHashMap<IntPair, MapBase>(new Grass());break;
		case "Road":
			bases = new DefaultHashMap<IntPair, MapBase>(new Road());break;
		case "Underground":
			bases = new DefaultHashMap<IntPair, MapBase>(new Underground());break;
		case "Nothing":
			bases = new DefaultHashMap<IntPair, MapBase>(new Nothing());break;
		default:
			bases = new DefaultHashMap<IntPair, MapBase>(new Nothing());break;		
		}
		

		String[] temp;
		for (short i = 1; i < lines; i++) {
			temp = data[i].split("~");

			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);

			switch (temp[0]) {
			case "Grass":
				bases.put(new IntPair(x,y), new Grass());break;
			case "Road": 
				bases.put(new IntPair(x,y), new Road());break;
			case "Underground":
				bases.put(new IntPair(x,y), new Underground());break;
			case "Nothing":
				bases.put(new IntPair(x,y), new Nothing());break;
			default:
				System.err.println("Map.createBaseFromFile: unknown base type " + temp[0]);
				LOGGER.warning("Map.createBaseFromFile: unknown base type " + temp[0]);
			}
		}
	}

	private void createMapFromFile() {
		String[] data = new String[mapSize * mapSize];
		int lines = 0;

		try {
			BufferedReader fromFile = Util.loadDataFileAsBufferedReader("MapData" + mapLV);

			// Read File Line By Line
			String strLine;
			while ((strLine = fromFile.readLine()) != null) {
				if (!strLine.equals("")) {
					data[lines] = strLine;
					lines++;
					Util.timedLog("Map.createMapFromFile |" + data[lines - 1] + "|");
					LOGGER.finer("Map.createMapFromFile |" + data[lines - 1] + "|");
				}
			}

			fromFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			LOGGER.severe("Error: " + e.getMessage());
		}

		fields = new DefaultHashMap<IntPair, MapField>(new PlainField());
		
		String[] temp;
		for (short i = 0; i < lines; i++) {
			temp = data[i].split("~");

			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);

			switch (temp[0]) {
			// map base
			case "Lava":
				fields.put(new IntPair(x,y), new Lava());break;
			case "Water":
				fields.put(new IntPair(x,y), new Water());break;
			case "Water1":
				fields.put(new IntPair(x,y), new Water1());break;
			case "Water2":
				fields.put(new IntPair(x,y), new Water2());break;
			case "Sand":
				fields.put(new IntPair(x,y), new Sand());break;
			case "Rock":
				fields.put(new IntPair(x,y), new Rock());break;
			case "Wood":
				fields.put(new IntPair(x,y), new Wood());break;

			// map object
			case "LadderUp":
				fields.put(new IntPair(x,y), new LadderUp());break;
			case "LadderDown":
				fields.put(new IntPair(x,y), new LadderDown());break;
			case "Tree":
				fields.put(new IntPair(x,y), new Tree());break;
			case "TropicalTree":
				fields.put(new IntPair(x,y), new TropicalTree());break;

			// special
			case "Plain":
				fields.put(new IntPair(x,y), new PlainField());break;
			case "SpawnField":
				fields.put(new IntPair(x,y), new TropicalTree());break;
			case "LavaHole":
				fields.put(new IntPair(x,y), new LavaHole());break;

			// Fences :
			// 1-Line
			case "FenceNorth":
				fields.put(new IntPair(x,y), new FenceNorth());break;
			case "FenceSouth":
				fields.put(new IntPair(x,y), new FenceSouth());break;
			case "FenceEast":
				fields.put(new IntPair(x,y), new FenceEast());break;
			case "FenceWest":
				fields.put(new IntPair(x,y), new FenceWest());break;
			// 2-Line
			case "FenceNE":
				fields.put(new IntPair(x,y), new FenceNE());break;
			case "FenceNW":
				fields.put(new IntPair(x,y), new FenceNW());break;
			case "FenceNS":
				fields.put(new IntPair(x,y), new FenceNS());break;
			case "FenceSE":
				fields.put(new IntPair(x,y), new FenceSE());break;
			case "FenceSW":
				fields.put(new IntPair(x,y), new FenceSW());break;
			case "FenceWE":
				fields.put(new IntPair(x,y), new FenceWE());break;
			// 3-line
			case "FenceNSE":
				fields.put(new IntPair(x,y), new FenceNSE());break;
			case "FenceNSW":
				fields.put(new IntPair(x,y), new FenceNSW());break;
			case "FenceNWE":
				fields.put(new IntPair(x,y), new FenceNWE());break;
			case "FenceSWE":
				fields.put(new IntPair(x,y), new FenceSWE());break;
			default:
				System.err.println("Map.createMapFromFile: unknown map field type " + temp[0]);
				LOGGER.warning("Map.createMapFromFile: unknown map field type " + temp[0]);
			}
		}
		
		Util.timedLog("Map.createMapFromFile: Size of fields is " + fields.size());
	}

	private void createMonFromFile() {
		String[] data = new String[mapSize * mapSize];
		int lines = 0;

		try {
			BufferedReader fromFile = Util.loadDataFileAsBufferedReader("MonData" + mapLV);

			// Read File Line By Line
			String strLine;
			while ((strLine = fromFile.readLine()) != null) {
				if (!strLine.equals("")) {
					data[lines] = strLine;
					lines++;
					Util.timedLog("Map.createMonFromFile |" + data[lines - 1] + "|");
					LOGGER.finer("Map.createMonFromFile |" + data[lines - 1] + "|");
				}
			}

			fromFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			LOGGER.severe("Error: " + e.getMessage());
		}

		String[] temp;
		for (short i = 0; i < lines; i++) {
			temp = data[i].split("~");
			String name = temp[0];
			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);
			int delay = Integer.parseInt(temp[3]);

			npcs[i] = NPC.createNpcByName(name, x, y, i);

			fields.get(new IntPair(x,y)).take(i);

			try {
				// npcs[i].setActive(delay);
			} catch (NullPointerException e) {
				System.out.println("Map.crMon :: npcs[" + i + "] = " + npcs[i]);
				System.out.println("Map.crMon :: delay = " + delay);
			}
			incrNPCNum();
		}
	}

	private boolean paintedOnce = false; // FOR DEBUG
	public void drawMap(int spaceLeft, int spaceTop, Graphics g) {
		initMaxFields();

		for (short i = 0; i < maxFieldsX + 2; i++) {
			for (short j = 0; j < maxFieldsY + 2; j++) {
				IntPair currentTilePosition = new IntPair(mapX + i, mapY + j);
				IntPair paintPosition = new IntPair(30 * (i - 1) + spaceLeft, 30 * (j - 1) + spaceTop);
				
				if (!paintedOnce) {
					StringBuffer debugMessage = new StringBuffer();
					
					debugMessage.append("Painting ");
					debugMessage.append(bases.get(currentTilePosition).getName() + "/" + fields.get(currentTilePosition).getName());
					debugMessage.append(" " + currentTilePosition + " ");
					debugMessage.append("at " + paintPosition);
					
					System.out.println(debugMessage);
				}
				
				g.drawImage(bases.get(currentTilePosition).getImage(), paintPosition.x, paintPosition.y, App.app);
				g.drawImage(fields.get(currentTilePosition).getImage(), paintPosition.x, paintPosition.y, App.app);
			}
		}


		for (short i = 0; i < npcNum; i++) {
			Image pic = npcs[i].getImage30();
			int x = npcs[i].getPosX();
			int y = npcs[i].getPosY();

			g.drawImage(pic, 30 * (x - 1) + spaceLeft, 30 * (y - 1) + spaceTop, App.app);
		}
		ExternalPlayer.paintAll(g);
		
		paintedOnce = true;
	}

	public void repaint() {
		App.getGamePanel().repaint();
	}

	public NPC getNpcById(int i) {
		return npcs[i];
	}

	public void setCurrentField(short x, short y) {
		if (y < 1) {
			App.actPlayer.move((short) 4);
			App.addInfo("There's no way I can go there ...");
			return;
		}
		if (x < 1) {
			App.actPlayer.move((short) 3);
			App.addInfo("There's no way I can go there ...");
			return;
		}

		currentField.exited();
		currentField = fields.get(new IntPair(x,y));
		currentField.entered();

		// MainProg.GameWin.setTitle("Current Position : " + x + " | " + y);
	}

	public void changeMapCoords(int newX, int newY) {
		int difX = mapX - newX;
		int difY = mapY - newY;

		mapX = newX;
		mapY = newY;

		for (int i = 0; i < npcNum; i++) {
			npcs[i].posX += difX;
			npcs[i].posY += difY;
		}
	}

	public int getMapX() {
		return mapX;
	}

	public int getMapY() {
		return mapY;
	}

	public void incrMapLV() {
		mapLV++;
	}

	public void decrMapLV() {
		mapLV--;
	}

	public int getMapLV() {
		return (int) mapLV;
	}

	public String getNameOfField(int x, int y) {
		return fields.get(new IntPair(x,y)).getName();
	}

	public void incrNPCNum() {
		npcNum++;
	}

	public static short checkMove(int x, int y, int dir) {
		int tempMapX = x;
		int tempMapY = y;
		int tempDir = 0;
		switch (dir) {
		case 1:
			tempMapX -= 1;
			tempDir = 3;
			break; // left
		case 2:
			tempMapY -= 1;
			tempDir = 4;
			break; // up
		case 3:
			tempMapX += 1;
			tempDir = 1;
			break; // right
		case 4:
			tempMapY += 1;
			tempDir = 2;
			break; // down
		default:
			return 0;
		}

		System.out.println("Map.checkMove : (tempX|tempY) => (" + tempMapX + "|" + tempMapY + ")");

		if (tempMapX <= 0) {
			return -1;
		}
		if (tempMapX >= 500) {
			return -1;
		}
		if (tempMapY <= 0) {
			return -1;
		}
		if (tempMapY >= 500) {
			return -1;
		}

		if (!App.map.fields.get(new IntPair(x,y)).checkAccessible(dir)) {
			// System.out.println("Map.checkMove :: start field is not
			// leavable");
			return 0;
		}

		if (!App.map.fields.get(new IntPair(tempMapX, tempMapY)).checkAccessible(tempDir)) {
			// System.out.println("Map.checkMove :: goal field is not
			// accessable");
			return 0;
		}
		return 1;
	}

	public String getNameOfOwner(int x, int y) {
		int id_of_owner = fields.get(new IntPair(x,y)).getOwnerID();
		if (id_of_owner == -1)
			return "";

		return npcs[id_of_owner].getName();
	}

	public NPC getOwner(int x, int y) {
		int id_of_owner = fields.get(new IntPair(x,y)).getOwnerID();
		if (id_of_owner > -1) {
			return npcs[id_of_owner];
		} else {
			return null;
		}
	}

	public void changeLV(byte k) {
		this.mapLV += k;
		recreateMap();
		System.out.println("Map LV changed to : " + mapLV);
	}

	public void setLV(byte k) {
		this.mapLV = k;
		recreateMap();
		System.out.println("Map LV changed to : " + mapLV);
	}

	public void recreateMap() {
		// clear arrays :
		fields = null;
		bases = null;

		for (int i = 0; i < 100; i++) {
			npcs[i] = null;
		}
		npcNum = 0;

		// store data of the new map lv :
		createBaseFromFile();
		createMapFromFile();
		createMonFromFile();
		// repaint();
	}

	public static int getMapShiftX() {
		int mapX = App.actPlayer.getMapX();
		int posX = App.actPlayer.getPosX();

		if (mapX < 8)
			return 0;
		else
			return mapX - posX;
	}

	public static int getMapShiftY() {
		int mapY = App.actPlayer.getMapY();
		int posY = App.actPlayer.getPosY();

		if (mapY < 8)
			return 0;
		else
			return mapY - posY;
	}

	// Input: MapX, Output: PosX
	public static int transformToPosX(int mapX) {
		return mapX - getMapShiftX();
	}
	// Input: MapY, Output: PosY
	public static int transformToPosY(int mapY) {
		return mapY - getMapShiftY();
	}

}
