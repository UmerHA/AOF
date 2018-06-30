package LocalServer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "player")
public class Player {
	@DatabaseField(id = true)
	private String username;
	@DatabaseField
	private String password;
	@DatabaseField
	private String x;
	@DatabaseField
	private String y;
	@DatabaseField
	private String z;
	@DatabaseField
	private String maxHP;
	@DatabaseField
	private String momHP;
	@DatabaseField
	private String coins;
	
	public Player() {}
	
	public void setDefaultValues() {
		this.x = "5";
		this.y = "5";
		this.z = "0";
		this.maxHP = "100";
		this.momHP = "100";
		this.coins = "50";
	}
	
	public void setUsername(String username) {this.username = username;}
	public void setPassword(String password) {this.password = password;}
	public void setX(String x) {this.x = x;}
	public void setY(String y) {this.y = y;}
	public void setZ(String z) {this.z = z;}
	public void setMaxHP(String maxHP) {this.maxHP = maxHP;}
	public void setMomHP(String momHP) {this.momHP = momHP;}
	public void setCoins(String coins) {this.coins = coins;}
	
	public String getUsername() {return username;}
	public String getPassword() {return password;}
	public String getX() {return x;}
	public String getY() {return y;}
	public String getZ() {return z;}
	public String getMaxHP() {return maxHP;}
	public String getMomHP() {return momHP;}
	public String getCoins() {return coins;}	

}
