package localServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import localServer.NPC;

class NPCMover extends Thread {
	static List<NPC> npcs = new ArrayList<NPC>();

	@Override
	public void run() {
		createNpcsFromFile();

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
					System.out.println("localServer.NPCMover.createMonFromFile |" + line + "|");
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

	void moveNPC(int id) {
		NPC npc = npcs.get(id);

		if (Math.random() < npc.walkingProbability)
			return;

		npc.updateDirection();
		int stepX = npc.direction.x;
		int stepY = npc.direction.y;
				
		if (npc.wouldWalkOutOfMap(stepX, stepY) || npc.wouldWalkOutOfRadius(stepX, stepY)) {
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
}
