package pastafari.structures;

public class Bridge extends Building {
	
	public static final int BUILD_COST = 50;
	public static final int MAX_HP = 2;
	
	public Bridge(int x, int y) {
		super(x, y, BUILD_COST, MAX_HP);
	}
}