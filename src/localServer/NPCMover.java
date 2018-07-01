package localServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import localServer.map.MapField;
import localServer.map.mapFields.*;
import localServer.map.mapObjects.*;

class NPCMover extends Thread {
	static List<NPC> npcs = new ArrayList<NPC>();

	static final int mapSize = 150;
	static MapField[] fields[] = new MapField[mapSize][mapSize];

	@Override
	public void run() {
		createNpcsFromFile();
		createMapFieldsFromFile();

		long time = 0;
		while (true) {
			try {
				Thread.sleep(100);
				time += 100;
			} catch (InterruptedException e) {
			}

			for (NPC npc : npcs) {
				if (time % npc.sleepingTime == 0)
					moveNPC(npc.id);
			}
		}
	}

	private void createNpcsFromFile() {
		List<String> lines = new ArrayList<String>();

		try {
			File file = new File("src/data/MonData0");
			FileInputStream fileStream = new FileInputStream(file);
			DataInputStream dataStream = new DataInputStream(fileStream);
			BufferedReader fromFile = new BufferedReader(new InputStreamReader(dataStream));

			// Read File Line By Line
			String line;

			while ((line = fromFile.readLine()) != null) {
				if (!line.equals("")) {
					System.out.println("localServer.NPCMover.createNpcsFromFile |" + line + "|");
					lines.add(line);
				}
			}

			fromFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		String[] temp;
		int id = 0;
		for (String line : lines) {
			temp = line.split("~");
			String name = temp[0];
			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);

			npcs.add(NPC.createNpcByName(name, x, y, id));

			id++;
		}
	}
	public void createMapFieldsFromFile() {
		List<String> lines = new ArrayList<String>();

		try {
			File file = new File("src/data/MapData0");

			FileInputStream fileStream = new FileInputStream(file);
			DataInputStream dataStream = new DataInputStream(fileStream);
			BufferedReader fromFile = new BufferedReader(new InputStreamReader(dataStream));

			// Read File Line By Line
			String line;
			while ((line = fromFile.readLine()) != null) {
				if (!line.equals("")) {
					System.out.println("localServer.NPCMover.createMapFieldsFromFile |" + line + "|");
					lines.add(line);
				}
			}

			fromFile.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		String[] temp;
		for (String line : lines) {
			temp = line.split("~");

			int x = Integer.parseInt(temp[1]);
			int y = Integer.parseInt(temp[2]);

			// System.out.println(data[i]);

			// map base
			if (temp[0].equals("Lava"))
				fields[x][y] = new Lava();
			if (temp[0].equals("Water"))
				fields[x][y] = new Water();
			if (temp[0].equals("Water1"))
				fields[x][y] = new Water1();
			if (temp[0].equals("Water2"))
				fields[x][y] = new Water2();
			if (temp[0].equals("Sand"))
				fields[x][y] = new Sand();
			if (temp[0].equals("Rock"))
				fields[x][y] = new Rock();
			if (temp[0].equals("Wood"))
				fields[x][y] = new Wood();

			// map object
			if (temp[0].equals("LadderUp"))
				fields[x][y] = new LadderUp();
			if (temp[0].equals("LadderDown"))
				fields[x][y] = new LadderDown();
			if (temp[0].equals("Tree"))
				fields[x][y] = new Tree();
			if (temp[0].equals("TropicalTree"))
				fields[x][y] = new TropicalTree();

			// special
			if (temp[0].equals("Plain"))
				fields[x][y] = new PlainField();
			if (temp[0].equals("SpawnField"))
				fields[x][y] = new TropicalTree();
			if (temp[0].equals("LavaHole"))
				fields[x][y] = new LavaHole();

			// Fences :
			// 1-Line
			if (temp[0].equals("FenceNorth"))
				fields[x][y] = new FenceNorth();
			if (temp[0].equals("FenceSouth"))
				fields[x][y] = new FenceSouth();
			if (temp[0].equals("FenceEast"))
				fields[x][y] = new FenceEast();
			if (temp[0].equals("FenceWest"))
				fields[x][y] = new FenceWest();
			// 2-Line
			if (temp[0].equals("FenceNE"))
				fields[x][y] = new FenceNE();
			if (temp[0].equals("FenceNW"))
				fields[x][y] = new FenceNW();
			if (temp[0].equals("FenceNS"))
				fields[x][y] = new FenceNS();
			if (temp[0].equals("FenceSE"))
				fields[x][y] = new FenceSE();
			if (temp[0].equals("FenceSW"))
				fields[x][y] = new FenceSW();
			if (temp[0].equals("FenceWE"))
				fields[x][y] = new FenceWE();
			// 3-line
			if (temp[0].equals("FenceNSE"))
				fields[x][y] = new FenceNSE();
			if (temp[0].equals("FenceNSW"))
				fields[x][y] = new FenceNSW();
			if (temp[0].equals("FenceNWE"))
				fields[x][y] = new FenceNWE();
			if (temp[0].equals("FenceSWE"))
				fields[x][y] = new FenceSWE();
		}
	}

	void moveNPC(int id) {
		NPC npc = npcs.get(id);

		if (Math.random() < npc.walkingProbability)
			return;

		npc.updateDirection();
		int stepX = npc.direction.x;
		int stepY = npc.direction.y;

		if (npc.wouldWalkOutOfMap(stepX, stepY) || npc.wouldWalkOutOfRadius(stepX, stepY)
				|| wouldCollideWithMapField(npc, stepX, stepY)) {
			npc.direction.rotateClockwise();
			return;
		}

		npc.posX += stepX;
		npc.posY += stepY;

		String newX = String.valueOf(npc.posX);
		String newY = String.valueOf(npc.posY);

		// notify all players of the NPC's move
		List<Response> responses = Server.allPlayerIds()
				.map((pID) -> Response.create(pID, "nupd", String.valueOf(id), newX, newY))
				.reduce(Response.EMPTY, (r1, r2) -> Response.join(r1, r2));

		ClientConnector.sendResponses(responses);
	}

	static boolean wouldCollideWithMapField(NPC npc, int stepX, int stepY) {
		int nextX = npc.posX + stepX;
		int nextY = npc.posY + stepY;

		// next field is empty
		if (fields[nextX][nextY] == null)
			return false;

		// next field is generally not open for NPCs
		if (fields[nextX][nextY].openForNPC == false)
			return true;

		// direction is east and next field is not accessible from west
		if (stepX == 1 && stepY == 0)
			if (!fields[nextX][nextY].accessibleWest)
				return true;

		// direction is west and next field is not accessible from east
		if (stepX == -1 && stepY == 0)
			if (!fields[nextX][nextY].accessibleEast)
				return true;

		// direction is south and next field is not accessible from north
		if (stepX == 0 && stepY == 1)
			if (!fields[nextX][nextY].accessibleNorth)
				return true;

		// direction is north and next field is not accessible from south
		if (stepX == 0 && stepY == -1)
			if (!fields[nextX][nextY].accessibleSouth)
				return true;

		return false;
	}
}
