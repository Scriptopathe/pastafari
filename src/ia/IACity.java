package ia;

import java.util.ArrayList;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Player;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class IACity implements IAInterface{

	@Override
	public void makeTurn(GameServer srv) {
		GameState state = srv.getGameState();
		int myID = state.getMyId();
		Player pMe = state.getPlayer(myID);
		int newPlace[] = pMe.getCity().leftPlace(state.getGrid());
		while (pMe.getGold() >= 10 && newPlace != null){ // si on a la place et l'argent
			if (pMe.getGold() >= 100){ // if we have enough money to buy an expensive unit
				int bCount = pMe.countBallista();
				int dCount = pMe.countDwarf();
				int pCount = pMe.countPaladin();
				if (bCount < dCount && bCount < pCount){
					srv.sendCreate(UnitType.BALLISTA);
				}
			}
			srv.sendCommand("C,P");
			pMe.setGold(pMe.getGold() - 10);
			newPlace = pMe.getCity().leftPlace(state.getGrid());
		}
		if (pMe.getCity().getTile().getUnitType() == UnitType.VOID)
			srv.sendCreate(UnitType.PEASANT);
	}
}
