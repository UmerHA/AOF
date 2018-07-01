package localServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Server {

	static ConnectionSource dbConnectionSource;
	static Dao<Player, String> playerDao;
	static Dao<NPC, String> npcDao;
	static List<Player> players = new ArrayList<Player>();

	static JFrame frame = new JFrame("Server");
	static JTextField dataField = new JTextField(40);
	static JTextArea messageArea = new JTextArea(8, 60);
	static JScrollPane scrollableMessageArea = new JScrollPane(messageArea);

	public static void main(String[] args) {
		connectToDB();
		startServer();
		new NPCMover().start();

		try {
			ClientConnector.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void connectToDB() {
		try {
			String url = "jdbc:sqlite:src/data/players.db";

			dbConnectionSource = new JdbcConnectionSource(url);

			playerDao = DaoManager.createDao(dbConnectionSource, Player.class);
			TableUtils.createTableIfNotExists(dbConnectionSource, Player.class);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	static void startServer() {
		// Layout GUI
		messageArea.setEditable(false);
		frame.getContentPane().add(dataField, "South");
		frame.getContentPane().add(scrollableMessageArea, "Center");

		// Add Listeners
		dataField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientConnector.sendMessage(dataField.getText(), 0);
				dataField.setText("");
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null); // this will center the JFrame
		frame.setVisible(true);

	}

	static void displayMessage(String message) {
		messageArea.append(message);

		JScrollBar vertical = scrollableMessageArea.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
	}

	public static List<Response> handle(String message, int clientNumber) throws NullPointerException {
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

		if (tokens[0].equals("mss"))
			return message_response(tokens, clientNumber);

		System.out.println("Could not handle: " + message);
		return null;
	}

	static Stream<Integer> otherPlayerIds(int playerId) {
		return ClientConnector.getPlayingClients().stream().filter((otherPlayerId) -> otherPlayerId != playerId);
	}

	static Stream<Integer> allPlayerIds() {
		return ClientConnector.getPlayingClients().stream();
	}

	static List<Response> login_response(String[] tokens, int clientNumber) {
		String username = tokens[1];
		String password = tokens[2];

		try {
			Player player = playerDao.queryForId(username);

			if (player == null)
				// Player not found
				return Response.create(clientNumber, "loin", "nf");

			if (password.equals(player.getPassword())) {
				// successful login
				players.set(clientNumber, player);

				String x = player.getX();
				String y = player.getY();
				String z = player.getZ();

				// notify all other clients of this clients login
				List<Response> notifyOtherPlayers = otherPlayerIds(clientNumber)
						.map((otherClientNum) -> Response.create(otherClientNum, "upd", username, "on", x, y, z))
						.reduce(Response.EMPTY, (r1, r2) -> Response.join(r1, r2));

				// notify this client of all other active clients
				List<Response> initExternalPlayers = otherPlayerIds(clientNumber).map((id) -> players.get(id)).map(
						(p) -> Response.create(clientNumber, "extinit", p.getUsername(), p.getX(), p.getY(), p.getZ()))
						.reduce(Response.EMPTY, (r1, r2) -> Response.join(r1, r2));

				// notify this client that login is successful and pass position
				List<Response> loginSuccessful = Response.create(clientNumber, "loin", "dne", x, y, z, username);

				return Response.join(loginSuccessful, initExternalPlayers, notifyOtherPlayers);

			} else
				return Response.create(clientNumber, "loin", "bafo", "pw");

		} catch (SQLException e) {
			// Player not found
			return Response.create(clientNumber, "Loin", "nf");
		}
	}

	static List<Response> register_response(String[] tokens, int clientNumber) {
		String username = tokens[1];
		String password = tokens[2];

		Player player = new Player();
		player.setUsername(username);
		player.setPassword(password);
		player.setDefaultValues();

		try {
			playerDao.create(player);
			players.add(player);

			return Response.create(clientNumber, "okay"); // TODO
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.create(clientNumber, "not okay"); // TODO
		}

	}

	static List<Response> logout_response(String[] tokens, int clientNumber) {
		Player player = players.get(clientNumber);
		String username = player.getUsername();

		try {
			playerDao.update(player);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		players.set(clientNumber, null);

		List<Response> responses = otherPlayerIds(clientNumber)
				.map((otherClientNum) -> Response.create(otherClientNum, "upd", username, "ofl"))
				.reduce(Response.EMPTY, (r1, r2) -> Response.join(r1, r2));

		return responses;
	}

	static List<Response> move_response(String[] tokens, int clientNumber) {
		String x = tokens[1];
		String y = tokens[2];
		String z = tokens[3];

		Player player = players.get(clientNumber);

		player.setX(x);
		player.setY(y);
		player.setZ(z);

		try {
			playerDao.update(player);

			String username = players.get(clientNumber).getUsername();

			List<Response> responsesForOtherPlayers = otherPlayerIds(clientNumber)
					.map(playerId -> Response.create(playerId, "upd", username, x, y, z))
					.reduce(Response.EMPTY, (r1, r2) -> Response.join(r1, r2));

			Util.log("Moving " + username + ". So I should update " + responsesForOtherPlayers.size()
					+ " other players.");

			return Response.join(Response.create(clientNumber, "upd", username, x, y, z),
					Response.create(clientNumber, "mve", "dne"), responsesForOtherPlayers);
		} catch (SQLException e) {
			e.printStackTrace();

			return Response.create(clientNumber, "Move not succesful"); // TODO:
																		// Move
																		// not
			// succesful
		}
	}

	static List<Response> message_response(String[] tokens, int clientNumber) {
		// Format: mss~message
		String message = tokens[1];
		String username = players.get(clientNumber).getUsername();

		return otherPlayerIds(clientNumber)
				.map((otherClientNumber) -> Response.create(otherClientNumber, "mss", username, message))
				.reduce(Response.EMPTY, (r1, r2) -> Response.join(r1, r2));
	}
}
