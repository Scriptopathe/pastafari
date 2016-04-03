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

public class IAPeteLesInges implements IAInterface {

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
			Thread.sleep(1*800);
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
			
		}
	}
	
	/*Unit findEnnemyInRange(Unit from, GameServer srv)
	{
		
	}*/
	
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
		
		// on regarde l'état updaté des ingés
		engineers = srv.getGameState().getMyPlayer().getUnitsByType(UnitType.ENGINEER);
		for(Unit eng : engineers)
		{
			if(eng.getTile().equals(target.getTile()) &&  eng.getCurrentAction() >= 2)
				srv.sendCommand("D " + eng.getId());
		}
	}
}
