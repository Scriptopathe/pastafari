package pastafari.structures;

public class City extends Building {
	public static final int BUILD_COST = 0;
	public static final int MAX_HP = 10;
	
	public City(int x, int y) {
		super(x, y, BUILD_COST, MAX_HP, BuildingType.CITY);
	}
}