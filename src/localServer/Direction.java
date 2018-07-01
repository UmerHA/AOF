package localServer;

class Direction {
	int x;
	int y;

	public Direction() {
		int rand = Util.randInt(1, 4);
		switch (rand) {
		case 1:
			x = 1;
			y = 0;
			break;
		case 2:
			x = 0;
			y = 1;
			break;
		case 3:
			x = -1;
			y = 0;
			break;
		case 4:
			x = 0;
			y = -1;
			break;
		}
	}

	void rotateClockwise() {
		// Apply rotation matrix [[0, 1], [-1,0]] for 90 degree clockwise
		// rotation
		int newX = y;
		int newY = -x;

		x = newX;
		y = newY;
	}

	void rotateCounterClockwise() {
		// Apply rotation matrix [[0, -1], [1,0]] for 90 degree clockwise
		// rotation
		int newX = -y;
		int newY = x;

		x = newX;
		y = newY;
	}

	void reverse() {
		x = -x;
		y = -y;
	}
}