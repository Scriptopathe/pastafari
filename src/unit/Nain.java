/*    */ package unit;
import game.Land;
/*    */ 
/*    */ public class Nain extends Unit
/*    */ {
/*    */   public Nain(Land pos, int p) {
/*  8 */     this.strengh = 4;
/*  9 */     this.defense = 4;
/* 10 */     this.action = 2;
/* 11 */     this.actionMax = 4;
/* 12 */     this.life = 4;
/* 13 */     this.lifeMax = 4;
/* 14 */     this.position = pos;
/* 15 */     this.player = p;
/* 16 */     this.range = 1;
/* 17 */     this.type = unitType.NAIN;
/*    */   }
/*    */   
/*    */   public static final int getCost()
/*    */   {
/* 22 */     return 100;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\Nain.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */