package ia;

import java.util.ArrayList;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Player;
import pastafari.Tile;
import pastafari.structures.City;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class IACity implements IAInterface{
	
	public int MAX_PEASANT;
	public int createdPeasant;
	
	@Override
	public void makeTurn(GameServer srv) {
		GameState state = srv.getGameState();
		Player pMe = state.getMyPlayer();
		boolean action;
		City city = pMe.getCity();
		
		do{
			// on suppose qu'on a fait aucune action
			action = false;
			
			// on récupère le dernier state à jour
			state = srv.getGameState();
			pMe = state.getMyPlayer();
			City myCity = pMe.getCity();
			
			// si une unité occupe la cité
			if (myCity.getTile().getUnitType() != UnitType.VOID && myCity.getTile().getUnit().getCurrentAction() != 0){
				// si on peut bouger l'unité
				ArrayList<Tile> tiles = state.getGrid().getFreeNeighbors(myCity.getTile(), false, false);
				
				if (tiles.size() != 0){
					Tile t = tiles.get(0);
					srv.sendMove(myCity.getTile().getUnit().getId(), t.getX(), t.getY());
					action = true;
				}
			}else{
				// sinon on dépense!
				int gold = pMe.getGold();
				if (gold > 50 && pMe.countEngineer() == 0 ){
					srv.sendCreate(UnitType.ENGINEER);
					action = true;
				}else if (gold > 10 && createdPeasant < MAX_PEASANT){
					srv.sendCreate(UnitType.PEASANT);
					createdPeasant++;
					action = true;
				}
			}
		} while (action);
	}
	
	public IACity(int maxPeasants){
		MAX_PEASANT = maxPeasants;
		createdPeasant = 0;
	}
	
	
}
