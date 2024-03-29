package ia;

import java.util.Comparator;
import java.util.List;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Grid;
import pastafari.Pathfinding;
import pastafari.Tile;
import pastafari.TileType;
import pastafari.structures.BuildingType;
import pastafari.structures.City;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class IADebile implements IAInterface {

	enum State
	{
		Farming,
		Attacking
	}
	
	class AttackState
	{
		public Tile position;
		public Unit target;
		public String toString()
		{
			return "(pos=" + position +")";
		}
	}
	
	State state = State.Farming;
	IACity city = new IACity(true);
	IAtest test = new IAtest();
	@Override
	public boolean makeTurn(GameServer srv) 
	{
		boolean returnVal = false;
		
		city.setMAX_PEASANT(8);
		GameState gs = srv.getGameState();

		test.moveExplore(gs);
		test.srv = srv;
		test.game = srv.getGameState();

		city.makeTurn(srv);
		
		// Gestion des paysants.
		if(state == State.Farming)
		{
			srv.log("FARMING : " + srv.getGameState().getMyPlayer().countUnitByType(UnitType.PEASANT));
			if(srv.getGameState().getMyPlayer().countUnitByType(UnitType.PEASANT) < 3)
			{
				
			}
			else
			{
				state = State.Attacking;
			}
			
			
		}
		
		if(state == State.Attacking)
		{
			srv.log("MOVE ENGINEERS !!");
			this.moveEngineers(srv);
			this.moveArmy(srv);
		}
		
		try {
			Thread.sleep(0*500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		srv.endTurn();
		
		return false;
	}
	
	void moveArmy(GameServer srv)
	{
		List<Unit> attaquants = srv.getGameState().getMyPlayer().getUnitsByType(UnitType.BALLISTA);
		attaquants.addAll(srv.getGameState().getMyPlayer().getUnitsByType(UnitType.PALADIN));
		attaquants.addAll(srv.getGameState().getMyPlayer().getUnitsByType(UnitType.DWARF));
		attaquants.addAll(srv.getGameState().getMyPlayer().getUnitsByType(UnitType.SOLDIER));
		attaquants.addAll(srv.getGameState().getMyPlayer().getUnitsByType(UnitType.SCOUT));
		Pathfinding p = new Pathfinding(srv);
		srv.log("MOVE ARMY !! COUNT = " + attaquants.size());
		for(Unit atq : attaquants)
		{
			AttackState s = findEnnemy(atq, srv);
			if(s == null)
				continue;
			
			List<Tile> path = p.FindBestPath(atq.getTile(), s.position, true);
			boolean pathDone = true;
			for(Tile t : path)
			{
				if(!srv.sendMove(atq.getId(), t.getX(), t.getY()))
				{
					pathDone = false;
					break;
				}
			}
			
			// Si on est arriv�
			Unit updatedTarget = srv.getGameState().getOtherPlayer().getUnitById(s.target.getId());
			if(updatedTarget != null && updatedTarget.isAlive())
			{
				if(pathDone && srv.getGameState().getMyPlayer().getUnitById(atq.getId()).getCurrentAction() >= 2)
				{
					srv.log("updated target = " + updatedTarget == null ? "null" : updatedTarget.isAlive() + " . " + updatedTarget.getCurrentHP());
					srv.sendAttack(atq.getId(), s.target.getTile().getX(), s.target.getTile().getY());
				}
			}
		}
	}
	
	AttackState findEnnemy(Unit from, GameServer srv)
	{
		Pathfinding p = new Pathfinding(srv);
		List<Tile> accessible = p.GetCCL(from.getTile(), from.getType(), true, false);
		List<Unit> ennemies = srv.getGameState().getOtherPlayer().getUnits();
		AttackState as = null;
		int minDistance = Integer.MAX_VALUE;
		for(Unit u : ennemies)
		{
			for(Tile tile : accessible)
			{
				int distance = Grid.getDistance(u.getTile(), tile);
				if(distance < minDistance) // distance < u.getRange() && 
				{
					minDistance = distance;
					as = new AttackState();
					as.position = tile;
					as.target = u;
				}
			}
		}

		srv.log("Accessible count = " + accessible.size() + "; Target = " + as);
		return as;
	}
	
	void moveEngineers(GameServer srv)
	{
		List<Unit> engineers = srv.getGameState().getMyPlayer().getUnitsByType(UnitType.ENGINEER);
		City target = srv.getGameState().getOtherPlayer().getCity();
		Pathfinding p = new Pathfinding(srv);
		
		for(Unit eng : engineers)
		{
			if(eng.getTile().getType() == TileType.RIVER && eng.getTile().getBuildingType() != BuildingType.BRIDGE)
				srv.sendBuild(eng.getId(), BuildingType.BRIDGE);
			
			List<Tile> path = p.FindBestPath(eng.getTile(), target.getTile(), true);
			for(Tile t : path)
			{
				
				if(!srv.sendMove(eng.getId(), t.getX(), t.getY()))
					break;
				
				if(eng.getTile().getType() == TileType.RIVER && eng.getTile().getBuildingType() != BuildingType.BRIDGE)
					if(!srv.sendBuild(eng.getId(), BuildingType.BRIDGE))
						break;
			}
		}
		
		// on regarde l'�tat updat� des ing�s
		engineers = srv.getGameState().getMyPlayer().getUnitsByType(UnitType.ENGINEER);
		for(Unit eng : engineers)
		{
			if(eng.getTile().equals(target.getTile()) &&  eng.getCurrentAction() >= 2)
				srv.sendCommand("D " + eng.getId());
		}
	}
}
