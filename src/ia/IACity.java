package ia;

import java.util.ArrayList;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Grid;
import pastafari.Pathfinding;
import pastafari.Player;
import pastafari.Tile;
import pastafari.structures.City;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class IACity implements IAInterface{
	
	private boolean limitEngineer;
	private int leftUnits;
	public int MAX_PEASANT;
	public int createdPeasant;
	private Pathfinding path;
	private UnitType[] units = new UnitType[]{UnitType.SCOUT, UnitType.ENGINEER, UnitType.ARCHER, UnitType.SOLDIER, UnitType.BALLISTA, UnitType.PALADIN, UnitType.DWARF};
	
	@Override
	public void makeTurn(GameServer srv) {
		GameState state = srv.getGameState();
		path = new Pathfinding(srv);
		this.setMAX_PEASANT((int)Math.sqrt(path.GetCCL(srv.getGameState().getMyPlayer().getCity().getTile(), UnitType.PEASANT, false, true).size())/2);
		Player pMe = state.getMyPlayer();
		Player pEnem;
		boolean action;
		boolean engCreated = false;
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
			Unit nearestUnit = null;
			
			// on regarde si il y a une unité à proximité
			for (Unit u : pEnem.getUnits()){
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
					if (gold > 100){
						srv.sendCreate(UnitType.DWARF);
						action = true;
					}else if (gold > 10){
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
							Tile t = getNearestToTile(tiles, nearestUnit.getTile());
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
					if (gold > 50 && 
							((!this.limitEngineer && pMe.countUnitByType(UnitType.ENGINEER) <= state.getGrid().getSize() / 5 - 1)
									|| (this.limitEngineer && pMe.countUnitByType(UnitType.ENGINEER) < 3))
										&& !engCreated){
						srv.sendCreate(UnitType.ENGINEER);
						engCreated = true;
						action = true;
					}else if (gold > 10 && pMe.countUnitByType(UnitType.PEASANT) < MAX_PEASANT){
						srv.sendCreate(UnitType.PEASANT);
						createdPeasant++;
						action = true;
					}else{
						int r = -1;
						if (gold > 100){
							r = (int)(Math.random() * units.length);
						}else if(gold > 60){
							r = (int)(Math.random() * (units.length - 3));
						}else if (gold > 50){
							r = (int)(Math.random() * leftUnits);
						}
						if (r != -1){
							srv.sendCreate(units[r]);
							action = true;
						}
					}
				}
			}
		} while (action);
	}
	
	public IACity(boolean limitEngineer){
		this.limitEngineer = limitEngineer;
		if (this.limitEngineer){
			leftUnits = 1;
			units = new UnitType[]{UnitType.SCOUT, UnitType.ARCHER, UnitType.SOLDIER, UnitType.BALLISTA, UnitType.PALADIN, UnitType.DWARF};
		}
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

	public void setMAX_PEASANT(int mAX_PEASANT) {
		MAX_PEASANT = mAX_PEASANT;
	}
	
	public static int getDefBallistaID(GameState state){
		Player p = state.getMyPlayer();
		Player enemP = state.getOtherPlayer();
		Tile cityTile = p.getCity().getTile();
		Tile enemCity = enemP.getCity().getTile();
		int minID = -1;
		ArrayList<Unit> bList = (ArrayList<Unit>) p.getUnitsByType(UnitType.BALLISTA);
		if (bList.size() > 0){
			Unit tmpBallista = bList.get(0);
			minID = tmpBallista.getId();
			for (Unit b : p.getUnitsByType(UnitType.BALLISTA)){
				if (Grid.getDistance(b.getTile(), cityTile) <= Grid.getDistance(tmpBallista.getTile(), cityTile)
						&& Grid.getDistance(b.getTile(), enemCity) > Grid.getDistance(tmpBallista.getTile(), enemCity)){
					b = tmpBallista;
					minID = b.getId();
				}
			}
		}
		return minID;
	}
}
