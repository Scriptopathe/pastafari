/*    */ package unit;
import game.Land;
/*    */ 
/*    */ public class Baliste extends Unit
/*    */ {
/*    */   int rangeMax;
/*    */   
/*    */   public Baliste(Land pos, int p) {
/* 10 */     this.strengh = 6;
/* 11 */     this.defense = 4;
/* 12 */     this.action = 2;
/* 13 */     this.actionMax = 2;
/* 14 */     this.life = 4;
/* 15 */     this.lifeMax = 4;
/* 16 */     this.position = pos;
/* 17 */     this.player = p;
/* 18 */     this.rangeMax = 6;
/* 19 */     this.range = 3;
/* 20 */     this.type = unitType.BALIST;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean inrange(Unit adversaire)
/*    */   {
/* 26 */     boolean pos = (this.position.place.distance(adversaire.position.place) <= this.rangeMax) && (this.position.place.distance(adversaire.position.place) >= this.range);
/* 27 */     boolean foret = adversaire.position.type != game.LandType.FORET;
/* 28 */     return (pos) && (foret);
/*    */   }
/*    */   
/*    */   public static final int getCost() {
/* 32 */     return 100;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\Baliste.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */