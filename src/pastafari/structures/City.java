package pastafari.structures;

import pastafari.Grid;
import pastafari.Tile;
import pastafari.units.UnitType;

public class City extends Building {
	public static final int BUILD_COST = 0;
	
	public City(Tile tile) {
		super(tile, BUILD_COST, BuildingType.CITY);
	}
	
	public int[] leftPlace(Grid g){
		int x = this.getTile().getX(), y = this.getTile().getY();
		for (int i = -1; i < 2; i++){
			for (int j = -1; j < 2; j++){
				if (i != 0 && j != 0 && x + i >= 0 && x + i < g.getSize() && y + j >= 0 && y + j < g.getSize()){
					if (g.getTile(x + i, y + j).getUnitType() != UnitType.VOID)
						return new int[]{x + i, y + j};
				}
			}
		}
		return null;
	}
}