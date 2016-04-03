package ia;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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
	}
	
	State state = State.Farming;
	IACity city = new IACity();
	IAtest test = new IAtest();
	@Override
	public void makeTurn(GameServer srv) 
	{
		city.setMAX_PEASANT(8);
		GameState gs = srv.getGameState();

		test.moveExplore(gs);
		test.srv = srv;
		test.game = srv.getGameState();
		
		city.makeTurn(srv);
		
		// Gestion des paysants.
		if(state == State.Farming)
		{
			srv.log("FARMING : " + srv.getGameState().getMyPlayer().countPeasant());
			if(srv.getGameState().getMyPlayer().countPeasant() < 3)
			{

			}
			else
			{
				state = State.Attacking;
			}
			
			
		}
		
		if(state == State.Attacking)
		{
			srv.log("ATTACK !!");
			this.moveEngineers(srv);
			this.moveArmy(srv);
		}
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		srv.endTurn();
	}
	
	void moveArmy(GameServer srv)
	{
		List<Unit> attaquants = srv.getGameState().getMyPlayer().getUnitsByType(UnitType.BALLISTA);
		attaquants.addAll(srv.getGameState().getMyPlayer().getUnitsByType(UnitType.PALADIN));
		Pathfinding p = new Pathfinding(srv);
		for(Unit atq : attaquants)
		{
			AttackState s = findEnnemy(atq, srv);
			if(s == null)
				continue;
			
			List<Tile> path = p.FindRealPath(atq.getTile(), s.position, true);
			boolean pathDone = true;
			for(Tile t : path)
			{
				if(!srv.sendMove(atq.getId(), t.getX(), t.getY()))
				{
					pathDone = false;
					break;
				}
			}
			
			// Si on est arrivé
			
			if(pathDone && srv.getGameState().getMyPlayer().getUnitById(atq.getId()).getCurrentAction() >= 2)
			{
				srv.sendAttack(atq.getId(), s.target.getTile().getX(), s.target.getTile().getX());
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
				if(distance <= u.getRange() && distance < minDistance)
				{
					minDistance = distance;
					as = new AttackState();
					as.position = tile;
					as.target = u;
				}
			}
		}
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
			
			List<Tile> path = p.FindRealPath(eng.getTile(), target.getTile(), true);
			for(Tile t : path)
			{
				
				if(!srv.sendMove(eng.getId(), t.getX(), t.getY()))
					break;
				
				if(eng.getTile().getType() == TileType.RIVER && eng.getTile().getBuildingType() != BuildingType.BRIDGE)
					if(!srv.sendBuild(eng.getId(), BuildingType.BRIDGE))
						break;
			}
		}
		
		// on regarde l'état updaté des ingés
		engineers = srv.getGameState().getMyPlayer().getUnitsByType(UnitType.ENGINEER);
		for(Unit eng : engineers)
		{
			if(eng.getTile().equals(target.getTile()) &&  eng.getCurrentAction() >= 2)
				srv.sendCommand("D " + eng.getId());
		}
	}
}
