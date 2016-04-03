package ia;

import java.util.List;

import pastafari.GameServer;
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
	
	State state = State.Farming;
	IACity city = new IACity();
	IAtest test = new IAtest();
	@Override
	public void makeTurn(GameServer srv) 
	{
		city.setMAX_PEASANT(8);
		test.moveExplore(srv.getGameState());
		// Gestion des paysants.
		if(state == State.Farming)
		{
			if(srv.getGameState().getMyPlayer().countUnitByType(UnitType.PEASANT) < 8)
			{
				test.srv = srv;
				test.game = srv.getGameState();
				city.makeTurn(srv);
			}
			else
			{
				state = State.Attacking;
			}
			
			
		}
		
		if(state == State.Attacking)
		{
			List<Unit> engineers = srv.getGameState().getMyPlayer().getUnitsByType(UnitType.ENGINEER);
			List<Unit> attaquants = srv.getGameState().getMyPlayer().getUnitsByType(UnitType.BALLISTA);
			City target = srv.getGameState().getOtherPlayer().getCity();
			Pathfinding p = new Pathfinding(srv);
			
			for(Unit eng : engineers)
			{
				if(eng.getTile().getType() == TileType.RIVER && eng.getTile().getBuildingType() != BuildingType.BRIDGE)
					srv.sendBuild(eng.getId(), BuildingType.BRIDGE);
				
				List<Tile> path = p.FindPath(eng.getTile(), target.getTile(), true, true);
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
		
		
		srv.endTurn();
	}

}
