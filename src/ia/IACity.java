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

	@Override
	public void makeTurn(GameServer srv) {
		GameState state = srv.getGameState();
		Player pMe = state.getMyPlayer();
		boolean action;
		City city = pMe.getCity();
		int cityX = city.getTile().getX();
		int cityY = city.getTile().getY();
		
		do{
			// on suppose qu'on a fait aucune action
			action = false;
			
			// on récupère le dernier state à jour
			state = srv.getGameState();
			pMe = state.getMyPlayer();
			City myCity = pMe.getCity();
			
			// si une unité occupe la cité
			if (myCity.getTile().getUnitType() != UnitType.VOID){
				// si on peut bouger l'unité
				int dest[] = myCity.leftPlace(state.getGrid().getNeighbors(myCity.getTile(), false), myCity.getTile().getUnit());
				if (dest != null){
					srv.sendMove(myCity.getTile().getUnit().getId(), dest[0], dest[1]);
					action = true;
				}
			}else{
				// sinon on dépense!
				int gold = pMe.getGold();
				if (gold >= 10){
					srv.sendCreate(UnitType.PEASANT);
					action = true;
				}
			}
		} while (action);
	}
}
