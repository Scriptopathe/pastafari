package pastafari.structures;

public abstract class Building {
	protected int buildCost;
	protected int maxHP;
	protected int currentHP;
	protected int x;
	protected int y;
	
	public Building(int x, int y, int buildCost, int maxHP) {
		this.buildCost = buildCost;
		this.x = x;
		this.y = y;
		this.maxHP = maxHP;
		this.currentHP = this.maxHP;
	}
	
	public int getBuildCost() {
		return this.buildCost;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getCurrentHP() {
		return currentHP;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
}
