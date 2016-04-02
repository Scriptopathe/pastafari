package pastafari;

public class Player {
	private int id;
	private int gold;
	private boolean alive;
	private boolean isMe;
	
	public Player(int id, boolean isMe) {
		this.id = id;
		this.gold = 100;
		this.alive = true;
		this.isMe = isMe;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getGold() {
		return gold;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isMe() {
		return isMe;
	}
}
