package LocalServer;

import java.sql.SQLException;

public class Protocol {
	public static String handle(String message, int clientNumber) throws NullPointerException {
		message = message.toLowerCase();		
		String[] tokens = message.replace("#", "").split("~");

		if (tokens[0].equals("regi"))
			return register_response(tokens, clientNumber);		
		
		if (tokens[0].equals("loin"))
			return login_response(tokens, clientNumber);

		if (tokens[0].equals("logout"))
			return logout_response(tokens, clientNumber);

		if (tokens[0].equals("mve"))
			return move_response(tokens, clientNumber);

		return "end reached";
	}

	static String buildResponse(String... tokens) {
		return String.join("~", tokens) + "#";
	}

	static String login_response(String[] tokens, int clientNumber) {
		String username = tokens[1];
		String password = tokens[2];

		try {
			Player player = Server.playerDao().queryForId(username);

			if (player == null)
				return buildResponse("Loin", "nf");
			
			if (password.equals(player.getPassword())) {
				// successful login
				Server.players.set(clientNumber, player);

				return buildResponse("Loin", "dne", player.getX(), player.getY(), player.getZ());

			} else
				return buildResponse("Loin", "bafo", "pw");

		} catch (SQLException e) {
			// Player not found
			return buildResponse("Loin", "nf");
		}
	}

	static String register_response(String[] tokens, int clientNumber) {
		String username = tokens[1];
		String password = tokens[2];

		Player player = new Player();
		player.setUsername(username);
		player.setPassword(password);
		player.setDefaultValues();

		try {
			Server.playerDao().create(player);
			Server.players.add(player);
			
			return "okay"; // TODO
		} catch (SQLException e) {
			e.printStackTrace();
			return "not okay"; // TODO
		}

	}

	static String logout_response(String[] tokens, int clientNumber) {
		Player player = Server.players.get(clientNumber);
		try {
			Server.playerDao().update(player);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ""; // TODO
	}

	static String move_response(String[] tokens, int clientNumber) {
		String x = tokens[1];
		String y = tokens[2];
		String z = tokens[3];

		Player player = Server.players.get(clientNumber);
		
		player.setX(x);
		player.setY(y);
		player.setZ(z);
		
		try {
			Server.playerDao.update(player);
			
			String username = Server.players.get(clientNumber).getUsername();

			return buildResponse("upd", username, x, y, z) + buildResponse("mve", "dne");
		} catch (SQLException e) {
			e.printStackTrace();
			
			return ""; //TODO: Move not succesful
		}
	}
}
