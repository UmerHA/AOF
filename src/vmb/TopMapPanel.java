package vmb;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import map.MapBase;
import map.MapField;
import npc.NPC;

public class TopMapPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private MapPanel map;
	private CoordPanel xPanel;
	private CoordPanel yPanel;
	private JLabel info;
	private Point[] actField = new Point[1000];
	private int actNum = 0;// number of active fields

	private int startX, startY;
	private int stopX, stopY;
	private boolean drawRect;
	private Point[] tempActField = new Point[1000];
	private int tempActNum = 0;
	private int tmpStrX, tmpStrY;// temp Start XY
	private int tmpStpX, tmpStpY;
	private int mouseOverX, mouseOverY;

	private boolean paintBase = true;
	private boolean paintField = true;
	private boolean paintNPC = false;

	private NumberOnlyTextField numField;
	private TreePanelNPC npcTreePane;

	private boolean baseChanged = false;
	private boolean fieldsChanged = false;
	private boolean npcsChanged = false;

	public byte highestLevel = 0;
	public byte lowestLevel = 0;
	
	private boolean drawFences = false; 
	private boolean showGrid = false;
	
	// constructor
	public TopMapPanel() {
		map = new MapPanel();
		xPanel = new CoordPanel(CoordPanel.HORIZONTAL);
		yPanel = new CoordPanel(CoordPanel.VERTICAL);
		info = new JLabel("Welcome");

		map.setTopMapPanel(this);
		xPanel.setTopMapPanel(this);
		yPanel.setTopMapPanel(this);

		map.addMouseMotionListener(new mmLis());
		map.addMouseListener(new mLis());
		map.addKeyListener(new keyLis());
		map.setFocusable(true);

		setLayout(new BorderLayout());
		add(map, "Center");
		add(xPanel, "North");
		add(yPanel, "East");
		add(info, "South");

		for (int i = 0; i < 1000; i++) {
			actField[i] = new Point(0, 0);
			tempActField[i] = new Point(0, 0);
		}

		map.requestFocus();
	}

	// listener
	class mmLis extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();

			int mx = toMapCoord(x) + map.getMapX();
			int my = toMapCoord(y) + map.getMapY();

			refreshCoordPanels(x, y);

			String temp = " (" + mx + "|" + my + ")  [ ";
			switch (VisualMapBuilder.getShowingIndex()) {
			case 0:
				temp += map.Bases[mx][my].getName();
				break;
			case 1:
				temp += map.Fields[mx][my].getName();
				break;
			case 2:
				try {
					temp += map.NPCs[mx][my].getName();
				} catch (NullPointerException npe) {
					temp += "None";
				}
				break;
			}
			temp += " ]";
			info.setText(temp);
			
			if (mouseOverX != toMapCoord(x)) {
				mouseOverX = toMapCoord(x);
				map.repaint();
			}
			if (mouseOverY != toMapCoord(y)) {
				mouseOverY = toMapCoord(y);
				map.repaint();
			}
		}
		public void mouseDragged(MouseEvent e) {
			stopX = e.getX();
			stopY = e.getY();
			
			if (stopX<0)
				stopX=0;
			if (stopY<0)
				stopY=0;
				
			refreshTempActFields();
			map.repaint();
			refreshCoordPanels(stopX, stopY);
			
			if (mouseOverX != toMapCoord(e.getX())) {
				mouseOverX = toMapCoord(e.getX());
				map.repaint();
			}
			if (mouseOverY != toMapCoord(e.getY())) {
				mouseOverY = toMapCoord(e.getY());
				map.repaint();
			}
		}
	}
	class mLis extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			map.grabFocus();

			startX = e.getX();
			startY = e.getY();
			stopX = startX;
			stopY = startY;

			drawRect = true;
		}
		public void mouseReleased(MouseEvent e) {
			map.grabFocus();

			drawRect = false;

			if (stopX == startX && stopY == startY) {
				if (e.isControlDown()) {
					if (actNum == 1000) {
						VisualMapBuilder.alert(
								"You can only select up tp 1000 fields!");
						return;
					}

					actField[actNum].x = toMapCoord(startX) + map.getMapX();
					actField[actNum].y = toMapCoord(startY) + map.getMapY();
					actNum++;
					VisualMapBuilder.treePanel().deselectAll();
				} else {
					actNum = 0;
					actField[0].x = toMapCoord(startX) + map.getMapX();
					actField[0].y = toMapCoord(startY) + map.getMapY();
					actNum = 1;

					switch (VisualMapBuilder.getShowingIndex()) {
					case 0:
						VisualMapBuilder.treePanel().select(
								map.Bases[actField[0].x][actField[0].y]
										.getName());
						break;
					case 1:
						VisualMapBuilder.treePanel().select(
								map.Fields[actField[0].x][actField[0].y]
										.getName());
						break;
					case 2:
						// not every field has to has a npc, so a
						// NullPointerException could occur
						try {
							VisualMapBuilder.treePanel().select(
									map.NPCs[actField[0].x][actField[0].y]
											.getName());
						} catch (NullPointerException npe) {
							VisualMapBuilder.treeNPC().select("None");
						}
						break;
					}

				}
			} else {
				if (drawFences) {
					drawFences();
					return;
				}
				
				
				if (e.isControlDown()) {
					//System.out.println("TopMapPanel.mLis :: old actNum : "+ actNum);
					/**/
					if (actNum == 1000) {
						VisualMapBuilder.alert(
								"You can only select up tp 1000 fields!");
						return;
					}
					int curActNum = actNum;
					for (int i = 0; i < tempActNum; i++) {
						actField[i + curActNum].x = tempActField[i].x;
						actField[i + curActNum].y = tempActField[i].y;
						actNum++;
						//System.out.println("actField ("+actField[i+curActNum].x+"|"+actField[i+curActNum].y+") changed");
					}
					VisualMapBuilder.treePanel().deselectAll();
					/**/
					//System.out.println("TopMapPanel.mLis :: new actNum : "+ actNum);
				} else {
					actNum = 0;
					//System.out.println("TopMapPanel.mLis :: old actNum : "+actNum);
					for (int i = 0; i < tempActNum; i++) {
						actField[i].x = tempActField[i].x;
						actField[i].y = tempActField[i].y;
						actNum++;
						//System.out.println("actField ("+actField[i].x+"|"+actField[i].y+") changed");
					}
					//System.out.println("TopMapPanel.mLis :: new actNum : "+actNum);
					if (actNum == 1) {
						VisualMapBuilder.treePanel().select(
								map.Fields[actField[0].x][actField[0].y]
										.getName());
					} else {
						VisualMapBuilder.treePanel().deselectAll();
					}
				}
			}
			if (paintNPC)
				refreshCurrenDelay();
			map.repaint();
		}
		public void mouseExited(MouseEvent e) {
			mouseOverX = -31;
			mouseOverY = -31;
			refreshCoordPanels(-30, -30);
			map.repaint();
		}
	}
	class keyLis extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int x = map.getMapX();
			int y = map.getMapY();

			boolean mapChanged = false;

			switch (e.getKeyCode()) {
			case 65:// a
			case 37:
				map.changeMapCoords(x - 1, y);
				mapChanged = true;
				break;// left
			case 87:// w
			case 38:
				map.changeMapCoords(x, y - 1);
				mapChanged = true;
				break;// up
			case 68:// d
			case 39:
				map.changeMapCoords(x + 1, y);
				mapChanged = true;
				break;// right
			case 83:// s
			case 40:
				map.changeMapCoords(x, y + 1);
				mapChanged = true;
				break;// down
			case 81:// q
				map.changeMapCoords(x - 1, y - 1);
				mapChanged = true;
				break;
			case 69:// e
				map.changeMapCoords(x + 1, y - 1);
				mapChanged = true;
				break;
			case 67:// c
				map.changeMapCoords(x + 1, y + 1);
				mapChanged = true;
				break;
			case 89:// y
				map.changeMapCoords(x - 1, y + 1);
				mapChanged = true;
				break;
			}

			if (mapChanged) {
				VisualMapBuilder.mapCoordPanel().setMapCoords(map.getMapX(),
						map.getMapY());
				xPanel.repaint();
				yPanel.repaint();
			}
		}
	}
	class keyLisSmall extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int x = map.getMapX();
			int y = map.getMapY();

			boolean mapChanged = false;

			switch (e.getKeyCode()) {
			case 65:// a
				map.changeMapCoords(x - 1, y);
				mapChanged = true;
				break;
			case 87:// w
				map.changeMapCoords(x, y - 1);
				mapChanged = true;
				break;
			case 68:// d
				map.changeMapCoords(x + 1, y);
				mapChanged = true;
				break;
			case 83:// s
				map.changeMapCoords(x, y + 1);
				mapChanged = true;
				break;// down
			case 81:// q
				map.changeMapCoords(x - 1, y - 1);
				mapChanged = true;
				break;
			case 69:// e
				map.changeMapCoords(x + 1, y - 1);
				mapChanged = true;
				break;
			case 67:// c
				map.changeMapCoords(x + 1, y + 1);
				mapChanged = true;
				break;
			case 89:// y
				map.changeMapCoords(x - 1, y + 1);
				mapChanged = true;
				break;
			}

			if (mapChanged) {
				VisualMapBuilder.mapCoordPanel().setMapCoords(map.getMapX(),
						map.getMapY());
				xPanel.repaint();
				yPanel.repaint();
			}
		}
	} 
	
	public Point[] actField() {
		return this.actField;
	}
	public Point[] tempActField() {
		return this.tempActField;
	}
	public int getActNum() {
		return this.actNum;
	}
	public int getTempActNum() {
		return this.tempActNum;
	}
	public int tmpStrX() {
		return this.tmpStrX;
	}
	public int tmpStrY() {
		return this.tmpStrY;
	}
	public int tmpStpX() {
		return this.tmpStpX;
	}
	public int tmpStpY() {
		return this.tmpStpY;
	}
	public KeyListener getKeyLis() {
		return new keyLis();
	}
	public KeyListener getSmallKeyLis() {
		return new keyLisSmall ();
	}
	public boolean paintBase() {
		return this.paintBase;
	}
	public boolean paintField() {
		return this.paintField;
	}
	public boolean paintNPC() {
		return this.paintNPC;
	}
	public boolean drawRect() {
		return drawRect;
	}
	public boolean getDrawFences() {
		return this.drawFences;
	}
	public void setDrawFences(boolean bool) {
		this.drawFences = bool;
		//System.out.println("TopMapPanel.setDrawFences :: bool = "+bool);
	}
	private void drawFences() {
		if (!paintField) {
			System.out.println("TopMapPanel.drawFences :: cannot draw fenes while not editing the field layer");
			return;
		}
		
		int x1 = toMapCoord(tmpStrX);
		int y1 = toMapCoord(tmpStrY);
		int x2 = toMapCoord(tmpStpX);
		int y2 = toMapCoord(tmpStpY);

		int w = x2 - x1 + 1;
		int h = y2 - y1 + 1;
		
		if (h==1 || w==1) {
			System.out.println("TopMapPanel.drawFences :: cannot daw fences beacause either the width or height difference is lower than one.");
			return;
		}
		
		int mx = map.getMapX();
		int my = map.getMapY();
		
		//corners
		map.Fields[x1+mx][y1+my] = new map.mapFields.FenceNW(0,0);
		map.Fields[x1+mx][y2+my] = new map.mapFields.FenceSW(0,0);
		map.Fields[x2+mx][y1+my] = new map.mapFields.FenceNE(0,0);
		map.Fields[x2+mx][y2+my] = new map.mapFields.FenceSE(0,0);
		//north+south side:
		for (int i=1;i<w-1;i++) {
			map.Fields[x1+i+mx][y1+my] = new map.mapFields.FenceNorth(0,0);
			map.Fields[x1+i+mx][y2+my] = new map.mapFields.FenceSouth(0,0);
		}
		//east+west side:
		for (int i=1;i<h-1;i++) {
			map.Fields[x1+mx][y1+i+my] = new map.mapFields.FenceWest(0,0);
			map.Fields[x2+mx][y1+i+my] = new map.mapFields.FenceEast(0,0);
		}
		fieldsChanged = true;
		map.repaint();
	}
	public void setDrawGrid (boolean bool) {
		this.showGrid = bool;
		map.repaint();
	}
	public boolean drawGrid() {
		return this.showGrid;
	}
	public int getMouseOverX() {
		return this.mouseOverX;
	}
	public int getMouseOverY() {
		return this.mouseOverY;
	}
	
	private void refreshTempActFields() {
		if (startX < stopX) {
			tmpStrX = startX;
			tmpStpX = stopX;
		} else {
			tmpStrX = stopX;
			tmpStpX = startX;
		}
		if (startY < stopY) {
			tmpStrY = startY;
			tmpStpY = stopY;
		} else {
			tmpStrY = stopY;
			tmpStpY = startY;
		}

		int x1 = toMapCoord(tmpStrX);
		int y1 = toMapCoord(tmpStrY);
		int x2 = toMapCoord(tmpStpX);
		int y2 = toMapCoord(tmpStpY);

		int w = x2 - x1 + 1;
		int h = y2 - y1 + 1;

		if (w * h > 999) {
			VisualMapBuilder.alert(
					"You may only select up to 1000 fields");
		}

		/*
		 * System.out.println("TopMapPanel -->");
		 * System.out.println("startXY : "+startX+" | "+startY);
		 * System.out.println("startXY : "+stopX+" | "+stopY);
		 * System.out.println("xy 1    : "+x1+" | "+y1);
		 * System.out.println("xy 2    : "+x2+" | "+y2);
		 * System.out.println("size    : "+w+" ; "+h);
		 * System.out.println("TopMapPanel <--");
		 */

		tempActNum = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				tempActField[tempActNum] = new Point(j + x1 + map.getMapX(), i
						+ y1 + map.getMapY());
				tempActNum++;
			}
		}
	}
	public void changeActiveMapField(MapField mp) {
		if (mp == null) {
			// System.err
			// .println("TopMapPanel.changeActiveMapField :: mp = null  <--- ist aber egal ^^");
			return;
		}

		boolean changed = false;
		for (int i = 0; i < actNum; i++) {
			if (!map.Fields[actField[i].x][actField[i].y].getName().equals(
					mp.getName())) {
				map.Fields[actField[i].x][actField[i].y] = mp;
				changed = true;
			}
		}
		if (changed) {
			map.repaint();
			fieldsChanged = true;
		}
	}
	public void changeActiveMapBase(MapBase mb) {
		if (mb == null) {
			// System.err
			// .println("TopMapPanel.changeActiveMapBase :: mb = null  <--- ist aber egal ^^");
			return;
		}

		for (int i = 0; i < actNum; i++) {
			try {
			map.Bases[actField[i].x][actField[i].y] = mb;
			} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("NullPointerException at VMB_Version2.TopMapPanel.changeActiveMapBase");
			}
		}
		map.repaint();
		baseChanged = true;
	}
	public void changeActiveNPC(NPC npc) {

		if (npc == null) {
			// System.err
			// .println("TopMapPanel.changeActiveMapBase :: npc = null  <--- ist aber egal ^^");
			if (!VisualMapBuilder.treeNPC().isNoneSelected())
				return;
		}

		for (int i = 0; i < actNum; i++)
			map.NPCs[actField[i].x][actField[i].y] = npc;
		map.repaint();
		npcsChanged = true;
	}

	public void saveCurrentMapLV() {
		System.out.println("TopMapPanel :: saving current map...");
		saveBase();
		saveField();
		saveNPC();
		System.out.println("TopMapPanel :: current map saved ...");
		if (VisualMapBuilder.optionWin().showSaveMsg())
			VisualMapBuilder.alert("Current map level saved.");

		baseChanged = false;
		fieldsChanged = false;
		npcsChanged = false;
	}
	public void saveCurrentLayer() {
		String temp = "Layer [";

		if (paintBase) {
			saveBase();
			temp += "Base";
			baseChanged = false;
		}
		if (paintField) {
			saveField();
			temp += "Field";
			fieldsChanged = false;
		}
		if (paintNPC) {
			saveNPC();
			temp += "NPC";
			npcsChanged = false;
		}
		temp += "] saved.";
		if (VisualMapBuilder.optionWin().showSaveMsg())
			VisualMapBuilder.alert(temp);
	}
	public void saveBase() {
		try {
			String fileName = VisualMapBuilder.PATH + "BaseData" + map.getMapLV();

			// delete and re-create file :
			File aFile = new File(fileName);
			aFile.delete();
			aFile.createNewFile();

			// write into file :

			try {

				BufferedWriter toFile = new BufferedWriter(new FileWriter(
						fileName));

				toFile.write("[" + map.levelBase + "]");// level base
				toFile.newLine();

				for (short i = 0; i < MapPanel.mapSize; i++) {
					for (short j = 0; j < MapPanel.mapSize; j++) {
						MapBase f = map.Bases[i][j];

						if (!f.getName().equalsIgnoreCase(map.levelBase)) {
							toFile.write(f.getName());
							toFile.write("~");
							toFile.write(String.valueOf(i));
							toFile.write("~");
							toFile.write(String.valueOf(j));
							toFile.newLine();
						}
					}
				}

				toFile.close();

				// MainProg.alert("Data Saved");
			} catch (IOException e) {
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void saveField() {
		try {
			String fileName = VisualMapBuilder.PATH + "MapData" + map.getMapLV();

			// delete and re-create file :
			File aFile = new File(fileName);
			aFile.delete();
			aFile.createNewFile();

			// write into file :

			try {

				BufferedWriter toFile = new BufferedWriter(new FileWriter(
						fileName));

				for (short i = 0; i < MapPanel.mapSize; i++) {
					for (short j = 0; j < MapPanel.mapSize; j++) {
						MapField f = map.Fields[i][j];

						if (!f.getName().equalsIgnoreCase("PlainField")) {
							toFile.write(f.getName());
							toFile.write("~");
							toFile.write(String.valueOf(i));
							toFile.write("~");
							toFile.write(String.valueOf(j));
							toFile.newLine();
						}
					}
				}

				toFile.close();

				// MainProg.alert("Data Saved");
			} catch (IOException e) {
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void saveNPC() {
		try {
			String fileName = VisualMapBuilder.PATH + "MonData" + map.getMapLV();

			// delete and re-create file :
			File aFile = new File(fileName);
			aFile.delete();
			aFile.createNewFile();

			// write into file :

			try {

				BufferedWriter toFile = new BufferedWriter(new FileWriter(
						fileName));

				for (short i = 0; i < MapPanel.mapSize; i++) {
					for (short j = 0; j < MapPanel.mapSize; j++) {
						NPC npc = map.NPCs[i][j];

						if (npc != null) {
							toFile.write(npc.getName());
							toFile.write("~");
							toFile.write(String.valueOf(i));
							toFile.write("~");
							toFile.write(String.valueOf(j));
							toFile.write("~");
							toFile.write(String.valueOf(map.delays[i][j]));
							toFile.newLine();
						}
					}
				}

				toFile.close();

				// MainProg.alert("Data Saved");
			} catch (IOException e) {
				System.out.println(e);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public boolean baseChanged() {
		return this.baseChanged;
	}
	public boolean fieldsChanged() {
		return this.fieldsChanged;
	}
	public boolean npcsChanged() {
		return this.npcsChanged;
	}

	public int toMapCoord(int x) {
		return (int) Math.floor(x / 30) + 1;
	}
	private void refreshCoordPanels(int x, int y) {
		int tempX = toMapCoord(x);
		int tempY = toMapCoord(y);

		xPanel.setActNum(tempX);
		yPanel.setActNum(tempY);
	}

	public int getMapLV() {
		return map.getMapLV();
	}
	public void changeLevelBy(byte k) {
		changeLV(k);
	}
	public void setLevel(byte k) {
		int dif = k - map.getMapLV();
		changeLV((byte) dif);
	}
	private void changeLV(byte k) {
		int temp = map.getMapLV() + k;
		File file = new File("bin/data/MapData" + temp);

		if ((!file.exists()) || (!file.isFile())) {
			VisualMapBuilder.alert(
					"Map Level " + temp + " does not exist.");
			return;
		}

		if (VisualMapBuilder.checkChanges())//first check if the current needs saving
			map.changeLV(k);
	}
	
	public int getMapX() {
		return map.getMapX();
	}
	public int getMapY() {
		return map.getMapY();
	}
	public void setMapX(int x) {
		map.changeMapCoords(x, map.getMapY());
		xPanel.repaint();
	}
	public void setMapY(int y) {
		map.changeMapCoords(map.getMapX(), y);
		yPanel.repaint();
	}
	public byte getHighestLevel() {
		return this.highestLevel;
	}
	public byte getLowestLevel() {
		return this.lowestLevel;
	}
	public void setHighestLevel (Byte lv) {
		this.highestLevel = lv;
	}
	public void setLowestLevel (Byte lv) {
		this.lowestLevel = lv;
	}
	
	public void showBases() {
		paintBase = true;
		paintField = false;
		paintNPC = false;
		repaint();
	}
	public void showFields() {
		paintBase = false;
		paintField = true;
		paintNPC = false;
		repaint();
	}
	public void showNPCs() {
		paintBase = false;
		paintField = false;
		paintNPC = true;
		repaint();
	}

	public void refreshCurrenDelay() {
		if (numField == null)
			numField = VisualMapBuilder.treeNPC().getNumField();
		if (npcTreePane == null)
			npcTreePane = VisualMapBuilder.treeNPC();

		if (actNum == 1) {
			npcTreePane.setChangingByProg(true);
			numField.setText(String
					.valueOf(map.delays[actField[0].x][actField[0].y]));
		} else {
			npcTreePane.setChangingByProg(true);
			//System.out.println("TMP.refreshCurrentDelay : actNum = " + actNum);
			numField.setText("");
		}
	}
	public void setCurrentDelay() {
		//System.out.println("TopMapPanel.setCurrentDelay :: reached");

		if (numField.getText().equals(""))
			return;

		if (numField == null)
			numField = VisualMapBuilder.treeNPC().getNumField();

		int delay = Integer.parseInt(numField.getText());
		for (int i = 0; i < actNum; i++)
			map.delays[actField[i].x][actField[i].y] = delay;
	}
}
