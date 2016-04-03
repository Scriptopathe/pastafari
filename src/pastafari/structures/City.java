package pastafari.structures;

import java.util.ArrayList;

import pastafari.Grid;
import pastafari.Tile;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class City extends Building {
	public static final int BUILD_COST = 0;
	
	public City(Tile tile) {
		super(tile, BUILD_COST, BuildingType.CITY);
	}
	
	public int[] leftPlace(ArrayList<Tile> neighbors, Unit u){
		for (Tile t : neighbors){
			if (t.getUnitType() == UnitType.VOID && u.canMove(t)){
				return new int[]{t.getX(), t.getY()};
			}
		}
		return null;
	}
}