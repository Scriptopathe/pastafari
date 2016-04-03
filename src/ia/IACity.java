package ia;

import java.util.ArrayList;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Grid;
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
		Player pEnem;
		boolean action;
		City city = pMe.getCity();
		
		do{
			// on suppose qu'on a fait aucune action
			action = false;
			
			// on récupère le dernier state à jour
			state = srv.getGameState();
			pMe = state.getMyPlayer();
			pEnem = state.getPlayer(1 - pMe.getId());
			City myCity = pMe.getCity();
			City enemyCity = state.getGrid().getCity(1 - pMe.getId());
			int gold = pMe.getGold();
			
			boolean danger = false;
			int minDist = Integer.MAX_VALUE;
			Unit nearestUnit;
			
			// si une unité ennemie n'est pas à proximité
			for (Unit u : state.getPlayer(1 - state.getMyId()).getUnits()){
				int dist = Grid.getDistance(u.getTile(), myCity.getTile());
				if (dist < 2){
					danger = true;
					if (dist < minDist){
						minDist = dist;
						nearestUnit = u;
					}else if (dist == minDist && u.getTile().getUnitType() == UnitType.ENGINEER){
						nearestUnit = u;
					}
				}
			}
			
			if (danger){
				// on est dans la merde!!
				
				if (myCity.getTile().getUnitType() == UnitType.VOID){
					// on essaye de créer un MNS
					if (gold >= 100){
						srv.sendCreate(UnitType.DWARF);
						action = true;
					}else if (gold >= 10){
						// sinon on se rabat sur le paysant
						srv.sendCreate(UnitType.PEASANT);
						action = true;
					}
				}else{
					// si on a assez d'argent pour créer d'autres murs
					// TODO: vérifier si c'est pas mieux de garder l'unité actuelle au lieu de la faire bouger
					if (gold >= 10){
						ArrayList<Tile> tiles = state.getGrid().getFreeNeighbors(myCity.getTile(), false, false);
						
						if (tiles.size() != 0){
							Tile t = tiles.get(0);
							//Tile t = getNearestEnemyUnit(tiles, )
							srv.sendMove(myCity.getTile().getUnit().getId(), t.getX(), t.getY());
							action = true;
						}
					}
				}
			}else{ // on est pas en danger
				
				UnitType uType = myCity.getTile().getUnitType();
				// si une unité occupe la cité
				if (uType != UnitType.VOID && myCity.getTile().getUnit().getCurrentAction() != 0){
					// si on peut bouger l'unité
					ArrayList<Tile> tiles = state.getGrid().getFreeNeighbors(myCity.getTile(), false, false);
					
					if (tiles.size() != 0){
						Tile goal;
						if (uType == UnitType.BALLISTA){
							// unités en retrait
							goal = getFarsestFromEnemy(tiles, enemyCity.getTile());
						}else{
							// unités en attaque
							goal = getNearestToTile(tiles, enemyCity.getTile());
						}
						
						srv.sendMove(myCity.getTile().getUnit().getId(), goal.getX(), goal.getY());
						action = true;
					}
				}else{
					// sinon on dépense!
					if (gold >= 50 && pMe.countEngineer() == state.getGrid().getSize() / 5 ){
						srv.sendCreate(UnitType.ENGINEER);
						action = true;
					}else if (gold >= 10 && pMe.countPeasant() < MAX_PEASANT){
						srv.sendCreate(UnitType.PEASANT);
						createdPeasant++;
						action = true;
					}
				}
			}
		} while (action);
	}
	
	public IACity(int maxPeasants){
		MAX_PEASANT = maxPeasants;
		createdPeasant = 0;
	}
	
	// on essaye de se rapprocher le plus de la cité ennemie
	public Tile getNearestToTile(ArrayList<Tile> tiles, Tile goalTile){
		int minDist = Integer.MAX_VALUE;
		Tile result = tiles.get(0);
		for (Tile t : tiles){
			if (Grid.getDistance(t, goalTile) < minDist){
				minDist = Grid.getDistance(t, goalTile);
				result = t;
			}
		}
		return result;
	}
	
	// on essaye de se rapprocher le plus de l'ennemis le plus proche
	public Tile getNearestEnemyUnit(ArrayList<Tile> tiles, ArrayList<Unit> units){
		int minDist = Integer.MAX_VALUE;
		Tile result = tiles.get(0);
		for (Tile t : tiles){
			for (Unit u: units){
				if (Grid.getDistance(t, u.getTile()) < minDist){
					minDist = Grid.getDistance(t, u.getTile());
					result = t;
				}
			}
		}
		return result;
	}
	
	
	// get the farsest tile from the enemy city
	public Tile getFarsestFromEnemy(ArrayList<Tile> tiles, Tile enemyCity){
		int maxDist = Integer.MIN_VALUE;
		Tile result = tiles.get(0);
		for (Tile t : tiles){
			if (Grid.getDistance(t, enemyCity) > maxDist){
				maxDist = Grid.getDistance(t, enemyCity);
				result = t;
			}
		}
		return result;
	}
	
	
}
