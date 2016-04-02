/*    */ package unit;
import game.Coordonnee;
import game.Land;
/*    */ 
/*    */ public class Archer extends Unit
/*    */ {
/*    */   int rangeMax;
/*    */   
/*    */   public Archer(Land pos, int p)
/*    */   {
/* 12 */     this.strengh = 3;
/* 13 */     this.defense = 1;
/* 14 */     this.action = 2;
/* 15 */     this.actionMax = 4;
/* 16 */     this.life = 1;
/* 17 */     this.lifeMax = 1;
/* 18 */     this.position = pos;
/* 19 */     this.player = p;
/* 20 */     this.rangeMax = 3;
/* 21 */     this.range = 2;
/* 22 */     this.type = unitType.ARCHER;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean inrange(Unit adversaire)
/*    */   {
/* 28 */     boolean pos = (this.position.place.distance(adversaire.position.place) <= this.rangeMax) && (this.position.place.distance(adversaire.position.place) >= this.range);
/* 29 */     boolean foret = adversaire.position.type != game.LandType.FORET;
/* 30 */     return (pos) && (foret);
/*    */   }
/*    */   
/*    */   public static final int getCost() {
/* 34 */     return 60;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\Archer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */