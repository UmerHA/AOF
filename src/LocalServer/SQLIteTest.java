package LocalServer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class SQLIteTest {

	static List<SQLiteAccount> accounts = new ArrayList<SQLiteAccount>();
	static Dao<SQLiteAccount, String> accountDao;

	static ConnectionSource connectionSource;

	public static void connect() {
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:src/data/chinook.db";
			// conn = DriverManager.getConnection(url);

			connectionSource = new JdbcConnectionSource(url);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public static void readAccounts() {
		accounts = new ArrayList<SQLiteAccount>();
		
		CloseableIterator<SQLiteAccount> iterator = accountDao.closeableIterator();
		try {
			while (iterator.hasNext()) {
				SQLiteAccount account = iterator.next();
				accounts.add(account);
				System.out.println("read: " + account.getName());
			}
		} finally {
			// close it at the end to close underlying SQL statement
			try {
				iterator.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	public static void createAccount(String username, String password) {
		SQLiteAccount account = new SQLiteAccount();
		account.setName(username);
		account.setPassword(password);

		try {
			accountDao.create(account);
			accounts.add(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void saveAccounts() {
		CloseableIterator<SQLiteAccount> iterator = accountDao.closeableIterator();
		try {
			while (iterator.hasNext()) {
				SQLiteAccount account = iterator.next();
				accountDao.update(account);
				System.out.println("saved: " + account.getName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close it at the end to close underlying SQL statement
			try {
				iterator.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		connect();
		try {
			accountDao = DaoManager.createDao(connectionSource, SQLiteAccount.class);
			
			TableUtils.createTableIfNotExists(connectionSource, SQLiteAccount.class);

			readAccounts();
			
			System.out.println("Current Accounts:");
			for (SQLiteAccount account : accounts)
				System.out.println(account.getName());

			// close the connection source
			connectionSource.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}