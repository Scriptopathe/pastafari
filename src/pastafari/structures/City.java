package pastafari.structures;

import pastafari.Grid;

public class City extends Building {
	public static final int BUILD_COST = 0;
	public static final int MAX_HP = 10;
	
	public City(int x, int y) {
		super(x, y, BUILD_COST, MAX_HP, BuildingType.CITY);
	}
	
	public int[] leftPlace(Grid g){
		int x = this.getX(), y = this.getY();
		for (int i = -1; i < 2; i++){
			for (int j = -1; j < 2; j++){
				if (i != 0 && j != 0 && x + i >= 0 && x + i < g.getSize() && y + j >= 0 && y + j < g.getSize()){
					if (g.getTile(x + i, y + j).isAccessible())
						return new int[]{x + i, y + j};
				}
			}
		}
		return null;
	}
}