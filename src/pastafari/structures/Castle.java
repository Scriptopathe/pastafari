package pastafari.structures;

public class Castle extends Building {
	public static final int BUILD_COST = 100;
	public static final int MAX_HP = 5;
	
	public Castle(int x, int y) {
		super(x, y, BUILD_COST, MAX_HP);
	}
}