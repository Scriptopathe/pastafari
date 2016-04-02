package pastafari.structures;

public class Hospital extends Building {
	public static final int BUILD_COST = 100;
	public static final int MAX_HP = 2;
	
	public Hospital(int x, int y) {
		super(x, y, BUILD_COST, MAX_HP);
	}
}