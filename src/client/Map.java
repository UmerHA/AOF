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

	private MapBase[] bases[] = new MapBase[mapSize][mapSize];
	public MapField[] fields[] = new MapField[mapSize][mapSize];

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

		Util.timedLog("MapPanel.create :: creating plain fields");
		LOGGER.info("MapPanel.create :: creating plain fields");		
		
		for (short i = 0; i < mapSize; i++) {
			for (short j = 0; j < mapSize; j++) {
				fields[i][j] = new PlainField(i, j);
			}
		}

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
		currentField = fields[x][y];
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

		String dat = (String) data[0].subSequence(1, data[0].length() - 1);
		// System.out.println(dat);
		for (short i = 0; i < mapSize; i++) {
			for (short j = 0; j < mapSize; j++) {

				if (dat.equals("Grass"))
					bases[i][j] = new Grass(i, j);
				if (dat.equals("Road"))
					bases[i][j] = new Road(i, j);
				if (dat.equals("Underground"))
					bases[i][j] = new Underground(i, j);
				if (dat.equals("Nothing"))
					bases[i][j] = new Nothing(i, j);
			}
		}

		String[] temp;
		for (short i = 1; i < lines; i++) {
			temp = data[i].split("~");

			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);

			if (temp[0].equals("Grass"))
				fields[x][y] = new Wood(x, y);
			if (temp[0].equals("Road"))
				fields[x][y] = new Road(x, y);
			if (temp[0].equals("Underground"))
				bases[x][y] = new Underground(x, y);
			if (temp[0].equals("Nothing"))
				bases[x][y] = new Nothing(x, y);

			// new Base
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

		String[] temp;
		for (short i = 0; i < lines; i++) {
			temp = data[i].split("~");

			// System.out.println("MapPanel.createFieldFromFile :: short i =
			// "+i);
			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);

			// System.out.println(data[i]);

			// map base
			if (temp[0].equals("Lava"))
				fields[x][y] = new Lava(x, y);
			if (temp[0].equals("Water"))
				fields[x][y] = new Water(x, y);
			if (temp[0].equals("Water1"))
				fields[x][y] = new Water1(x, y);
			if (temp[0].equals("Water2"))
				fields[x][y] = new Water2(x, y);
			if (temp[0].equals("Sand"))
				fields[x][y] = new Sand(x, y);
			if (temp[0].equals("Rock"))
				fields[x][y] = new Rock(x, y);
			if (temp[0].equals("Wood"))
				fields[x][y] = new Wood(x, y);

			// map object
			if (temp[0].equals("LadderUp"))
				fields[x][y] = new LadderUp(x, y);
			if (temp[0].equals("LadderDown"))
				fields[x][y] = new LadderDown(x, y);
			if (temp[0].equals("Tree"))
				fields[x][y] = new Tree(x, y);
			if (temp[0].equals("TropicalTree"))
				fields[x][y] = new TropicalTree(x, y);

			// special
			if (temp[0].equals("Plain"))
				fields[x][y] = new PlainField(x, y);
			if (temp[0].equals("SpawnField"))
				fields[x][y] = new TropicalTree(x, y);
			if (temp[0].equals("LavaHole"))
				fields[x][y] = new LavaHole(x, y);

			// Fences :
			// 1-Line
			if (temp[0].equals("FenceNorth"))
				fields[x][y] = new FenceNorth(x, y);
			if (temp[0].equals("FenceSouth"))
				fields[x][y] = new FenceSouth(x, y);
			if (temp[0].equals("FenceEast"))
				fields[x][y] = new FenceEast(x, y);
			if (temp[0].equals("FenceWest"))
				fields[x][y] = new FenceWest(x, y);
			// 2-Line
			if (temp[0].equals("FenceNE"))
				fields[x][y] = new FenceNE(x, y);
			if (temp[0].equals("FenceNW"))
				fields[x][y] = new FenceNW(x, y);
			if (temp[0].equals("FenceNS"))
				fields[x][y] = new FenceNS(x, y);
			if (temp[0].equals("FenceSE"))
				fields[x][y] = new FenceSE(x, y);
			if (temp[0].equals("FenceSW"))
				fields[x][y] = new FenceSW(x, y);
			if (temp[0].equals("FenceWE"))
				fields[x][y] = new FenceWE(x, y);
			// 3-line
			if (temp[0].equals("FenceNSE"))
				fields[x][y] = new FenceNSE(x, y);
			if (temp[0].equals("FenceNSW"))
				fields[x][y] = new FenceNSW(x, y);
			if (temp[0].equals("FenceNWE"))
				fields[x][y] = new FenceNWE(x, y);
			if (temp[0].equals("FenceSWE"))
				fields[x][y] = new FenceSWE(x, y);

			// new Field
		}
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

			fields[x][y].take(i);

			try {
				// npcs[i].setActive(delay);
			} catch (NullPointerException e) {
				System.out.println("Map.crMon :: npcs[" + i + "] = " + npcs[i]);
				System.out.println("Map.crMon :: delay = " + delay);
			}
			incrNPCNum();
		}
	}

	public void drawMap(int spaceLeft, int spaceTop, Graphics g) {
		initMaxFields();

		for (short i = 0; i < maxFieldsX + 2; i++) {
			for (short j = 0; j < maxFieldsY + 2; j++) {
				g.drawImage(bases[mapX + i][mapY + j].getImage(), 30 * (i - 1) + spaceLeft, 30 * (j - 1) + spaceTop,
						App.app);
				g.drawImage(fields[mapX + i][mapY + j].getImage(), 30 * (i - 1) + spaceLeft, 30 * (j - 1) + spaceTop,
						App.app);
			}
		}


		for (short i = 0; i < npcNum; i++) {
			Image pic = npcs[i].getImage30();
			int x = npcs[i].getPosX();
			int y = npcs[i].getPosY();

			g.drawImage(pic, 30 * (x - 1) + spaceLeft, 30 * (y - 1) + spaceTop, App.app);
		}
		ExternalPlayer.paintAll(g);
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
		currentField = fields[x][y];
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
		return fields[x][y].getName();
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

		if (!App.map.fields[x][y].checkAccessible(dir)) {
			// System.out.println("Map.checkMove :: start field is not
			// leavable");
			return 0;
		}

		if (!App.map.fields[tempMapX][tempMapY].checkAccessible(tempDir)) {
			// System.out.println("Map.checkMove :: goal field is not
			// accessable");
			return 0;
		}
		return 1;
	}

	public String getNameOfOwner(int x, int y) {
		int id_of_owner = fields[x][y].getOwnerID();
		if (id_of_owner == -1)
			return "";

		return npcs[id_of_owner].getName();
	}

	public NPC getOwner(int x, int y) {
		int id_of_owner = fields[x][y].getOwnerID();
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
		for (short i = 0; i < mapSize; i++) {
			for (short j = 0; j < mapSize; j++) {
				fields[i][j] = new PlainField(i, j);
			}
		}
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
