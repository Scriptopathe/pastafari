package pastafari;

import java.util.ArrayList;
import java.util.List;

import pastafari.structures.City;
import pastafari.units.Engineer;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class Player {
	public static Player NoPlayer = new Player(-1, false);
	
	private int id;
	private int gold;
	private boolean alive;
	private boolean isMe;
	
	private ArrayList<Unit> units;
	private City city;
	
	public Player(int id, boolean isMe) {
		this.id = id;
		this.gold = 100;
		this.alive = true;
		this.isMe = isMe;
		
		this.units = new ArrayList<>();
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
	
	public void addUnit(Unit u){
		units.add(u);
	}
	
	public ArrayList<Unit> getUnits(){
		return this.units;
	}
	
	public int countUnitByType(UnitType type){
		return getUnitsByType(type).size();
	}
	
	public List<Unit> getUnitsByType(UnitType type)
	{
		List<Unit> units = new ArrayList<>();
		for(Unit u : this.units)
		{
			if(u.getType() == type)
				units.add(u);
		}
		return units;
	}
	
	public Unit getUnitById(int id)
	{
		for(Unit u : this.units)
		{
			if(u.getId() == id)
				return u;
		}
		return null;
	}
	
	public void setCity(City c) {
		city = c;
	}
	
	public City getCity() {
		return city;
	}
	
	public void setDifferentEngineer(){
		List<Unit> engineers = this.getUnitsByType(UnitType.ENGINEER);
		if (engineers.size() > 1){
			Unit minInge = engineers.get(0);
			for (Unit eng : engineers){
				if (Grid.getDistance(this.getCity().getTile(), eng.getTile()) < Grid.getDistance(this.getCity().getTile(), minInge.getTile())){
					minInge = eng;
				}
			}
			Engineer e = (Engineer) minInge;
			e.setDifferent();
		}
	}
}
