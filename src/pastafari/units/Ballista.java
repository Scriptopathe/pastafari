package pastafari.units;

import pastafari.Player;
import pastafari.Tile;

public class Ballista extends RangedUnit {
	public static final int STRENGTH = 6;
	public static final int DEFENSE = 4;
	public static final int MAX_ACTION = 2;
	public static final int MAX_HP = 4;
	public static final int MIN_RANGE = 3;
	public static final int MAX_RANGE = 6;
	public static final int COST = 100;
	
	public Ballista(Tile tile, Player player) {
		super(player, tile, STRENGTH, DEFENSE, MAX_ACTION, MAX_HP, MIN_RANGE, MAX_RANGE, COST);
	}
}
//import game.Land;
///*    */ 
///*    */ public class Ballista extends Unit
///*    */ {
///*    */   int rangeMax;
///*    */   
///*    */   public Ballista(Land pos, int p) {
///* 10 */     this.strengh = 6;
///* 11 */     this.defense = 4;
///* 12 */     this.action = 2;
///* 13 */     this.actionMax = 2;
///* 14 */     this.life = 4;
///* 15 */     this.lifeMax = 4;
///* 16 */     this.position = pos;
///* 17 */     this.player = p;
///* 18 */     this.rangeMax = 6;
///* 19 */     this.range = 3;
///* 20 */     this.type = unitType.BALIST;
///*    */   }
///*    */   
///*    */ 
///*    */   public boolean inrange(Unit adversaire)
///*    */   {
///* 26 */     boolean pos = (this.position.place.distance(adversaire.position.place) <= this.rangeMax) && (this.position.place.distance(adversaire.position.place) >= this.range);
///* 27 */     boolean foret = adversaire.position.type != game.LandType.FORET;
///* 28 */     return (pos) && (foret);
///*    */   }
///*    */   
///*    */   public static final int getCost() {
///* 32 */     return 100;
///*    */   }
///*    */ }
//
//
///* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\Baliste.class
// * Java compiler version: 7 (51.0)
// * JD-Core Version:       0.7.1
// */