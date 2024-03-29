package pastafari.units;

import java.util.ArrayList;
import java.util.List;

import pastafari.Grid;
import pastafari.Player;
import pastafari.Tile;
import pastafari.TileType;
import pastafari.structures.BuildingType;
import pastafari.structures.City;

public abstract class Unit {
	
	private int id;
	private int strength;
	private int defense;
	private int currentAction;
	private int maxAction;
	private int currentHP;
	private int maxHP;
	private Player player;
	private int range;
	private Tile tile;
	private int buildCost;
	private UnitType type;
	
	public Unit(int id, Player player, Tile tile, int strength, int defense, int maxAction, int maxHP, int range, int buildCost, UnitType type) {
		this.player = player;		
		this.strength = strength;
		this.defense = defense;
		this.maxAction = maxAction;
		this.currentAction = 2;
		this.maxHP = maxHP;
		this.currentHP = this.maxHP;
		this.range = range;
		this.tile = tile;
		this.buildCost = buildCost;
		this.id = id;
		this.type = type;
	}

	
	public boolean canMove(Tile to) {	
		return Grid.getMoveCost(this.tile, to) <= this.currentAction;
	}
	
	/** Retourne le chemin maximal pouvant �tre effectu� par cette unit�. */
	public List<Tile> getMaxMove(List<Tile> path)
	{
		int cost = 0;
		List<Tile> maxPath = new ArrayList<Tile>();
		for(Tile t : path)
		{
			cost += Grid.getMoveCost(t, t);
			if(cost <= this.currentAction)
				maxPath.add(t);
			else
				break;
		}
		return maxPath;
	}
	
	/** D�place une unit� vers une case adjacente, si c'est possible */
	public void moveTo(Tile to) {
		int cost = Grid.getMoveCost(this.tile, to);
		if(cost <= this.currentAction) {
			this.tile.setUnit(null);
			to.setUnit(this);
			this.setTile(to);
			
			if(to.getBuildingType() != BuildingType.CITY)
				to.setOwner(this.player);
			
			this.currentAction -= cost;
		}
	}
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public boolean isAlive() {
		return this.currentHP > 0;
	}
	
	public void newTurn() {
		if(this.currentHP > 0) this.currentAction = this.maxAction;
	}
	
	public boolean inRange(Unit unit) {
		return Grid.getDistance(this.getTile(), unit.getTile()) <= range;
	}
	
	public UnitType getType() {
		return type;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public int getCurrentAction() {
		return currentAction;
	}

	public int getRange() {
		return range;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getCurrentHP() {
		return currentHP;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public int getMaxAction() {
		return maxAction;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getBuildCost() {
		return buildCost;
	}
	
	public int getId() {
		return id;
	}
	
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	
	public void setCurrentAction(int currentAction) {
		this.currentAction = currentAction;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	public static Unit unitFrom(String type, int id, Tile tile, Player player) {
		if(type.equals("a")) return new Archer(id, tile, player);
		else if(type.equals("b")) return new Ballista(id, tile, player);
		else if(type.equals("n")) return new Dwarf(id, tile, player);
		else if(type.equals("i")) return new Engineer(id, tile, player);
		else if(type.equals("c")) return new Paladin(id, tile, player);
		else if(type.equals("p")) return new Peasant(id, tile, player);
		else if(type.equals("e")) return new Scout(id, tile, player);
		else if(type.equals("s")) return new Soldier(id, tile, player);
		else return null;
	}

	public static char getCharCode(UnitType type){
		char c;
		switch (type){
		case ARCHER:
			c = 'A';
			break;
		case BALLISTA:
			c = 'B';
			break;
		case DWARF:
			c = 'N';
			break;
		case ENGINEER:
			c = 'I';
			break;
		case PALADIN:
			c = 'C';
			break;
		case PEASANT:
			c = 'P';
			break;
		case SCOUT:
			c = 'E';
			break;
		case SOLDIER:
			c = 'S';
			break;
		default:
			c = 'V';
			break;
		}
		return c;
	}
}