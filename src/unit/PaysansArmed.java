/*    */ package unit;
import game.Land;
/*    */ 
/*    */ public class PaysansArmed extends Unit
/*    */ {
/*    */   public PaysansArmed(Land pos, int p)
/*    */   {
/*  9 */     this.strengh = 1;
/* 10 */     this.defense = 1;
/* 11 */     this.action = 2;
/* 12 */     this.actionMax = 2;
/* 13 */     this.life = 1;
/* 14 */     this.lifeMax = 1;
/* 15 */     this.position = pos;
/* 16 */     this.player = p;
/* 17 */     this.range = 1;
/* 18 */     this.type = unitType.PAYSAN;
/*    */   }
/*    */   
/*    */   public static final int getCost()
/*    */   {
/* 23 */     return 10;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\PaysansArmed.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */