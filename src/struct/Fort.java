/*    */ package struct;
import game.Coordonnee;
/*    */ 
/*    */ public class Fort extends Batiment
/*    */ {
/*    */   public Fort(Coordonnee pos)
/*    */   {
/*  9 */     this.PVMax = 5;
/* 10 */     this.PV = 5;
/* 11 */     this.place = pos;
/* 12 */     this.type = Structure.FORT;
/*    */   }
/*    */   
/*    */   public Structure getType()
/*    */   {
/* 17 */     return Structure.FORT;
/*    */   }
/*    */   
/*    */   public static final int getCoast()
/*    */   {
/* 22 */     return 100;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Struct\Fort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */