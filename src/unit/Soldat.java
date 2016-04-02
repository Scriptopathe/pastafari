/*    */ package unit;
import game.Land;
/*    */ 
/*    */ public class Soldat extends Unit
/*    */ {
/*    */   public Soldat(Land pos, int p) {
/*  8 */     this.strengh = 3;
/*  9 */     this.defense = 3;
/* 10 */     this.action = 2;
/* 11 */     this.actionMax = 4;
/* 12 */     this.life = 4;
/* 13 */     this.lifeMax = 4;
/* 14 */     this.position = pos;
/* 15 */     this.player = p;
/* 16 */     this.range = 1;
/* 17 */     this.type = unitType.SOLDAT;
/*    */   }
/*    */   
/*    */   public static final int getCost()
/*    */   {
/* 22 */     return 60;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\Soldat.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */