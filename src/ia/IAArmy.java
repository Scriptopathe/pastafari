package ia;

import java.util.ArrayList;
import java.util.PriorityQueue;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Grid;
import pastafari.Pathfinding;
import pastafari.Player;
import pastafari.Tile;
import pastafari.TileType;
import pastafari.structures.Building;
import pastafari.structures.BuildingType;
import pastafari.units.Engineer;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class IAArmy implements IAInterface {
	GameState game;
	@Override
	public void makeTurn(GameServer srv) {
		game = srv.getGameState();
		Player me = game.getMyPlayer();
		
		PriorityQueue<Target> sortedTarget = new PriorityQueue<>();
		
		for(Unit u : me.getUnits())
		if(u.getType() == UnitType.ENGINEER) {
			Engineer eng = (Engineer) u;
			if (!eng.isDifferent()){
				if(u.getTile().getBuilding() == me.getCity()) {
					attackInRange(u, game.getGrid(), sortedTarget);
				}
				else if(u.getTile().getBuildingType() != BuildingType.BRIDGE && u.getTile().getType() == TileType.RIVER) {// If on river: make bridge
					if(u.getTile().getBuildingType() != BuildingType.VOID)
						srv.sendDestroy(u.getId());
					srv.sendBuild(u.getId(), BuildingType.BRIDGE);
				}
				else if(u.getTile().getBuildingType() != BuildingType.ROAD && u.getTile().getType() == TileType.MOUNTAIN) {
					if(u.getTile().getBuildingType() != BuildingType.VOID)
						srv.sendDestroy(u.getId());
					srv.sendBuild(u.getId(), BuildingType.ROAD);
				}
				else if(u.getTile().getBuildingType() == BuildingType.CITY) {
					srv.sendDestroy(u.getId());
				}
				else {
					attackInRange(u, game.getGrid(), sortedTarget);
				}
			}else{
				Tile myCityTile = game.getMyPlayer().getCity().getTile();
				if (Grid.getDistance(myCityTile, eng.getTile()) == 1 && myCityTile.getBuildingType() == BuildingType.VOID && myCityTile.getType() != TileType.RIVER){
					srv.sendBuild(eng.getId(), BuildingType.HOSPITAL);
				}else{
					if(u.getTile().getBuilding() == me.getCity()) {
						attackInRange(u, game.getGrid(), sortedTarget);
					}
					else if(u.getTile().getBuildingType() != BuildingType.BRIDGE && u.getTile().getType() == TileType.RIVER) {// If on river: make bridge
						if(u.getTile().getBuildingType() != BuildingType.VOID)
							srv.sendDestroy(u.getId());
						srv.sendBuild(u.getId(), BuildingType.BRIDGE);
					}
					else if(u.getTile().getBuildingType() != BuildingType.ROAD && u.getTile().getType() == TileType.MOUNTAIN) {
						if(u.getTile().getBuildingType() != BuildingType.VOID)
							srv.sendDestroy(u.getId());
						srv.sendBuild(u.getId(), BuildingType.ROAD);
					}
					else if(u.getTile().getBuildingType() == BuildingType.CITY) {
						srv.sendDestroy(u.getId());
					}
					else {
						attackInRange(u, game.getGrid(), sortedTarget);
					}
				}
			}
		} else /* also move fucking lazy peasant  if(u.getType() != UnitType.PEASANT)*/ {
			attackInRange(u, game.getGrid(), sortedTarget);
		}
		
		// Attack
		while(!sortedTarget.isEmpty()) {
			Target t = sortedTarget.poll();
			if(!t.hunter.isAlive()) continue;
			if(t.hunter.getCurrentAction() < 2) continue;
			if(!t.target.isAlive()) continue;
			
			srv.sendAttack(t.hunter.getId(), t.target.getTile().getX(), t.target.getTile().getY());
		}
		
		game = srv.getGameState();
		me = game.getMyPlayer();
		
		PriorityQueue<MoveToCity> sortedDest = new PriorityQueue<>();
		Pathfinding p = new Pathfinding(srv);
		for(Unit u : me.getUnits())
		if(u.getCurrentAction() > 0 && u.isAlive()) {
			ArrayList<Tile> path = (ArrayList<Tile>) p.FindPath(u.getTile(), game.getPlayer(1-game.getMyPlayer().getId()).getCity().getTile(), u.getType() == UnitType.ENGINEER, true);
			if(path.size() > 0) {
				sortedDest.add(new MoveToCity(u, path, game.getMyPlayer().getCity().getTile(), game.getPlayer(1-game.getMyPlayer().getId()).getCity().getTile()));
			}
		}
		
		while(!sortedDest.isEmpty()) {
			MoveToCity m = sortedDest.poll();
			if(!m.u.isAlive()) continue;
			if(!Grid.canMove(m.u.getType()==UnitType.ENGINEER, m.u.getCurrentAction(), m.path.get(0))) continue;
			srv.sendMove(m.u.getId(), m.path.get(0).getX(), m.path.get(0).getY());
		}
	}
	
	void attackInRange(Unit u, Grid g, PriorityQueue<Target> sortedTarget) {
		for(int x = 0; x < g.getSize(); x++)
		for(int y = 0; y < g.getSize(); y++)
		if(g.getTile(x, y).getUnit() != null && !g.getTile(x, y).getUnit().getPlayer().isMe())
		if(u.inRange(g.getTile(x, y).getUnit()))
		{
			sortedTarget.add(new Target(u, g.getTile(x, y).getUnit(), game.getMyPlayer().getCity().getTile()));
		}
	}
	
	class MoveToCity implements Comparable<MoveToCity> {
		ArrayList<Tile> path;
		Tile myCity;
		Tile opponentCity;
		Unit u;
		public MoveToCity(Unit u, ArrayList<Tile> path, Tile city, Tile opponentCity) {
			this.u = u;
			this.path = path;
			myCity = city;
			this.opponentCity = opponentCity;
		}
		@Override
		public int compareTo(MoveToCity o) {
			int d1 = Grid.getDistance(opponentCity, u.getTile()); 
			int d2 = Grid.getDistance(opponentCity, o.u.getTile()); 
			if(d1 < d2)
				return -1;
			if(d2 < d1)
				return +1;
			d1 = Grid.getDistance(myCity, u.getTile()); 
			d2 = Grid.getDistance(myCity, o.u.getTile()); 
			if(d1 > d2)
				return -1;
			if(d2 > d1)
				return +1;
			return 0;
		}
		
	}
	
	class Target implements Comparable<Target> {
		Unit hunter, target;
		Tile myCity;
		Target(Unit hunter, Unit target, Tile city) {
			this.hunter = hunter;
			this.target = target;
			this.myCity = city;
		}
		@Override
		public int compareTo(Target o) {
			if(target.getType() == UnitType.ENGINEER)
				return -1;
			else if(o.target.getType() == UnitType.ENGINEER)
				return +1;
			else {
				int d = Grid.getDistance(target.getTile(), myCity);
				int od = Grid.getDistance(o.target.getTile(), myCity);
				return d < od ? -1 : +1;
			}
				
		}
		
	}
	
	public static boolean canAttackMove(boolean isEngineer, int action, Tile to) {
		if(to.getType() == TileType.RIVER && !(isEngineer || to.getBuildingType() == BuildingType.BRIDGE)) return false;
		
		int cost = 2;
		
		if(to.getType() == TileType.MOUNTAIN) cost += 2;
		if(to.getBuildingType() == BuildingType.ROAD) cost /= 2;
		
		return cost <= action;
	}
}
