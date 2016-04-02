package pastafari;

public class Player {
	private int id;
	private int gold;
	private boolean alive;
	
	public Player(int id) {
		this.id = id;
		this.gold = 100;
		this.alive = true;
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
}
