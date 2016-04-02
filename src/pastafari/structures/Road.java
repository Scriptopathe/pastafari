package pastafari.structures;

public class Road extends Building {
	public static final int BUILD_COST = 50;
	public static final int MAX_HP = 1;
	
	public Road(int x, int y) {
		super(x, y, BUILD_COST, MAX_HP);
	}
}
