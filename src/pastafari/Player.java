package pastafari;

import java.util.ArrayList;
import java.util.List;

import pastafari.structures.City;
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
	
	public int countArcher(){
		int count = 0;
		for (Unit u : this.units){
			if (u.getType() == UnitType.ARCHER)
				count++;
		}
		return count;
	}
	
	public int countBallista(){
		int count = 0;
		for (Unit u : this.units){
			if (u.getType() == UnitType.BALLISTA)
				count++;
		}
		return count;
	}
	
	public int countDwarf(){
		int count = 0;
		for (Unit u : this.units){
			if (u.getType() == UnitType.DWARF)
				count++;
		}
		return count;
	}
	
	public int countEngineer(){
		int count = 0;
		for (Unit u : this.units){
			if (u.getType() == UnitType.ENGINEER)
				count++;
		}
		return count;
	}
	
	public int countPaladin(){
		int count = 0;
		for (Unit u : this.units){
			if (u.getType() == UnitType.PALADIN)
				count++;
		}
		return count;
	}
	
	public int countPeasant(){
		int count = 0;
		for (Unit u : this.units){
			if (u.getType() == UnitType.PEASANT)
				count++;
		}
		return count;
	}

	public int countScout(){
		int count = 0;
		for (Unit u : this.units){
			if (u.getType() == UnitType.SCOUT)
				count++;
		}
		return count;
	}

	public int countSoldier(){
		int count = 0;
		for (Unit u : this.units){
			if (u.getType() == UnitType.SOLDIER)
				count++;
		}
		return count;
	}
	/*
	public int countUnitByType(UnitType type){
		int result = 0;
	}
	*/
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
	
	public void setCity(City c) {
		city = c;
	}
	
	public City getCity() {
		return city;
	}
}
