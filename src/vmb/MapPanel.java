package vmb;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;

import javax.swing.JPanel;

import client.Util;
import client.map.MapBase;
import client.map.MapField;
import client.map.mapBases.*;
import client.map.mapFields.*;
import client.map.mapObjects.*;
import client.npc.NPC;

public class MapPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int mapSize = client.Map.mapSize;
	
	public String levelBase;
	public MapBase[] Bases[] = new MapBase[mapSize][mapSize];
	public MapField[] Fields[] = new MapField[mapSize][mapSize];
	public NPC[] NPCs[] = new NPC[mapSize][mapSize];
	public int[] delays[] = new int[mapSize][mapSize];
	
	private int mapX = 0;
	private int mapY = 0;
	private byte mapLV = 0;
	
	private int prev_mapX = 0;//in case map move needs to be reset
	private int prev_mapY = 0;

	private int maxFieldsX;
	private int maxFieldsY;
	
	private TopMapPanel parent;
	
	public MapPanel () {
		mapX = 0;
		mapY = 0;

		for (short i = 0; i < mapSize; i++) {
			for (short j = 0; j < mapSize; j++) {
				Fields[i][j] = new PlainField();
				delays[i][j] = 0;
			}
		}

		createBaseFromFile();
		createMapFromFile();
		createNpcFromFile();
	}


	public void initMaxFields() {
		int w = getWidth();
		int h = getHeight();

		maxFieldsX = (int) Math.floor(w / 30);
		maxFieldsY = (int) Math.floor(h / 30);
	}

	private void createBaseFromFile () {
		String[] data = new String[mapSize*mapSize];
		short lines = 0;

		try {
			BufferedReader fromFile = Util.loadDataFileAsBufferedReader("BaseData"+ mapLV);

			// Read File Line By Line
			String strLine;
			while ((strLine = fromFile.readLine()) != null) {
				if (!strLine.equals("")) {
					data[lines] = strLine;
					lines++;
					// System.out.println("|"+ data[lines-1] +"|");
				}
			}

			fromFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		levelBase = (String) data[0].subSequence(1, data[0].length()-1);
		
		for (short i=0;i<mapSize;i++) {
			for (short j=0;j<mapSize;j++) {	
				if (levelBase.equals("Grass")) 
					Bases[i][j] = new Grass();
				if (levelBase.equals("Road"))
					Bases[i][j] = new Road();
				if (levelBase.equals("Underground"))
					Bases[i][j] = new Underground();
				if (levelBase.equals("Nothing"))
					Bases[i][j] = new Nothing();
			}
		}
		

		String[] temp;
		for (short i = 1; i < lines; i++) {
			temp = data[i].split("~");
			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);

			/* - Debug
			System.out.println("MapPanel -->");
			for (int k=0;k<temp.length;k++)
				System.out.println(k+" : "+temp[k]);
			System.out.println("<--");	
			*/
			
			if (temp[0].equals("Grass"))
				Bases[x][y] = new Grass();
			if (temp[0].equals("Road"))
				Bases[x][y] = new Road();
			if (temp[0].equals("Underground"))
				Bases[x][y] = new Underground();
			if (temp[0].equals("Nothing"))
				Bases[x][y] = new Nothing();

			// new Base
		}
	}
	private void createMapFromFile() {
		String[] data = new String[mapSize*mapSize];
		short lines = 0;

		try {
			BufferedReader fromFile = Util.loadDataFileAsBufferedReader("MapData"+ mapLV);
			String strLine;
			// Read File Line By Line

			while ((strLine = fromFile.readLine()) != null) {
				if (!strLine.equals("")) {
					data[lines] = strLine;
					lines++;
					// System.out.println("|"+ data[lines-1] +"|");
				}
			}

			fromFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		String[] temp;
		for (short i = 0; i < lines; i++) {
			temp = data[i].split("~");
			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);

			// System.out.println(data[i]);
			
			//map base
			if (temp[0].equals("Lava"))
				Fields[x][y] = new Lava();
			if (temp[0].equals("Water"))
				Fields[x][y] = new Water();
			if (temp[0].equals("Water1"))
				Fields[x][y] = new Water1();
			if (temp[0].equals("Water2"))
				Fields[x][y] = new Water2();
			if (temp[0].equals("Sand"))
				Fields[x][y] = new Sand();
			if (temp[0].equals("Rock"))
				Fields[x][y] = new Rock();
			if (temp[0].equals("Wood"))
				Fields[x][y] = new Wood();
			
			//map object
			if (temp[0].equals("LadderUp"))
				Fields[x][y] = new LadderUp();
			if (temp[0].equals("LadderDown"))
				Fields[x][y] = new LadderDown();
			if (temp[0].equals("Tree"))
				Fields[x][y] = new Tree();
			if (temp[0].equals("TropicalTree"))
				Fields[x][y] = new TropicalTree();
			
			//special
			if (temp[0].equals("Plain"))
				Fields[x][y] = new PlainField();
			if (temp[0].equals("SpawnField"))
				Fields[x][y] = new TropicalTree();
			if (temp[0].equals("LavaHole"))
				Fields[x][y] = new LavaHole();

			// Fences :
			// 1-Line
			if (temp[0].equals("FenceNorth"))
				Fields[x][y] = new FenceNorth();
			if (temp[0].equals("FenceSouth"))
				Fields[x][y] = new FenceSouth();
			if (temp[0].equals("FenceEast"))
				Fields[x][y] = new FenceEast();
			if (temp[0].equals("FenceWest"))
				Fields[x][y] = new FenceWest();
			// 2-Line
			if (temp[0].equals("FenceNE"))
				Fields[x][y] = new FenceNE();
			if (temp[0].equals("FenceNW"))
				Fields[x][y] = new FenceNW();
			if (temp[0].equals("FenceNS"))
				Fields[x][y] = new FenceNS();
			if (temp[0].equals("FenceSE"))
				Fields[x][y] = new FenceSE();
			if (temp[0].equals("FenceSW"))
				Fields[x][y] = new FenceSW();
			if (temp[0].equals("FenceWE"))
				Fields[x][y] = new FenceWE();
			// 3-line
			if (temp[0].equals("FenceNSE"))
				Fields[x][y] = new FenceNSE();
			if (temp[0].equals("FenceNSW"))
				Fields[x][y] = new FenceNSW();
			if (temp[0].equals("FenceNWE"))
				Fields[x][y] = new FenceNWE();
			if (temp[0].equals("FenceSWE"))
				Fields[x][y] = new FenceSWE();


			// new Field
		}
	}
	private void createNpcFromFile() {
		String[] data = new String[mapSize*mapSize*3];
		short lines = 0;

		try {
			BufferedReader fromFile = Util.loadDataFileAsBufferedReader("MonData"+ mapLV);

			String strLine;
			// Read File Line By Line

			while ((strLine = fromFile.readLine()) != null) {
				if (!strLine.equals("")) {
					data[lines] = strLine;
					lines++;
					// System.out.println("|"+ data[lines-1] +"|");
				}
			}

			fromFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		String temp[];
		for (short i = 0; i < lines; i++) {
			temp = data[i].split("~");
			
			String name = temp[0];
			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);
			delays[x][y] = Integer.parseInt(temp[3]);
			
			NPCs[x][y] = NPC.createNpcByName(name, x, y, i);
		}
	}
	
	public void paint (Graphics g) {
		Color col  =g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(col);
		
		int spaceLeft = 0;
		int spaceTop = 0;
		
		initMaxFields();
		
		for (short i = 0; i < maxFieldsX + 2; i++) {
			for (short j = 0; j < maxFieldsY + 2; j++) {
				if (mapX+i>=mapSize || mapY+j>=mapSize) {
					g.drawImage(java.awt.Toolkit.getDefaultToolkit().getImage("bin/pics/fields/noField.jpg"), 30 * (i-1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					//break;
				} else {
				
				
				if (parent.paintBase()) {
					g.drawImage(Bases[mapX + i][mapY + j].getImage(), 30 * (i-1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					if (VisualMapBuilder.optionWin().baseShowField())
						g.drawImage(Fields[mapX + i][mapY + j].getImage(), 30* (i - 1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					try {
						if (VisualMapBuilder.optionWin().baseShowNPC())
							g.drawImage(NPCs[mapX + i][mapY + j].getImage30(), 30* (i - 1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					} catch (NullPointerException e) {
						//do nothing
					}
				}
				if (parent.paintField()) {
					if (VisualMapBuilder.optionWin().fieldShowBase()) {
						//System.out.println((mapX + i) + " "+(mapY + j));
						g.drawImage(Bases[mapX + i][mapY + j].getImage(), 30 * (i-1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					}
					g.drawImage(Fields[mapX + i][mapY + j].getImage(), 30* (i - 1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					try {
						if (VisualMapBuilder.optionWin().fieldShowNPC())
							g.drawImage(NPCs[mapX + i][mapY + j].getImage30(), 30* (i - 1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					} catch (NullPointerException e) {
						//do nothing
					}
				}
				if (parent.paintNPC()) {
					if (VisualMapBuilder.optionWin().npcShowBase())
						g.drawImage(Bases[mapX + i][mapY + j].getImage(), 30 * (i-1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					if (VisualMapBuilder.optionWin().npcShowField())
						g.drawImage(Fields[mapX + i][mapY + j].getImage(), 30* (i - 1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					try {
						g.drawImage(NPCs[mapX + i][mapY + j].getImage30(), 30* (i - 1) + spaceLeft, 30 * (j - 1) + spaceTop, this);
					} catch (NullPointerException e) {
						//do nothing
					}
				}
				}
			}
		}
		
		//if (parent.actField()==null)
		//	return;

		//first draw grid, then the active fields, then the temporary active fields and at last the current mouseOver field
		if (parent.drawGrid()) {
			Color temp = g.getColor();
			g.setColor(new Color(64,128,128));
			for (short i = 0; i < maxFieldsX + 2; i++) {
				g.drawLine(i*30, 0, i*30, getHeight());
			}
			for (short i = 0; i < maxFieldsY + 2; i++) {
				g.drawLine(0, i*30, getWidth(), i*30);
			}
			g.setColor(temp);
		}
		for (int i=0;i<parent.getActNum();i++) {
			int x = parent.actField()[i].x - mapX;
			int y = parent.actField()[i].y - mapY;
			g.drawRect((x-1)*30, (y-1)*30, 30, 30);
		}	
		
		
		if (parent.drawRect()) {
			Color temp = g.getColor();
			if (parent.drawGrid()) {
				g.setColor(new Color(175,0,0));
			} else {
				g.setColor(new Color(75,110,243));
			}
			
			for (int i=0;i<parent.getTempActNum();i++) {
				int x = parent.tempActField()[i].x - mapX;
				int y = parent.tempActField()[i].y - mapY;
				g.drawRect((x-1)*30, (y-1)*30, 30, 30);
			}	
			g.setColor(temp);
		}
		
		/*-->*/
			Color temp = g.getColor();
			g.setColor(new Color(252,242,65));
			int x = parent.getMouseOverX();
			int y = parent.getMouseOverY();
			g.drawRect((x-1)*30, (y-1)*30, 30, 30);
			g.setColor(temp);
		/*<--*/
		
		
		//System.out.println("MapPanel.paint :: "+x+" ; "+y);
		//System.out.println("MapPanel.paint :: "+((x-1)*30)+" ; "+((y-1)*30));
		
	}

	
	
	public void changeMapCoords (int newX, int newY) {
		prev_mapX = mapX;
		prev_mapY = mapY;
		
		mapX = newX;
		mapY = newY;
		
		if (mapX<0)
			mapX = 0;
		if (mapY<0)
			mapY = 0;
		
		//System.out.println("MapPanel.changeMapCoords :: "+mapX+" ; "+mapY);
		repaint();
	}
	public void resetMapMove () {
		changeMapCoords(prev_mapX, prev_mapY);
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
		return Fields[x][y].getName();
	}

	
	public void changeLV (byte k) {
		this.mapLV += k;
		recreateMap();
		System.out.println("Map LV changed to : "+mapLV);
	}
	
	public void recreateMap () {
		// clear arrays :
		for (short i = 0; i < mapSize; i++) {
			for (short j = 0; j < mapSize; j++) {
				Fields[i][j] = new PlainField();
				NPCs[i][j] = null;
			}
		}
		
		// store data of the new map lv :
		createBaseFromFile();
		createMapFromFile();
		createNpcFromFile();
		repaint();
	}

	public void setLV(byte k) {		
		this.mapLV = k;
		recreateMap();
		System.out.println("Map LV changed to : "+mapLV);	
	}

	public void setTopMapPanel (TopMapPanel tmp) {
		this.parent = tmp;
	}
}
